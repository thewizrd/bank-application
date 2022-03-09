package com.learning.banking.service;

import java.util.List;
import java.util.Optional;

import com.learning.banking.entity.Account;

/**
 * AccountService
 *
 * @author bryan
 * @date Mar 8, 2022-4:34:37 PM
 */
public interface AccountService {
	Optional<Account> getAccountByAccountNumber(long accountNumber);
	List<Account> updateAccounts(Iterable<Account> accounts);
}
