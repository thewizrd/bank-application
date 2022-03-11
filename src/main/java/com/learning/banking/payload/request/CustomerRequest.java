package com.learning.banking.payload.request;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.Role;
import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
	private long customerId;
	private String username;
	private String fristName;
	private String lastName;
	private String password;
	private String phone;
	private String pan;
	private String aadhar;
	private String secretQuestion;
	private String secretAnswer;
	private LocalDate createDate;
	@Enumerated(EnumType.STRING)
	private CustomerStatus customerStatus;
	private Set<String> roles;
	private Set<Account> accounts; 
}
