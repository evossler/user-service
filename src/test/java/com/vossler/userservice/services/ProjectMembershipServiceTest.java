package com.vossler.userservice.services;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import com.vossler.userservice.dto.ProjectMembership;
import com.vossler.userservice.services.DataService;
import com.vossler.userservice.services.ProjectMembershipService;
@RunWith(MockitoJUnitRunner.class)
public class ProjectMembershipServiceTest {
	@InjectMocks ProjectMembershipService service;
	@Mock DataService dataService;
	
	@Before
	public void setup() {
	}
	
	@Test
	public void testGetProjectMemberships() {
		List<ProjectMembership> projectMemberships = Arrays.asList(randomProjectMembership(), randomProjectMembership(), randomProjectMembership());
		when(dataService.getProjectMemberships()).thenReturn(projectMemberships);
		
		assertEquals(projectMemberships, service.getProjectMemberships());
		
		verify(dataService).getProjectMemberships();
	}
	
	@Test
	public void testGetProjectMembershipsAsynchronously() throws Exception {
		List<ProjectMembership> projectMemberships = Arrays.asList(randomProjectMembership(), randomProjectMembership(), randomProjectMembership());
		when(dataService.getProjectMemberships()).thenReturn(projectMemberships);
		
		assertEquals(projectMemberships, service.getProjectMembershipsAsynchronously().get());
		
		verify(dataService).getProjectMemberships();
	}

	@Test
	public void testGetProjectMemberships_ExceptionThrown() {
		HttpServerErrorException ex = new HttpServerErrorException(HttpStatus.I_AM_A_TEAPOT);
		when(dataService.getProjectMemberships()).thenThrow(ex);
		
		try {
			service.getProjectMemberships();
			fail("Should have thrown exception.");
		} catch (HttpServerErrorException e) {
			assertEquals(ex, e);
		}
		
		verify(dataService).getProjectMemberships();
	}
	
	private ProjectMembership randomProjectMembership() {
		return new ProjectMembership().setId(randomNumeric(3)).setProjectId(randomNumeric(3)).setUserId(randomNumeric(3));
	}
}
