package com.learning.banking.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdateCustomerStatusRequest
 *
 * @author bryan
 * @date Mar 14, 2022-12:55:35 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerStatusRequest {
	@Positive
	@NotNull
	private Long customerId;
	@NotNull
	private CustomerStatus status;
}
