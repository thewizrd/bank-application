package com.learning.banking.exceptions;

public class RolePermissionsException extends RuntimeException {
	
	public RolePermissionsException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return super.getMessage();
	}
}
