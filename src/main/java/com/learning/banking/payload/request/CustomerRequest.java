package com.learning.banking.payload.request;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.Role;
import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
	@Positive
	@NotNull
	private Long customerId;
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

	private String pan;
	private String aadhar;
	
	@NotBlank
	private String secretQuestion;
	@NotBlank
	private String secretAnswer;

	@NotNull
	private LocalDate createDate;
	@NotNull
	@Enumerated(EnumType.STRING)
	private CustomerStatus customerStatus;
	@NotEmpty
	private Set<String> roles;
}
