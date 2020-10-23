package com.example.portfolio;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@ExceptionHandler
//	@ResponseStatus(HttpStatus.NOT_FOUND)
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
