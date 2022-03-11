package com.learning.banking.payload.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedAccountResponse {
	private String accountType;
	private String firstName;
	private String lastName;
	private long accountNumber;
	private LocalDate dateCreated;
	private String approved;
	private String username;
}
