package com.learning.banking.payload.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.learning.banking.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TransferRequest
 *
 * @author bryan
 * @date Mar 6, 2022-5:38:54 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
	@Positive
	@NotNull
	private Long fromAccNumber;
	@Positive
	@NotNull
	private Long toAccNumber;
	@Positive
	@NotNull
	private BigDecimal amount;
	@NotBlank
	private String reason;
	@Positive
	@NotNull
	private Long by; // Customer or Staff ID or username? who initiated transfer
	
	@JsonFormat(with = { Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, Feature.ACCEPT_CASE_INSENSITIVE_VALUES }) 
	private TransactionType transactionType;
}
