package com.learning.banking.payload.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.learning.banking.entity.Account;
import com.learning.banking.enums.AccountType;

import lombok.Data;

/**
 * CreateAccountResponse
 *
 * @author bryan
 * @date Mar 6, 2022-4:43:33 PM
 */
@Data
public class CreateAccountResponse {
	private AccountType accountType;
	private BigDecimal accountBalance;
	private boolean approved;
	private long accountNumber;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime dateOfCreation;
	private long customerID;
	
	public CreateAccountResponse(Account account) {
		this.accountType = account.getAccountType();
		this.accountBalance = account.getAccountBalance();
		this.approved = account.isApproved();
		this.accountNumber = account.getAccountNumber();
		this.dateOfCreation = account.getDateOfCreation();
		this.customerID = account.getCustomer().getCustomerID();
	}
}
