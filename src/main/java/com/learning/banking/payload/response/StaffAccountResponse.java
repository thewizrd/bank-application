package com.learning.banking.payload.response;

import java.sql.Date;
import java.util.Set;

import com.learning.banking.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffAccountResponse
 *
 * @author bryan
 * @date Mar 11, 2022-1:03:14 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffAccountResponse {
	private AccountType accountType;
	private Double accountBalance;
	private String approved;
	private Long accountNumber;
	private Date dateOfCreation;
	private Long customerId;
	private String customerName;
	private Set<TransactionsResponse> transactions;
}
