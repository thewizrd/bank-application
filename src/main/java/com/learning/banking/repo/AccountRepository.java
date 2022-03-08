package com.learning.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.banking.entity.Account;

/**
 * AccountRepository
 *
 * @author bryan
 * @date Mar 8, 2022-3:21:56 PM
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

}
