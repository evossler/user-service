package com.vossler.userservice.controllers.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ HttpServerErrorException.class })
	public final ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException ex, WebRequest request)throws Exception {
		return handleExceptionInternal(ex, null, new HttpHeaders(), ex.getStatusCode(), request);
	}
	
}
