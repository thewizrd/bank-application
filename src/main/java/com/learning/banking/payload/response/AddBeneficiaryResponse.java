package com.learning.banking.payload.response;

import com.learning.banking.entity.AccountType;
import com.learning.banking.entity.Beneficiary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AddBeneficiaryResponse
 *
 * @author bryan
 * @date Mar 8, 2022-4:52:25 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBeneficiaryResponse {
	private Long accountNumber;
	private AccountType accountType;
	private boolean approved;
	
	public AddBeneficiaryResponse(Beneficiary beneficiary) {
		this.accountNumber = beneficiary.getAccount().getAccountNumber();
		this.accountType = beneficiary.getAccount().getAccountType();
		this.approved = beneficiary.isApproved();
	}
}