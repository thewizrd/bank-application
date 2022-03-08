package com.learning.banking.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.learning.banking.entity.AccountType;

import lombok.Data;

/**
 * AddBeneficiaryRequest
 *
 * @author bryan
 * @date Mar 6, 2022-5:31:29 PM
 */
@Data
public class AddBeneficiaryRequest {
	@Positive
	@NotNull
	private Long accountNumber;
	@NotNull
	@JsonFormat(with = { Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, Feature.ACCEPT_CASE_INSENSITIVE_VALUES }) 
	private AccountType accountType;
	//private boolean approved = false;
}
