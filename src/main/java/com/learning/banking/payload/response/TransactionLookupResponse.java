package com.learning.banking.payload.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLookupResponse {
	private LocalDate date;
	private String reference;
	private BigDecimal amount;
	private String transactionType;
}
