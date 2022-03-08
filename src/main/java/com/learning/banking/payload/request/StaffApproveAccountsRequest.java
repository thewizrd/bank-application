package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffApproveAccountsRequest
 *
 * @author bryan
 * @date Mar 6, 2022-7:18:07 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffApproveAccountsRequest {
	@Positive
	@NotNull
	private Long accountNumber;
	private boolean approved = false;
	@NotBlank
	private String staffUserName;
}
