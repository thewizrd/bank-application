package com.learning.banking.payload.response;

import java.time.LocalDate;

import com.learning.banking.entity.Customer;
import com.learning.banking.entity.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffGetCustomersResponse
 *
 * @author bryan
 * @date Mar 6, 2022-7:32:15 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffGetCustomersResponse {
	private long customerId;
	private String customerName;
	private CustomerStatus status;
	private LocalDate created;
	
	public StaffGetCustomersResponse(Customer customer) {
		this.customerId = customer.getCustomerID();
		this.customerName = customer.getFullName();
		this.status = customer.getStatus();
		this.created = customer.getDateCreated().toLocalDate();
	}
}
