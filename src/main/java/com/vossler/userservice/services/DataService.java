package com.vossler.userservice.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.vossler.userservice.dto.ProjectMembership;
import com.vossler.userservice.dto.User;

/**
 * Service that serves as a client to the external data services.  In a more complex project, I might divide this class into multiple services, for example, one for
 * user data and another for membership data, but in this case, given all three calls are to the same external API, it makes sense to collect them here.
 */
@Component
public class DataService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);
	
	static final String REGISTERED_USERS_PATH = "/registeredusers";
	static final String UNREGISTERED_USERS_PATH = "/unregisteredusers";
	static final String MEMBERSHIPS_PATH = "/projectmemberships";
	
	@Value("${dataServiceBaseUrl}") private String dataServiceBaseUrl;
	
	@Autowired private RestTemplate restTemplate;
	
	ParameterizedTypeReference<List<User>> userListTypeRef = new ParameterizedTypeReference<List<User>>() {};
	ParameterizedTypeReference<List<ProjectMembership>> projectMembershipListTypeRef = new ParameterizedTypeReference<List<ProjectMembership>>() {};
	
	public List<User> getUnregisteredUsers() {
		String endpointUrl = dataServiceBaseUrl + UNREGISTERED_USERS_PATH;
		try {
			return restTemplate.exchange(endpointUrl, HttpMethod.GET, null, userListTypeRef).getBody();
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Unable to retrieve unregistered users from {}.  HttpStatus: {}\nResponse Body: {}", endpointUrl, e.getStatusCode(), e.getResponseBodyAsString());
			throw e;
		}
	}
	
	public List<User> getRegisteredUsers() {
		String endpointUrl = dataServiceBaseUrl + REGISTERED_USERS_PATH;
		try {
			return restTemplate.exchange(endpointUrl, HttpMethod.GET, null, userListTypeRef).getBody();
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Unable to retrieve registered users from {}.  HttpStatus: {}\nResponse Body: {}", endpointUrl, e.getStatusCode(), e.getResponseBodyAsString());
			throw e;
		}
	}
	
	public List<ProjectMembership> getProjectMemberships() {
		String endpointUrl = dataServiceBaseUrl + MEMBERSHIPS_PATH;
		try {
			return restTemplate.exchange(endpointUrl, HttpMethod.GET, null, projectMembershipListTypeRef).getBody();
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Unable to retrieve project memberships from {}.  HttpStatus: {}\nResponse Body: {}", endpointUrl, e.getStatusCode(), e.getResponseBodyAsString());
			throw e;
		}
	}
}
