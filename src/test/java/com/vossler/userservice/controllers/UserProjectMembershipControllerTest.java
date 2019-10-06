package com.vossler.userservice.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.vossler.userservice.controllers.UserProjectMembershipController;
import com.vossler.userservice.dto.User;
import com.vossler.userservice.services.UserProjectMembershipService;

@RunWith(MockitoJUnitRunner.class)
public class UserProjectMembershipControllerTest {
	@InjectMocks UserProjectMembershipController controller;
	@Mock UserProjectMembershipService service;
	
	@Test
	public void testGetUsers() {
		List<User> expected = Arrays.asList(new User().setId("1"), new User().setId("2"));
		when(service.getUsers()).thenReturn(expected);
		
		assertEquals(expected, controller.getUsers());
		verify(service).getUsers();
	}
}
