package com.learning.banking.payload.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.learning.banking.enums.AccountType;
import com.learning.banking.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAmountRequest {
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
	@NotBlank
	private String byStaff;
	
	@JsonFormat(with = { Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, Feature.ACCEPT_CASE_INSENSITIVE_VALUES }) 
	private TransactionType transactionType;
}
