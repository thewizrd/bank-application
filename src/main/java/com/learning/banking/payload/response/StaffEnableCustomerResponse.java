package com.learning.banking.payload.response;

import com.learning.banking.entity.Customer;
import com.learning.banking.entity.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StaffEnableCustomerResponse
 *
 * @author bryan
 * @date Mar 6, 2022-7:40:03 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffEnableCustomerResponse {
	private long customerId;
	private CustomerStatus status;
	
	public StaffEnableCustomerResponse(Customer customer) {
		this.customerId = customer.getCustomerID();
		this.status = customer.getStatus();
	}
}