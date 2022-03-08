package com.learning.banking.payload.response;

import com.learning.banking.entity.Customer;

import lombok.Data;

/**
 * CustomerResponse
 *
 * @author bryan
 * @date Mar 4, 2022-5:32:47 PM
 */
@Data
public class CustomerResponse {
	private String username;
	private String firstName;
	private String lastName;
	
	private String phone;
	
	private String pan;
	
	private String aadhar;
	
	public CustomerResponse(Customer customer) {
		this.username = customer.getUsername();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();

		this.phone = customer.getPhone();
		
		this.pan = customer.getPan();
		this.aadhar = customer.getAadhar();
	}
}
