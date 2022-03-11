package com.learning.banking.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
	
	private Set<String> roles;
}
