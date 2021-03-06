package com.learning.banking.payload.response;

import com.learning.banking.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomerResponse
 *
 * @author bryan
 * @date Mar 4, 2022-5:32:47 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCustomerResponse {
	private Long id;

	private String username;
	private String firstName;
	private String lastName;
	
	public RegisterCustomerResponse(Customer customer) {
		this.id = customer.getCustomerID();
		this.username = customer.getUsername();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
	}
}
