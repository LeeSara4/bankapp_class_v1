package com.dung.bankapp.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends RuntimeException {
	private HttpStatus status;

	public UnAuthorizedException(String message, HttpStatus httpStatus) {
		super(message);
		this.status = httpStatus;
	}
}
