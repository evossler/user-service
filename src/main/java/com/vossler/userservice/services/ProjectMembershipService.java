package com.vossler.userservice.services;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.vossler.userservice.dto.ProjectMembership;

/**
 * This service provides both synchronous and asynchronous operations for getting project memberships from the data service.
 */
@Component
public class ProjectMembershipService {
	
	@Autowired private DataService dataService;
	
	@Async
	public Future<List<ProjectMembership>> getProjectMembershipsAsynchronously() {
		return new AsyncResult<>(getProjectMemberships());
	}
	
	public List<ProjectMembership> getProjectMemberships() {
		return dataService.getProjectMemberships();
	}

}
