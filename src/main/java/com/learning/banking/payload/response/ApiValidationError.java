package com.learning.banking.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ApiValidationError
 *
 * @author bryan
 * @date Feb 16, 2022-10:18:34 AM
 */
@Data
@EqualsAndHashCode(callSuper = false) // will not call super.hashcode and/or super.equals from base class
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	public ApiValidationError(String object, String message) {
		// TODO Auto-generated constructor stub
		this.object = object;
		this.message = message;
	}
}
