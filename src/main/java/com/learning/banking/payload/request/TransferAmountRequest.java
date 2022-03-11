package com.learning.banking.payload.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAmountRequest {
	@NotNull
	private Long fromAccNumber;
	@NotNull
	private Long toAccNumber;
	@NotNull
	private BigDecimal amount;
	private String reason;
	@NotNull
	private String byStaff;
}
