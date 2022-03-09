package com.learning.banking.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.Beneficiary;
import com.learning.banking.entity.BeneficiaryStatus;
import com.learning.banking.entity.Customer;
import com.learning.banking.entity.Transaction;
import com.learning.banking.entity.TransactionType;
import com.learning.banking.exceptions.InsufficientFundsException;
import com.learning.banking.exceptions.NoRecordsFoundException;
import com.learning.banking.payload.request.AddBeneficiaryRequest;
import com.learning.banking.payload.request.ResetPasswordRequest;
import com.learning.banking.payload.request.TransferRequest;
import com.learning.banking.payload.response.AccountDetailsResponse;
import com.learning.banking.payload.response.AddBeneficiaryResponse;
import com.learning.banking.payload.response.ApiMessage;
import com.learning.banking.payload.response.GetBeneficiariesResponse;
import com.learning.banking.payload.response.GetCustomerQandAResponse;
import com.learning.banking.payload.response.TransferResponse;
import com.learning.banking.service.AccountService;
import com.learning.banking.service.CustomerService;

/**
 * CustomerController
 *
 * @author bryan
 * @date Mar 8, 2022-3:19:33 PM
 */
@RestController
@RequestMapping("/api/customer")
@Validated
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;

	/* TODO: password encoder */

	@GetMapping("/{customerID}/account/{accountID}")
	public ResponseEntity<?> getCustomerAccountByID(@PathVariable Long customerID, @PathVariable Long accountID)
			throws NoRecordsFoundException {
		// Check if customer exists in database
		Customer customer = customerService.getCustomerByID(customerID).orElseThrow(() -> {
			return new NoRecordsFoundException("Customer with ID: " + customerID + " not found");
		});

		/* TODO: write repo method to get this in one shot */
		// Look for account which matches the accountID
		Account account = customer.getAccounts().stream().filter(acc -> {
			return acc.getAccountNumber() == accountID;
		}).findFirst().orElseThrow(() -> {
			return new NoRecordsFoundException("Account with ID: " + accountID + " not found");
		});

		return ResponseEntity.status(HttpStatus.OK).body(new AccountDetailsResponse(account));
	}

	@PostMapping("/{customerID}/beneficiary")
	public ResponseEntity<?> addBeneficiaryToCustomer(@PathVariable Long customerID,
			@Valid @RequestBody AddBeneficiaryRequest request) throws NoRecordsFoundException {
		// Check if customer exists in database
		Customer customer = customerService.getCustomerByID(customerID).orElseThrow(() -> {
			return new NoRecordsFoundException("Customer with ID: " + customerID + " not found");
		});

		// Check if beneficiary account exists with accountNumber from request
		Account beneficiaryAccount = accountService.getAccountByAccountNumber(request.getAccountNumber())
				.orElseThrow(() -> {
					return new NoRecordsFoundException(
							"Beneficiary with account number: " + request.getAccountNumber() + " not found");
				});

		Beneficiary beneficiary = new Beneficiary(beneficiaryAccount, customer);
		beneficiary.setAddedDate(LocalDate.now());
		beneficiary.setApproved(false);
		beneficiary.setActive(BeneficiaryStatus.YES);

		// Add beneficiary to collection from customer object (to establish
		// relationship)
		customer.getBeneficiaries().add(beneficiary);

		// Update customer
		Customer updatedCustomer = customerService.updateCustomer(customer);

		// Retrieve added beneficiary
		Beneficiary addedBeneficiary = updatedCustomer.getBeneficiaries().stream().filter(b -> {
			return b.getAccount().getAccountNumber() == request.getAccountNumber();
		}).findFirst().orElseThrow(() -> {
			return new NoRecordsFoundException(
					"Beneficiary with account number: " + request.getAccountNumber() + " not found");
		});

		// Return response
		return ResponseEntity.status(HttpStatus.OK).body(new AddBeneficiaryResponse(addedBeneficiary));
	}

	@GetMapping("/{customerID}/beneficiary")
	public ResponseEntity<?> getBeneficiariesForCustomer(@PathVariable Long customerID) throws NoRecordsFoundException {
		// Check if customer exists in database
		Customer customer = customerService.getCustomerByID(customerID).orElseThrow(() -> {
			return new NoRecordsFoundException("Customer with ID: " + customerID + " not found");
		});

		// Map beneficiaries to Response object
		List<GetBeneficiariesResponse> beneficiariesReponseList = customer.getBeneficiaries().stream().map(b -> {
			return new GetBeneficiariesResponse(b);
		}).collect(Collectors.toList());

		// Return response
		return ResponseEntity.status(HttpStatus.OK).body(beneficiariesReponseList);
	}

	@DeleteMapping("/{customerID}/beneficiary/{beneficiaryID}")
	public ResponseEntity<?> deleteBeneficiaryFromCustomer(@PathVariable Long customerID,
			@PathVariable Long beneficiaryID) throws NoRecordsFoundException {
		// Check if customer exists in database
		Customer customer = customerService.getCustomerByID(customerID).orElseThrow(() -> {
			return new NoRecordsFoundException("Customer with ID: " + customerID + " not found");
		});

		boolean removed = customer.getBeneficiaries().removeIf(b -> {
			return b.getBeneficiaryID() == beneficiaryID;
		});

		if (removed) {
			customer = customerService.updateCustomer(customer);
			return ResponseEntity.ok(new ApiMessage("Beneficiary deleted successfully"));
		} else {
			// TODO: use different HTTP Status
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiMessage("Unable to remove beneficiary"));
		}
	}

	@PutMapping("/transfer")
	public ResponseEntity<?> accountTransferByCustomer(@Valid @RequestBody TransferRequest request) throws NoRecordsFoundException, InsufficientFundsException {
		/*
		 * Transfer flow
		 * 
		 * 1. Retrieve Accounts matching account numbers (fromAccount and toAccount)
		 * 1a. Throw exception if accounts can't be found
		 * 2. Retrieve Customer object who initiated request (using by [ID] from request)
		 * 3. Check if account (fromAccount) has enough funds to deduct from
		 * 3a. If funds not available throw exception (InsufficientFundsException?)
		 * 4. Deduct amount from "fromAccount" and add to "toAccount"
		 * 5. Create 2 separate Transaction entries and add to both accounts
		 * 6. Save both entities using @Transaction
		 * 7. Return payload
		 */
		
		// 1. Retrieve accounts
		final Account fromAccount = accountService.getAccountByAccountNumber(request.getFromAccNumber()).orElseThrow(() -> {
			return new NoRecordsFoundException("Account number: " + request.getFromAccNumber() + " cannot be found");
		});
		final Account toAccount = accountService.getAccountByAccountNumber(request.getToAccNumber()).orElseThrow(() -> {
			return new NoRecordsFoundException("Account number: " + request.getToAccNumber() + " cannot be found");
		});
		
		// 2. Retrieve initiating customer
		final Customer initiatedBy = customerService.getCustomerByID(request.getBy()).orElseThrow(() -> {
			return new NoRecordsFoundException("Customer with ID: " + request.getBy() + " not found");
		});
		
		// 3. Validate funds
		if (fromAccount.getAccountBalance().subtract(request.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
			// Amount is negative after subtraction; account has insufficient funds
			throw new InsufficientFundsException(String.format("Account number: %d does not have sufficient funds to process transfer" , request.getFromAccNumber()));
		} else {
			// 4. Deduct amount
			fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(request.getAmount()));
			// 4a. Add amount
			toAccount.setAccountBalance(toAccount.getAccountBalance().add(request.getAmount()));
		}
		
		final LocalDateTime now = LocalDateTime.now();

		// 5. Create Transaction objects
		Transaction fromTransaction = new Transaction();
		fromTransaction.setDate(now);
		fromTransaction.setReference(request.getReason());
		fromTransaction.setAmount(request.getAmount());
		fromTransaction.setTransactionType(TransactionType.DEBIT); // TODO: add to request?
		fromTransaction.setInitiatedBy(initiatedBy);
		// 5a. Add to list
		fromAccount.getTransactions().add(fromTransaction);

		Transaction toTransaction = new Transaction();
		toTransaction.setDate(now);
		toTransaction.setReference(request.getReason());
		toTransaction.setAmount(request.getAmount());
		toTransaction.setTransactionType(TransactionType.DEBIT); // TODO: add to request?
		toTransaction.setInitiatedBy(initiatedBy);
		// 5a. Add to list
		toAccount.getTransactions().add(toTransaction);
		
		// 6. Save entities
		accountService.updateAccounts(Arrays.asList(fromAccount, toAccount));

		// 7. Return payload
		TransferResponse response = new TransferResponse();
		response.setFromAccNumber(request.getFromAccNumber());
		response.setToAccNumber(request.getToAccNumber());
		response.setAmount(request.getAmount());
		response.setReason(request.getReason());
		response.setBy(request.getBy());
		
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{username}/forgot/question/answer")
	public ResponseEntity<?> getCustomerSecurityQandA(@PathVariable String username) throws NoRecordsFoundException {
		// Check if customer exists in database
		Customer customer = customerService.getCustomerByUsername(username).orElseThrow(() -> {
			return new NoRecordsFoundException("Customer with username: " + username + " not found");
		});
		
		return ResponseEntity.ok(new GetCustomerQandAResponse(customer.getSecretQuestion(), customer.getSecretAnswer()));
	}
	
	@PutMapping("/{username}/forgot")
	public ResponseEntity<?> updateForgottenPassword(@PathVariable String username, @Valid @RequestBody ResetPasswordRequest request) throws NoRecordsFoundException {
		// Check if customer exists in database
		Customer customer = customerService.getCustomerByUsername(username).orElseThrow(() -> {
			return new NoRecordsFoundException("Customer with username: " + username + " not found");
		});
		
		// Check if passwords match
		if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
			// Passwords don't match
			return ResponseEntity.badRequest().body(new ApiMessage("Sorry password not updated"));
		} else {
			// Passwords match update accordingly
			customer.setPassword(request.getPassword()); // TODO: encode password
			customerService.updateCustomer(customer);
			
			return ResponseEntity.ok(new ApiMessage("Password updated successfully"));
		}
	}
}
