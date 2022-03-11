package com.learning.banking.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.Customer;
import com.learning.banking.entity.Transaction;
import com.learning.banking.enums.CustomerStatus;
import com.learning.banking.enums.TransactionType;
import com.learning.banking.exceptions.NoDataFoundException;
import com.learning.banking.payload.request.CustomerRequest;
import com.learning.banking.payload.request.TransferAmountRequest;
import com.learning.banking.payload.response.CustomerResponseFromStaff;
import com.learning.banking.payload.response.StaffTransactionResponse;
import com.learning.banking.service.AccountService;
import com.learning.banking.service.CustomerService;
import com.learning.banking.service.TransactionService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	// Enable or disable the customer, based on that the customer should be able to
	// login
	@PutMapping(value = "/customer")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<?> changeCustomerStatus(@Valid @RequestBody CustomerRequest customerRequest) {

		// customer should be able to login
		Long customerId = customerRequest.getCustomerId();
		if (customerService.existsByID(customerId)) {
			Customer customer = customerService.getCustomerByID(customerId).get();
			CustomerStatus status = customerRequest.getCustomerStatus();
			customer.setStatus(status);
			Customer c = customerService.addCustomer(customer);
			CustomerResponseFromStaff customerResponse = new CustomerResponseFromStaff();
			customerResponse.setId(c.getCustomerID());
			customerResponse.setFullname(c.getFullName());
			customerResponse.setCustomerStatus(c.getStatus());
			customerResponse.setCreateDate(c.getDateCreated().toLocalDate());
			return ResponseEntity.status(200).body(customerResponse);
		} else {
			throw new NoDataFoundException("Customer status not changed");
		}

	}

	// Role: Staff ,Get customer with the id
	@GetMapping(value = "/customer/{customerID}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<?> getCustomerById(@Valid @PathVariable("customerID") Long id) {
		Customer customer = customerService.getCustomerByID(id)
				.orElseThrow(() -> new NoDataFoundException("Customer Not Found"));
		CustomerResponseFromStaff customerResponse = new CustomerResponseFromStaff();
		customerResponse.setId(customer.getCustomerID());
		customerResponse.setFullname(customer.getFullName());
		customerResponse.setCustomerStatus(customer.getStatus());
		customerResponse.setCreateDate(customer.getDateCreated().toLocalDate());
		return ResponseEntity.status(200).body(customerResponse);
	}

	// To transfer the amount from one account to another account
	@PutMapping(value = "/transfer")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<?> doTransfer(@Valid @RequestBody TransferAmountRequest trans) {
		// From/To Account Number valid
		Long fromAccNumber = trans.getFromAccNumber();
		Long toAccNumber = trans.getToAccNumber();
		BigDecimal amount = trans.getAmount();

		if (accountService.existsByAccountNumber(fromAccNumber) && accountService.existsByAccountNumber(toAccNumber)) {
			System.out.println("start transfer money!!!!");
			transactionService.transferMoney(fromAccNumber, toAccNumber, amount);
			System.out.println("transfer money sucessful!!!");
			Account fromAccount = accountService.findAccountByAccountNumber(fromAccNumber).get();
			Transaction transaction1 = new Transaction();
			transaction1.setAccount(fromAccount);
			transaction1.setAmount(amount.negate());
			transaction1.setDate(LocalDateTime.now());
			transaction1.setReference(trans.getReason());
			transaction1.setTransactionType(TransactionType.DEBIT);
			Transaction transaction01 = transactionService.addTransaction(transaction1);

			Account toAccount = accountService.findAccountByAccountNumber(toAccNumber).get();
			Transaction transaction2 = new Transaction();
			transaction2.setAccount(toAccount);
			transaction2.setAmount(amount);
			transaction2.setDate(LocalDateTime.now());
			transaction2.setReference(trans.getReason());
			transaction2.setTransactionType(TransactionType.DEBIT);
			Transaction transaction02 = transactionService.addTransaction(transaction2);

			StaffTransactionResponse transactionResponse = new StaffTransactionResponse();
			transactionResponse.setAmount(amount);
			transactionResponse.setFromAccNumber(fromAccNumber);
			transactionResponse.setToAccNumber(toAccNumber);
			transactionResponse.setReason(transaction01.getReference());
			transactionResponse.setByStaff(trans.getByStaff());
			return ResponseEntity.status(200).body(transactionResponse);
		} else {
			throw new NoDataFoundException("From/To Account Number Not Valid");
		}

	}
}
