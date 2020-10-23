package com.example.portfolio;

public class DataNotFoundException extends RuntimeException{

	DataNotFoundException() {
	}

	DataNotFoundException(String message) {
		super(message);
	}
}
