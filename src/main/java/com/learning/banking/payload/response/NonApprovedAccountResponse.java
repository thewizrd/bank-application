package com.learning.banking.payload.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonApprovedAccountResponse {
	private String accountType;
	private String customerName;
	private long accountNumber;
	private LocalDate dateCreated;
	private String approved;
}
