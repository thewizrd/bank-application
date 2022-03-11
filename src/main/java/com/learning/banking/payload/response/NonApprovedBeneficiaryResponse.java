package com.learning.banking.payload.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonApprovedBeneficiaryResponse {
	private long fromCustomer;
	private long beneficiaryAccountNumber;
	private LocalDate dateAdded;
	private String isApproved;
}
