package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * CustomerRegistration
 *
 * @author bryan
 * @date Mar 4, 2022-5:14:02 PM
 */
@Data
public class CustomerRegistrationRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String password;
	@NotBlank
	private String confirmPassword;
}
