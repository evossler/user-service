package com.vossler.userservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.HttpServerErrorException;

import com.vossler.userservice.dto.ProjectMembership;
import com.vossler.userservice.dto.User;
import com.vossler.userservice.services.ProjectMembershipService;
import com.vossler.userservice.services.UserProjectMembershipService;
import com.vossler.userservice.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserProjectMembershipServiceTest {
	@InjectMocks UserProjectMembershipService service;
	@Mock UserService userService;
	@Mock ProjectMembershipService membershipService;
	
	@Before
	public void setup() {
		Map<String, User> registeredUsers = new LinkedHashMap<>();
		registeredUsers.put("user1", new User().setId("user1"));
		registeredUsers.put("user2", new User().setId("user2"));
		when(userService.getRegisteredUsersAsynchronously()).thenReturn(new AsyncResult<>(registeredUsers));

		Map<String, User> unregisteredUsers = new LinkedHashMap<>();
		registeredUsers.put("user3", new User().setId("user3"));
		registeredUsers.put("user4", new User().setId("user4"));
		when(userService.getUnregisteredUsersAsynchronously()).thenReturn(new AsyncResult<>(unregisteredUsers));
		
		List<ProjectMembership> projectMemberships = new ArrayList<>();
		projectMemberships.add(new ProjectMembership("1", "project1", "user1"));
		projectMemberships.add(new ProjectMembership("2", "project2", "user1"));
		projectMemberships.add(new ProjectMembership("3", "project2", "user2"));
		projectMemberships.add(new ProjectMembership("4", "project3", "user1"));
		projectMemberships.add(new ProjectMembership("5", "project3", "user3"));
		projectMemberships.add(new ProjectMembership("6", "project4", "user3"));
		
		when(membershipService.getProjectMembershipsAsynchronously()).thenReturn(new AsyncResult<>(projectMemberships));
	}
	
	@Test
	public void testGetUsers_HappyPath() {
		List<User> results = service.getUsers();
		
		assertEquals("user1", results.get(0).getId());
		assertEquals(3, results.get(0).getProjectIds().size());
		assertTrue(results.get(0).getProjectIds().contains("project1"));
		assertTrue(results.get(0).getProjectIds().contains("project2"));
		assertTrue(results.get(0).getProjectIds().contains("project3"));
		
		assertEquals("user2", results.get(1).getId());
		assertEquals(1, results.get(1).getProjectIds().size());
		assertTrue(results.get(1).getProjectIds().contains("project2"));
		
		assertEquals("user3", results.get(2).getId());
		assertEquals(2, results.get(2).getProjectIds().size());
		assertTrue(results.get(2).getProjectIds().contains("project3"));
		assertTrue(results.get(2).getProjectIds().contains("project4"));
		
		assertEquals("user4", results.get(3).getId());
		assertEquals(0, results.get(3).getProjectIds().size());
	}
	
	@Test
	public void testGetUsers_ExecutionExceptionCausedByHttpServerErrorException() {
		HttpServerErrorException ex = new HttpServerErrorException(HttpStatus.I_AM_A_TEAPOT);
		when(userService.getRegisteredUsersAsynchronously()).thenReturn(AsyncResult.forExecutionException(ex));
		
		try {
			service.getUsers();
			fail("Should have thrown exception");
		} catch (Exception e) {
			// Even though the AsyncResult wraps the original exception we have coded the UserProjectMembershipService to unwrap it again
			assertEquals(ex, e);
		}
	}

	@Test
	public void testGetUsers_ExecutionExceptionCausedByOtherException() {
		when(userService.getRegisteredUsersAsynchronously()).thenReturn(AsyncResult.forExecutionException(new RuntimeException("Boom")));
		
		try {
			service.getUsers();
			fail("Should have thrown exception");
		} catch (Exception e) {
			// UserProjectMembershipService should instead throw an HttpServerErrorException with status 500 for other unexpected exceptions
			assertTrue(e instanceof HttpServerErrorException);
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((HttpServerErrorException) e).getStatusCode());
		}
	}
	
	@Test
	public void testGetUsers_OtherException() {
		when(userService.getRegisteredUsersAsynchronously()).thenThrow(new RuntimeException("Boom"));
		
		try {
			service.getUsers();
			fail("Should have thrown exception");
		} catch (Exception e) {
			// UserProjectMembershipService should instead throw an HttpServerErrorException with status 500 for other unexpected exceptions
			assertTrue(e instanceof HttpServerErrorException);
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((HttpServerErrorException) e).getStatusCode());
		}
	}
}

	