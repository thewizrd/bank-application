package com.learning.banking.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffEnableCustomerRequest
 *
 * @author bryan
 * @date Mar 6, 2022-7:34:52 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffEnableCustomerRequest {
	@Positive
	@NotNull
	private Long customerId;
	@NotNull
	@JsonFormat(with = { Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, Feature.ACCEPT_CASE_INSENSITIVE_VALUES }) 
	private CustomerStatus status;
}
