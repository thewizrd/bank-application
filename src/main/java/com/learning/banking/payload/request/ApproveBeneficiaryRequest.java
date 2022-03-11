package com.learning.banking.payload.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveBeneficiaryRequest {
	private long customerId;
	private long beneficiaryAccountNumber;
	private LocalDate dateOfApproval;
	private String isApproved;
}
