package com.learning.banking.payload.response;

import com.learning.banking.entity.Customer;

import lombok.Data;

/**
 * CustomerRegistrationResponse
 *
 * @author bryan
 * @date Mar 6, 2022-4:44:01 PM
 */
@Data
public class CustomerRegistrationResponse {
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	
	public CustomerRegistrationResponse(Customer customer) {
		this.id = customer.getCustomerID();
		this.username = customer.getUsername();
		this.password = customer.getPassword();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
	}
}
