package com.learning.banking.payload.response;

import java.time.LocalDate;

import com.learning.banking.entity.Beneficiary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffApproveBeneficiaryResponse
 *
 * @author bryan
 * @date Mar 6, 2022-7:10:47 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffApproveBeneficiaryResponse {
	private Long fromCustomerID;
	private Long beneficiaryAccNo;
	private LocalDate beneficiaryAddedDate;
	private boolean approved;
	
	public StaffApproveBeneficiaryResponse(Beneficiary beneficiary) {
		this.fromCustomerID = beneficiary.getBeneficiaryOf().getCustomerID();
		this.beneficiaryAccNo = beneficiary.getAccount().getAccountNumber();
		this.beneficiaryAddedDate = beneficiary.getAddedDate();
		this.approved = beneficiary.isApproved();
	}
}
