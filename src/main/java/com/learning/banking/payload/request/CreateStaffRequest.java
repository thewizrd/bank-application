package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateStaffRequest
 *
 * @author bryan
 * @date Mar 14, 2022-9:43:28 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStaffRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	@NotBlank
	private String password;

	/*
	@NotBlank
	private String secretQuestion;
	@NotBlank
	private String secretAnswer;
	*/
}
