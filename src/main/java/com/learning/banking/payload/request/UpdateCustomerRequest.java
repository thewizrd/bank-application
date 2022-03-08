package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdateCustomerRequest
 *
 * @author bryan
 * @date Mar 6, 2022-4:54:52 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
	@Positive
	@NotNull
	private Long customerId;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String phone;
	@NotBlank
	private String pan;
	@NotBlank
	private String aadhar;
	@NotBlank
	private String secretQuestion;
	@NotBlank
	private String secretAnswer;

	/*
	private Object pan; // multipart?
	private Object aarchar; // multipart?
	*/
}
