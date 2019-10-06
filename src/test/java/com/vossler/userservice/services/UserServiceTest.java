package com.vossler.userservice.services;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.vossler.userservice.dto.User;
import com.vossler.userservice.services.DataService;
import com.vossler.userservice.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@InjectMocks UserService service;
	@Mock DataService dataService;
	
	@Test
	public void testGetUnregisteredUsers() {
		List<User> users = Arrays.asList(randomUser(), randomUser(), randomUser());
		when(dataService.getUnregisteredUsers()).thenReturn(users);

		Map<String, User> results = service.getUnregisteredUsers();
		assertEquals(users.size(), results.size());
		assertEquals(users.get(0), results.get(users.get(0).getId()));
		assertEquals(users.get(1), results.get(users.get(1).getId()));
		assertEquals(users.get(2), results.get(users.get(2).getId()));
	}

	@Test
	public void testGetRegisteredUsers() {
		List<User> users = Arrays.asList(randomUser(), randomUser(), randomUser());
		when(dataService.getRegisteredUsers()).thenReturn(users);

		Map<String, User> results = service.getRegisteredUsers();
		assertEquals(users.size(), results.size());
		assertEquals(users.get(0), results.get(users.get(0).getId()));
		assertEquals(users.get(1), results.get(users.get(1).getId()));
		assertEquals(users.get(2), results.get(users.get(2).getId()));
	}
	
	@Test
	public void testGetUnregisteredUsersAsynchronously() throws Exception {
		List<User> users = Arrays.asList(randomUser(), randomUser(), randomUser());
		when(dataService.getUnregisteredUsers()).thenReturn(users);

		Map<String, User> results = service.getUnregisteredUsersAsynchronously().get();
		assertEquals(users.size(), results.size());
		assertEquals(users.get(0), results.get(users.get(0).getId()));
		assertEquals(users.get(1), results.get(users.get(1).getId()));
		assertEquals(users.get(2), results.get(users.get(2).getId()));
	}

	@Test
	public void testGetRegisteredUsersAsynchronously() throws Exception {
		List<User> users = Arrays.asList(randomUser(), randomUser(), randomUser());
		when(dataService.getRegisteredUsers()).thenReturn(users);

		Map<String, User> results = service.getRegisteredUsersAsynchronously().get();
		assertEquals(users.size(), results.size());
		assertEquals(users.get(0), results.get(users.get(0).getId()));
		assertEquals(users.get(1), results.get(users.get(1).getId()));
		assertEquals(users.get(2), results.get(users.get(2).getId()));
	}
	
	private User randomUser() {
		User u = new User();
		u.setId(randomAlphabetic(10));
		return u;
	}
	
}
