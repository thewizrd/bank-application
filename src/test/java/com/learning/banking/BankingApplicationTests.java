package com.learning.banking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.learning.banking.repo.CustomerRepository;

@SpringBootTest
class BankingApplicationTests {
	
	@Autowired
	CustomerRepository customerRepository;

	

}
