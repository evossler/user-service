package com.vossler.userservice.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vossler.userservice.dto.User;
import com.vossler.userservice.services.UserProjectMembershipService;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserProjectMembershipController {

	@Autowired private UserProjectMembershipService service;

	@GetMapping("/users")
	@ApiOperation(value = "Get Users", notes = "Gets Users both registered and unregistered, with project membership information included.")
	public List<User> getUsers() {
		return service.getUsers();
	}	

}
