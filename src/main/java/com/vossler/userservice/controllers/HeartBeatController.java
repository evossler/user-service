package com.vossler.userservice.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class HeartBeatController {
	
	@GetMapping("/")
	@ApiOperation(value = "Heart Beat", notes = "A meaningless endpoint that can be accessed to confirm that the service is running.")
	public String helloWorld() {
		return "200 OK";
	}	

}
