package com.learning.banking.payload.response;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.learning.banking.entity.Account;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * StaffAccountStatementResponse
 *
 * @author bryan
 * @date Mar 6, 2022-6:37:18 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffAccountStatementResponse {
	private long accountNumber;
	private String customerName;
	private BigDecimal balance;
	private List<TransactionsResponse> transaction;
	
	public StaffAccountStatementResponse(Account account) {
		this.accountNumber = account.getAccountNumber();
		this.customerName = account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName();
		this.balance = account.getAccountBalance();
		
		if (account.getTransactions() != null) {
			this.transaction = account.getTransactions().stream().map(t -> {
				return new TransactionsResponse(t);
			}).collect(Collectors.toList());
		} else {
			this.transaction = Collections.emptyList();
		}
	}
}
