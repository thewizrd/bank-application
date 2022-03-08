package com.learning.banking.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

/**
 * ApproveAccountRequest
 *
 * @author bryan
 * @date Mar 4, 2022-5:28:29 PM
 */
@Data
public class StaffApproveAccountRequest {
	@Positive
	@NotNull
	private Long accountNumber;
	private boolean approved;
}