package com.learning.banking.payload.response;

import java.time.LocalDate;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffApproveAccountsResponse
 *
 * @author bryan
 * @date Mar 6, 2022-7:14:52 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffApproveAccountsResponse {
	private AccountType accountType;
	private String customerName;
	private long accountNumber;
	private LocalDate dateCreated;
	private boolean approved;
	
	public StaffApproveAccountsResponse(Account account) {
		this.accountType = account.getAccountType();
		this.customerName = account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName();
		this.accountNumber = account.getAccountNumber();
		this.dateCreated = account.getDateOfCreation().toLocalDate();
		this.approved = account.isApproved();
	}
}
