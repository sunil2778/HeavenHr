package com.heavenhr.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DuplicateEntryException extends RuntimeException{

	public DuplicateEntryException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DuplicateEntryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
