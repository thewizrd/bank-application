package com.learning.banking.payload.response;

import javax.validation.constraints.Positive;

import lombok.Data;

/**
 * ApproveAccountResponse
 *
 * @author bryan
 * @date Mar 6, 2022-4:51:20 PM
 */
@Data
public class StaffApproveAccountResponse {
	@Positive
	private long accountNumber;
	private String approved;
}