package com.learning.banking.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.banking.entity.Account;

/**
 * AccountRepository
 *
 * @author bryan
 * @date Mar 8, 2022-3:21:56 PM
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	@Transactional // Transactional: avoids LazyInitException
	@Override
	<S extends Account> S save(S entity);
	
	@Transactional // Transactional: avoids LazyInitException
	@Override
	<S extends Account> List<S> saveAll(Iterable<S> entities);
}
