package com.example.portfolio;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@ExceptionHandler(com.example.portfolio.DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handle() {
		log.info("Exception caught, returning 404");
	}
}
