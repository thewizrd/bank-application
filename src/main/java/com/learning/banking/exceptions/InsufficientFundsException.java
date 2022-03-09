package com.learning.banking.exceptions;

/**
 * InsufficientFundsException
 *
 * @author bryan
 * @date Mar 9, 2022-2:55:56 PM
 */
public class InsufficientFundsException extends Exception {
	public InsufficientFundsException() {
		super();
	}

	public InsufficientFundsException(String message) {
		super(message);
	}
	
}
