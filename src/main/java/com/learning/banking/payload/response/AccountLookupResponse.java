package com.learning.banking.payload.response;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountLookupResponse {
	private long accountNumber;
	private String firstName;
	private String lastName;
	private BigDecimal balance;
	private Set<TransactionLookupResponse> transactions = new HashSet<TransactionLookupResponse>();
}
