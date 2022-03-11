package com.learning.banking.service;

import java.math.BigDecimal;

import com.learning.banking.entity.Transaction;

public interface TransactionService {
	public Transaction addTransaction(Transaction transaction);

	public void sendMoney(Long fromAccount, BigDecimal amount);

	public void getMoney(Long toAccount, BigDecimal amount);

	public void transferMoney(Long fromAccount, Long toAccount, BigDecimal amount);
}
