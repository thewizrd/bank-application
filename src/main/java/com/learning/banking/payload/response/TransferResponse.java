package com.learning.banking.payload.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TransferResponse
 *
 * @author bryan
 * @date Mar 9, 2022-3:26:41 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
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
}