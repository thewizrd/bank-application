package com.learning.banking.payload.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffTransactionResponse
 *
 * @author bryan
 * @date Mar 11, 2022-1:00:55 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffTransactionResponse {
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
