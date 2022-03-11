package com.learning.banking.exceptions;

public class TransferException extends RuntimeException {
	public TransferException(String msg) {
		super(msg);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
	
	
}
