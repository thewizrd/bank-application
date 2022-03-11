package com.learning.banking.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.banking.entity.Customer;
import com.learning.banking.enums.UserRoles;
import com.learning.banking.exceptions.NoRecordsFoundException;
import com.learning.banking.repo.CustomerRepository;
import com.learning.banking.service.CustomerService;

/**
 * CustomerServiceImpl
 *
 * @author bryan
 * @date Mar 8, 2022-3:39:23 PM
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository repo;

	@Override
	public Customer addCustomer(Customer customer) {
		return repo.save(customer);
	}

	@Override
	public Optional<Customer> getCustomerByID(long id) {
		return repo.findById(id);
	}

	@Override
	public Optional<Customer> getCustomerByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return repo.findAll();
	}

	@Override
	public boolean deleteCustomerByID(long id) {
		Customer customerRef = repo.getById(id);
		repo.delete(customerRef);
		return true;
	}

	@Override
	public Customer updateCustomer(Customer customer) throws NoRecordsFoundException {
		if (repo.existsById(customer.getCustomerID())) {
			return repo.save(customer);
		} else {
			throw new NoRecordsFoundException("Customer with id: " + customer.getCustomerID() + " not found");
		}
	}

	@Override
	public boolean existsByID(long id) {
		return repo.existsById(id);
	}

	@Override
	public boolean existsByUsername(String username) {
		return repo.existsByUsername(username);
	}

	@Override
	public List<Customer> findCustomersByrolesRoleName(UserRoles roleName) {
		return repo.findCustomersByrolesRoleName(roleName);
	}
}
