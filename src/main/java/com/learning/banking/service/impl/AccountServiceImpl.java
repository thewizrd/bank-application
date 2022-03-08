package com.learning.banking.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.banking.entity.Account;
import com.learning.banking.repo.AccountRepository;
import com.learning.banking.service.AccountService;

/**
 * AccountServiceImpl
 *
 * @author bryan
 * @date Mar 8, 2022-4:35:41 PM
 */
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository repo;

	@Override
	public Optional<Account> getAccountByAccountNumber(long accountNumber) {
		return repo.findById(accountNumber);
	}
}
