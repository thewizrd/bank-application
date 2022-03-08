package com.learning.banking.exceptions;

/**
 * NoRecordsFoundException
 *
 * @author bryan
 * @date Mar 8, 2022-3:33:31 PM
 */
public class NoRecordsFoundException extends Exception {
	public NoRecordsFoundException() {
		super();
	}

	public NoRecordsFoundException(String message) {
		super(message);
	}
}
