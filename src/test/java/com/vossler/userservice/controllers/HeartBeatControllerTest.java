package com.vossler.userservice.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.vossler.userservice.controllers.HeartBeatController;

@RunWith(MockitoJUnitRunner.class)
public class HeartBeatControllerTest {
	@InjectMocks HeartBeatController controller;
	
	@Test
	public void testHello() {
		assertEquals("200 OK", controller.helloWorld());
	}
}
