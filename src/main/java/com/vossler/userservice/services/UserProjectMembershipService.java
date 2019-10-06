package com.vossler.userservice.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import com.vossler.userservice.dto.ProjectMembership;
import com.vossler.userservice.dto.User;
import com.vossler.userservice.util.UserIdComparator;

/**
 * This service performs the work of combining the information from ProjectMembershipService, with the information from the UserService to produce a single
 * List of User DTOs, with projectId fields populated.
 */
@Component
public class UserProjectMembershipService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProjectMembershipService.class);
	
	@Autowired private UserService userService;
	@Autowired private ProjectMembershipService membershipService;
	
	public List<User> getUsers() {

		try {
			// Launch asynchronous tasks to get the three chunks of data we wish to combine from from the external API
			Future<Map<String, User>> unregisteredUsers = userService.getUnregisteredUsersAsynchronously();
			Future<Map<String, User>> registeredUsers = userService.getRegisteredUsersAsynchronously();
			Future<List<ProjectMembership>> projectMemberships = membershipService.getProjectMembershipsAsynchronously();

			// NOTE: I am combining the users into one Map here.  This is on the assumption that the same user id should not appear in both the unregistered users 
			// and the registered users, or that when they do (say because a user transitioned from one state to the other in the time between the two calls), 
			// that it is acceptable to include only the registered one.  If some other outcome were desired, such as including both, or merging the two into one User DTO,
			// The implementation here would be slightly different.
			Map<String, User> users = new HashMap<>(unregisteredUsers.get());
			users.putAll(registeredUsers.get());
			
			// Apply the membership facts to the users.  Membership facts that do not relate to one of the users are discarded.
			projectMemberships.get().forEach(pm -> {
				if (users.containsKey(pm.getUserId())) users.get(pm.getUserId()).addProjectId(pm.getProjectId());
			});

			// Sort the results by user id
			List<User> combinedUsers = new ArrayList<>(users.values());
			Collections.sort(combinedUsers, new UserIdComparator());
			
			return combinedUsers;
			
		} catch (RuntimeException | InterruptedException | ExecutionException e) {
			LOGGER.error("An Unexpected Exception has occurred.", e);
			
			if (e instanceof HttpServerErrorException) throw (HttpServerErrorException) e;
			else if (e instanceof ExecutionException && e.getCause() instanceof HttpServerErrorException) throw (HttpServerErrorException) e.getCause();
			else throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}	
}
