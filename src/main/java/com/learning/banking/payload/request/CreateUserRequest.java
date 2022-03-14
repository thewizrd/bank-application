package com.learning.banking.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	@NotBlank
	private String password;
	@NotBlank
	private String phone;
	@NotBlank
	private String aadhar;
	@NotBlank
	private String pan;
	@NotBlank
	private String secretQuestion;
	@NotBlank
	private String secretAnswer;
	@NotEmpty
	private Set<String> roles;
}
