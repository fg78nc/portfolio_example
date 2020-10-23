package com.example.portfolio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException{

	DataNotFoundException() {
	}

	DataNotFoundException(String message) {
		super(message);
	}
}
