package com.example.portfolio.controller;

import java.lang.invoke.MethodHandles;

import com.example.portfolio.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<String> handle(Exception e) {
		log.info("Exception caught : {}, returning 404", e.getMessage());
		if (e instanceof DataNotFoundException){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Unknown Exception", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
