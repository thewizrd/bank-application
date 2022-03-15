package com.learning.banking.payload.response;

import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCustomersResponse {
	private long customerId;
	private String customerName;
	private CustomerStatus status;
}
