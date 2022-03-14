package com.learning.banking;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.learning.banking.repo.AccountRepository;
import com.learning.banking.repo.CustomerRepository;

@SpringBootTest
class BankingApplicationTests {
	
	
	@Autowired
	AccountRepository accountRepo;

	@Test
	void findAllAccount() {
		
		accountRepo.findAccountsByCustomerCustomerID(1L);
	}

}
