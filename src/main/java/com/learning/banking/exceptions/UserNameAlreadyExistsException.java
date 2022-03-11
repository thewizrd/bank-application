package com.learning.banking.exceptions;

import java.util.Arrays;

public class UserNameAlreadyExistsException extends RuntimeException {
	public UserNameAlreadyExistsException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
	
}
