package com.learning.banking.payload.response;

import java.math.BigDecimal;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.AccountStatus;
import com.learning.banking.entity.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomerAccountsResponse
 *
 * @author bryan
 * @date Mar 4, 2022-5:29:54 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerAccountsResponse {
	private long accountNumber;

	private AccountType accountType;
	
	private BigDecimal accountBalance;

	private AccountStatus accountStatus;
	
	public GetCustomerAccountsResponse(Account account) {
		this.accountNumber = account.getAccountNumber();
		this.accountType = account.getAccountType();
		this.accountBalance = account.getAccountBalance();
		this.accountStatus = account.getAccountStatus();
	}
}
