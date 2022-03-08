package com.learning.banking.payload.response;

import java.time.LocalDate;

import com.learning.banking.entity.Beneficiary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffGetBeneficiariesResponse
 *
 * @author bryan
 * @date Mar 6, 2022-6:41:22 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffGetBeneficiariesResponse {
	private long fromCustomerID;
	private long beneficiaryAccNo;
	private LocalDate beneficiaryAddedDate;
	private boolean approved;
	
	public StaffGetBeneficiariesResponse(Beneficiary beneficiary) {
		this.fromCustomerID = beneficiary.getBeneficiaryOf().getCustomerID();
		this.beneficiaryAccNo = beneficiary.getAccount().getAccountNumber();
		this.beneficiaryAddedDate = beneficiary.getAddedDate();
		this.approved = beneficiary.isApproved();
	}
}
