package com.learning.banking.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCustomersResponse {
	private long customerId;
	private String customerName;
	private String status;
}
