package com.vossler.userservice.services;

import static com.vossler.userservice.services.DataService.MEMBERSHIPS_PATH;
import static com.vossler.userservice.services.DataService.REGISTERED_USERS_PATH;
import static com.vossler.userservice.services.DataService.UNREGISTERED_USERS_PATH;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.vossler.userservice.dto.ProjectMembership;
import com.vossler.userservice.dto.User;
import com.vossler.userservice.services.DataService;

@RunWith(MockitoJUnitRunner.class)
public class DataServiceTest {
	@InjectMocks DataService service;
	@Mock RestTemplate restTemplate;
	@Mock ResponseEntity<List<User>> userResponseEntity;
	@Mock ResponseEntity<List<ProjectMembership>> projectMembershipResponseEntity;
	
	static final String BASEURL = "https://baseUrl";
	
	ParameterizedTypeReference<List<User>> userListTypeRef = new ParameterizedTypeReference<List<User>>() {};
	ParameterizedTypeReference<List<ProjectMembership>> projectMembershipListTypeRef = new ParameterizedTypeReference<List<ProjectMembership>>() {};

	@Before
	public void setup() {
		ReflectionTestUtils.setField(service, "dataServiceBaseUrl", BASEURL);
	}
	
	@Test
	public void testGetUnregisteredUsers() {
		List<User> expected = Arrays.asList(randomUser(), randomUser(), randomUser());
		
		when(restTemplate.exchange(BASEURL + UNREGISTERED_USERS_PATH, HttpMethod.GET, null, userListTypeRef)).thenReturn(userResponseEntity);
		when(userResponseEntity.getBody()).thenReturn(expected);
		
		assertEquals(expected, service.getUnregisteredUsers());
	}

	@Test
	public void testGetRegisteredUsers() {
		List<User> expected = Arrays.asList(randomUser(), randomUser(), randomUser());
		
		when(restTemplate.exchange(BASEURL + REGISTERED_USERS_PATH, HttpMethod.GET, null, userListTypeRef)).thenReturn(userResponseEntity);
		when(userResponseEntity.getBody()).thenReturn(expected);
		
		assertEquals(expected, service.getRegisteredUsers());
	}	
	
	@Test
	public void testGetProjectMemberships() {
		List<ProjectMembership> expected = Arrays.asList(randomProjectMembership(), randomProjectMembership(), randomProjectMembership());
		
		when(restTemplate.exchange(BASEURL + MEMBERSHIPS_PATH, HttpMethod.GET, null, projectMembershipListTypeRef)).thenReturn(projectMembershipResponseEntity);
		when(projectMembershipResponseEntity.getBody()).thenReturn(expected);
		
		assertEquals(expected, service.getProjectMemberships());
	}
	
	public void testGetUnregisteredUsers_DoesNotSwallowExceptions() {
		HttpServerErrorException ex = new HttpServerErrorException(HttpStatus.I_AM_A_TEAPOT);
		when(restTemplate.exchange(BASEURL + UNREGISTERED_USERS_PATH, HttpMethod.GET, null, userListTypeRef)).thenThrow(ex);
		
		try {
			service.getUnregisteredUsers();
			fail("Should have thrown the exception thrown by restTemplate");
		} catch (RuntimeException e) {
			assertEquals(ex, e);
		}
	}

	@Test
	public void testGetRegisteredUsers_DoesNotSwallowExceptions() {
		HttpServerErrorException ex = new HttpServerErrorException(HttpStatus.I_AM_A_TEAPOT);
		when(restTemplate.exchange(BASEURL + REGISTERED_USERS_PATH, HttpMethod.GET, null, userListTypeRef)).thenThrow(ex);
		
		try {
			service.getRegisteredUsers();
			fail("Should have thrown the exception thrown by restTemplate");
		} catch (RuntimeException e) {
			assertEquals(ex, e);
		}
	}	
	
	@Test
	public void testGetProjectMemberships_DoesNotSwallowExceptions() {
		HttpServerErrorException ex = new HttpServerErrorException(HttpStatus.I_AM_A_TEAPOT);
		when(restTemplate.exchange(BASEURL + MEMBERSHIPS_PATH, HttpMethod.GET, null, projectMembershipListTypeRef)).thenThrow(ex);
		
		try {
			service.getProjectMemberships();
			fail("Should have thrown the exception thrown by restTemplate");
		} catch (RuntimeException e) {
			assertEquals(ex, e);
		}
	}
	
	private ProjectMembership randomProjectMembership() {
		ProjectMembership pm = new ProjectMembership();
		pm.setId(randomAlphanumeric(10));
		return pm;
	}

	private User randomUser() {
		User u = new User();
		u.setId(randomAlphanumeric(10));
		return u;
	}
}
