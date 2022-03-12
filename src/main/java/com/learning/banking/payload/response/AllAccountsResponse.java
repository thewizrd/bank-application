package com.learning.banking.payload.response;

import java.math.BigDecimal;
import java.util.List;

import com.learning.banking.enums.AccountStatus;
import com.learning.banking.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllAccountsResponse {
	private long accountNumber;

	private AccountType accountType;
	
	private BigDecimal accountBalance;

	private AccountStatus accountStatus;
}
