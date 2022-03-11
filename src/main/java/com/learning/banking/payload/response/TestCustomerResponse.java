package com.learning.banking.payload.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.learning.banking.entity.Customer;
import com.learning.banking.entity.Role;
import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TestCustomerResponse
 *
 * @author bryan
 * @date Mar 9, 2022-5:57:31 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCustomerResponse {
	private long customerID;
	private String customerName;
	private String username;
	private String password;
	private Set<Role> roles;
	private String secretQuestion;
	private String secretAnswer;
	
	private String phone;
	
	private String pan;
	private String aadhar;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateCreated;
	
	private CustomerStatus status;
	
	private List<AccountDetailsResponse> accounts;
	private List<BeneficiaryResponse> beneficiaries;
	
	public TestCustomerResponse(Customer customer) {
		this.customerID = customer.getCustomerID();
		this.customerName = customer.getFullName();
		this.username = customer.getUsername();
		this.password = customer.getPassword();
		this.roles = customer.getRoles();
		this.secretQuestion = customer.getSecretQuestion();
		this.secretAnswer = customer.getSecretAnswer();
		this.phone = customer.getPhone();
		this.pan = customer.getPan();
		this.aadhar = customer.getAadhar();
		
		this.dateCreated = customer.getDateCreated();
		
		this.status = customer.getStatus();
		
		this.accounts = customer.getAccounts().stream().map(acc -> {
			return new AccountDetailsResponse(acc);
		}).collect(Collectors.toList());
		
		this.beneficiaries = customer.getBeneficiaries().stream().map(b -> {
			return new BeneficiaryResponse(b);
		}).collect(Collectors.toList());
	}
}
