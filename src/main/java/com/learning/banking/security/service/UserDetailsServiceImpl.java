package com.learning.banking.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.banking.entity.Customer;
import com.learning.banking.exceptions.NoDataFoundException;
import com.learning.banking.repo.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	CustomerRepository customerRepository;

	// @Transactional annotation belongs to the Service layer because it is the
	// Service layer’s responsibility to define the transaction boundaries.
	// Ensure that multiple database operations within a method either succeed or
	// fail at the same time
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Customer user = customerRepository.findByUsername(username)
				.orElseThrow(() -> new NoDataFoundException("user not find with username " + username));

		return UserDetailsImpl.build(user);

	}

}
