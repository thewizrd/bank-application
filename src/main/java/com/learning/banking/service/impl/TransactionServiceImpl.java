package com.learning.banking.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.Transaction;
import com.learning.banking.exceptions.TransferException;
import com.learning.banking.repo.AccountRepository;
import com.learning.banking.repo.TransactionsRespository;
import com.learning.banking.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionsRespository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Override
	public Transaction addTransaction(Transaction transaction) {

		return transactionRepository.save(transaction);
	}

	@Override
	public void sendMoney(Long fromAccount, BigDecimal amount) {
		// TODO Auto-generated method stub
		Account fAccount = accountRepository.findAccountByAccountNumber(fromAccount).get();
		// System.out.println("start send money form: "+fromAccount);
		BigDecimal balance = fAccount.getAccountBalance();
		// System.out.println("balance:"+balance);
		if (balance.compareTo(amount) > 0) {

			BigDecimal sendAmount = balance.add(amount.negate());
			fAccount.setAccountBalance(sendAmount);

			accountRepository.save(fAccount);
		} else {
			throw new TransferException("Account balance not vaild");
		}

	}

	@Override
	public void getMoney(Long toAccount, BigDecimal amount) {
		// TODO Auto-generated method stub

		Account tAccount = accountRepository.findAccountByAccountNumber(toAccount).get();

		BigDecimal balance = tAccount.getAccountBalance().add(amount);

		tAccount.setAccountBalance(balance);

		accountRepository.save(tAccount);

	}

	@Override
	@Transactional(rollbackFor = TransferException.class)
	public void transferMoney(Long fromAccount, Long toAccount, BigDecimal amount) {
		// TODO Auto-generated method stub
		sendMoney(fromAccount, amount);
		getMoney(toAccount, amount);

	}

}
