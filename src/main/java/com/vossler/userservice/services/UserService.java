package com.vossler.userservice.services;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.vossler.userservice.dto.User;

/**
 * This service provides both synchronous and asynchronous operations for getting registered and unregistered users from the data service,
 * organizing them into a Map with the user's id as key.
 */
@Component
public class UserService {
	
	@Autowired private DataService dataService;
	
	@Async
	public Future<Map<String, User>> getUnregisteredUsersAsynchronously() {
		return new AsyncResult<>(getUnregisteredUsers());
	}
	
	public Map<String, User> getUnregisteredUsers() {
		return dataService.getUnregisteredUsers().stream().collect(Collectors.toMap(u -> u.getId(), u -> u));
	}
	
	@Async
	public Future<Map<String, User>> getRegisteredUsersAsynchronously() {
		return new AsyncResult<>(getRegisteredUsers());
	}
	
	public Map<String, User> getRegisteredUsers() {
		return dataService.getRegisteredUsers().stream().collect(Collectors.toMap(u -> u.getId(), u -> u));
	}
}
