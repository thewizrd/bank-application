package com.learning.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.banking.entity.Transaction;

/**
 * TransactionsRespository
 *
 * @author bryan
 * @date Mar 8, 2022-3:26:56 PM
 */
@Repository
public interface TransactionsRespository extends JpaRepository<Transaction, Long> {

}
