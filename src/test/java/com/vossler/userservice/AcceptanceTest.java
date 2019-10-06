package com.vossler.userservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import com.vossler.userservice.dto.ProjectMembership;
import com.vossler.userservice.dto.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {
	@Value("${dataServiceBaseUrl}") String dataServiceBaseUrl;

	@Autowired WebTestClient webClient;
	@Autowired RestTemplate restTemplate;
	
	ParameterizedTypeReference<List<User>> userListTypeRef = new ParameterizedTypeReference<List<User>>() {};
	ParameterizedTypeReference<List<ProjectMembership>> projectMembershipListTypeRef = new ParameterizedTypeReference<List<ProjectMembership>>() {};

	List<User> expectedRegisteredUsers;
	List<User> expectedUnregisteredUsers;
	List<ProjectMembership> expectedMemberships;
	
	@Before
	public void setup() {
		// Because I can't stage data into the external services whose responses we are combining, The only ways I can make assertions about the expected
		// output of our service here in an acceptance test would be to either hard code the expected results, or to call have this test call the external services
		// itself.  The former would work because our external services are actually a static mock, but would not be possible in the real world, where the data coming from
		// the external services might change.  The latter could still generate false failures if the data changes while the test is running, but is still the best
		// we can do under the circumstances.
		expectedRegisteredUsers = restTemplate.exchange(dataServiceBaseUrl + "/registeredusers", HttpMethod.GET, null, userListTypeRef).getBody();
		expectedUnregisteredUsers = restTemplate.exchange(dataServiceBaseUrl + "/unregisteredusers", HttpMethod.GET, null, userListTypeRef).getBody();
		expectedMemberships = restTemplate.exchange(dataServiceBaseUrl + "/projectmemberships", HttpMethod.GET, null, projectMembershipListTypeRef).getBody();
	}
	
	@Test
	public void heartbeatTest() {
		this.webClient.get().uri("/").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("200 OK");
	}
	
	@Test
	public void getUsersTest() {
		
		EntityExchangeResult<List<User>> result = this.webClient.get().uri("/users").exchange().expectStatus().isOk().expectBody(userListTypeRef).returnResult();
		List<User> actualUsers = result.getResponseBody();

		// Assert that every user is represented in the final output, and that all fields other than projectIds are unchanged
		assertEquals(expectedRegisteredUsers.size() + expectedUnregisteredUsers.size(), actualUsers.size());
		for (User expected : expectedRegisteredUsers) assertExpectedUserPresentAndCorrect(expected, actualUsers);
		for (User expected : expectedUnregisteredUsers) assertExpectedUserPresentAndCorrect(expected, actualUsers);
		
		// Assert that every project membership that relates to one of those users is represented in the output
		for (ProjectMembership pm : expectedMemberships) {
			Optional<User> actualUser = actualUsers.stream().filter(u -> u.getId().equals(pm.getUserId())).findFirst();
			if (actualUser.isPresent()) {
				assertTrue(actualUser.get().getProjectIds().contains(pm.getProjectId()));
			}
		}
		
		// Assert that the output does not contain any project memberships not in the set of expected memberships
		for (User u : actualUsers) {
			for (String projectId : u.getProjectIds()) {
				Optional<ProjectMembership> pm = expectedMemberships.stream().filter(item -> item.getProjectId().equals(projectId) && item.getUserId().equals(u.getId())).findFirst();
				assertTrue(pm.isPresent());
			}
		}
	}

	private void assertExpectedUserPresentAndCorrect(User expected, List<User> actualUsers) {
		Optional<User> actual = actualUsers.stream().filter(u -> u.getId().equals(expected.getId())).findFirst();
		assertTrue(actual.isPresent());
		assertUserFieldsEqual(expected, actual.get());
	}
	
	private void assertUserFieldsEqual(User expected, User actual) {
		assertEquals(expected.getCity(), actual.getCity());
		assertEquals(expected.getCountry(), actual.getCountry());
		assertEquals(expected.getCompany(), actual.getCompany());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getOrganizationType(), actual.getOrganizationType());
		assertEquals(expected.getPhone(), actual.getPhone());
		assertEquals(expected.getState(), actual.getState());
		assertEquals(expected.getZipCode(), actual.getZipCode());
		assertEquals(expected.getDisclaimerAccepted(), actual.getDisclaimerAccepted());
		assertEquals(expected.getLanguageCode(), actual.getLanguageCode());
		assertEquals(expected.getEmailAddress(), actual.getEmailAddress());
		assertEquals(expected.getRegistrationId(), actual.getRegistrationId());
		assertEquals(expected.getRegistrationIdGeneratedTime(), actual.getRegistrationIdGeneratedTime());
	}
}
