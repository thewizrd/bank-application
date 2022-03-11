package com.learning.banking.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffApproveBeneficiaryRequest
 *
 * @author bryan
 * @date Mar 6, 2022-7:08:44 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryRequest {
	@Positive
	@NotNull
	private Long fromCustomerAccNo;
	@Positive
	@NotNull
	private Long beneficiaryAccNo;
	//private LocalDate beneficiaryAddedDate;
	private boolean approved = false;
}
