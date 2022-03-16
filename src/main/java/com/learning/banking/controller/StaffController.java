package com.learning.banking.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.banking.entity.Account;
import com.learning.banking.entity.Beneficiary;
import com.learning.banking.entity.Customer;
import com.learning.banking.entity.Role;
import com.learning.banking.entity.Transaction;
import com.learning.banking.enums.BeneficiaryStatus;
import com.learning.banking.enums.CustomerStatus;
import com.learning.banking.enums.TransactionType;
import com.learning.banking.enums.UserRoles;
import com.learning.banking.exceptions.NoDataFoundException;
import com.learning.banking.exceptions.NoRecordsFoundException;
import com.learning.banking.exceptions.RolePermissionsException;
import com.learning.banking.exceptions.TransferException;
import com.learning.banking.payload.request.ApproveBeneficiaryRequest;
import com.learning.banking.payload.request.ApprovedAccountRequest;
import com.learning.banking.payload.request.SignInRequest;
import com.learning.banking.payload.request.TransferAmountRequest;
import com.learning.banking.payload.request.UpdateCustomerStatusRequest;
import com.learning.banking.payload.response.AccountLookupResponse;
import com.learning.banking.payload.response.AllAccountsResponse;
import com.learning.banking.payload.response.AllCustomersResponse;
import com.learning.banking.payload.response.ApprovedAccountResponse;
import com.learning.banking.payload.response.CustomerResponseFromStaff;
import com.learning.banking.payload.response.JwtResponse;
import com.learning.banking.payload.response.NonApprovedAccountResponse;
import com.learning.banking.payload.response.NonApprovedBeneficiaryResponse;
import com.learning.banking.payload.response.StaffTransactionResponse;
import com.learning.banking.payload.response.TransactionLookupResponse;
import com.learning.banking.security.jwt.JwtUtils;
import com.learning.banking.security.service.UserDetailsImpl;
import com.learning.banking.service.AccountService;
import com.learning.banking.service.BeneficiaryService;
import com.learning.banking.service.CustomerService;
import com.learning.banking.service.TransactionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/staff")
public class StaffController {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private BeneficiaryService beneficiaryService;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	
	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	// 15
	@PostMapping("/authenticate")
	public ResponseEntity<?> signInUser(@Valid @RequestBody SignInRequest signInRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);

		UserDetailsImpl staffDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = staffDetailsImpl.getAuthorities().stream().map(e -> e.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, staffDetailsImpl.getId(), staffDetailsImpl.getUsername(), roles));
	}

	// 16
	@GetMapping("/account/{accountNo}")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public ResponseEntity<?> getCustomerByAccountNumber(@PathVariable("accountNo") long accountNo)
			throws NoDataFoundException {
		Account account = accountService.getAccountByAccountNumber(accountNo)
				.orElseThrow(() -> new NoDataFoundException("account not found"));
		AccountLookupResponse accountLookupResponse = new AccountLookupResponse();
		accountLookupResponse.setAccountNumber(account.getAccountNumber());
		accountLookupResponse.setBalance(account.getAccountBalance());
		accountLookupResponse.setFirstName(account.getCustomer().getFirstName());
		accountLookupResponse.setLastName(account.getCustomer().getLastName());
		Set<TransactionLookupResponse> transactions = new LinkedHashSet<TransactionLookupResponse>();
		Collections.sort(account.getTransactions(), new Comparator<Transaction>() {

			@Override
			public int compare(Transaction o1, Transaction o2) {
				// TODO Auto-generated method stub
				return o1.getDate().compareTo(o2.getDate());
			}
			
		});
		Collections.reverse(account.getTransactions());
		account.getTransactions().forEach(e -> {
			TransactionLookupResponse transaction = new TransactionLookupResponse();
			transaction.setAmount(e.getAmount());
			transaction.setDate(e.getDate().toLocalDate());
			transaction.setReference(e.getReference());
			transaction.setTransactionType(e.getTransactionType().toString());
			transactions.add(transaction);
		});
		
		accountLookupResponse.setTransactions(transactions);

		return ResponseEntity.status(200).body(accountLookupResponse);
	}

	// 17
	@GetMapping("/beneficiary")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public ResponseEntity<?> getAllNonApprovedBenefeciaries() {
		List<Beneficiary> allBeneficiaries = beneficiaryService.getAllBeneficiaries();
		List<NonApprovedBeneficiaryResponse> nonApprovedBeneficiaries = new ArrayList<NonApprovedBeneficiaryResponse>();
		allBeneficiaries.forEach(e -> {
			if (!e.isApproved()) {
				NonApprovedBeneficiaryResponse beneficiary = new NonApprovedBeneficiaryResponse();
				beneficiary.setBeneficiaryAccountNumber(e.getAccount().getAccountNumber());
				beneficiary.setDateAdded(e.getAddedDate());
				beneficiary.setFromCustomer(e.getBeneficiaryOf().getCustomerID());
				beneficiary.setIsApproved("no");
				nonApprovedBeneficiaries.add(beneficiary);
			}
		});
		return ResponseEntity.status(200).body(nonApprovedBeneficiaries);
	}

	// 18
	@PutMapping(value = "/beneficiary")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public ResponseEntity<?> approveBeneficiary(@Valid @RequestBody ApproveBeneficiaryRequest request) throws NoDataFoundException, NoRecordsFoundException {
		if (request.getIsApproved().equalsIgnoreCase("yes")) {
			// Get Staff details
			Authentication authentication = getAuthentication();
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			final Customer approver = customerService.getCustomerByID(userDetails.getId()).orElseThrow(() ->{
				return new NoRecordsFoundException("Customer with ID: " + userDetails.getId() + " not found");
			});
			
			// Retrieve customer
			Customer customer = customerService.getCustomerByID(request.getCustomerId()).orElseThrow(() ->{
				return new NoRecordsFoundException("Customer with ID: " + request.getCustomerId() + " not found");
			});

			// Get beneficiary matching account number
			Beneficiary beneficiary = customer.getBeneficiaries().stream().filter((b) -> {
				return b.getAccount().getAccountNumber() == request.getBeneficiaryAccountNumber();
			}).findFirst().orElseThrow(() -> {
				return new NoRecordsFoundException("Beneficiary with account number: " + request.getBeneficiaryAccountNumber() + " not found");
			});
			beneficiary.setApproved(true);
			beneficiary.setActive(BeneficiaryStatus.YES);
			beneficiary.setApprovedBy(approver);
			beneficiaryService.saveBeneficiary(beneficiary);
		} else {
			Map<String, String> responseBody = new HashMap<String, String>();
			responseBody.put("message:", "Sorry, beneficiary not approved.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
		}

		return ResponseEntity.status(200).body(request);
	}

	// 19
	@GetMapping(value = "/accounts/approve")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public ResponseEntity<?> getAllNonApprovedAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		List<NonApprovedAccountResponse> nonApprovedAccounts = new ArrayList<NonApprovedAccountResponse>();
		accounts = accountService.getAllAccounts();
		accounts.forEach(e -> {
			if (!e.isApproved()) {
				NonApprovedAccountResponse nonApprovedAccount = new NonApprovedAccountResponse();
				nonApprovedAccount.setAccountNumber(e.getAccountNumber());
				nonApprovedAccount.setAccountType(e.getAccountType().toString());
				nonApprovedAccount.setApproved("no");
				nonApprovedAccount
						.setCustomerName(e.getCustomer().getFullName());
				nonApprovedAccount.setDateCreated(e.getDateOfCreation().toLocalDate());
				nonApprovedAccounts.add(nonApprovedAccount);
			}
		});

		return ResponseEntity.status(200).body(nonApprovedAccounts);
	}

	// 20
	@PutMapping(value = "accounts/approve")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public ResponseEntity<?> approveCustomerAccounts(@Valid @RequestBody ApprovedAccountRequest request) throws NoDataFoundException {
		if (request.getApproved().equalsIgnoreCase("yes")) {
			Customer staffMember = customerService.getCustomerByUsername(request.getStaffUserName())
					.orElseThrow(() -> new NoDataFoundException("Staff member not found."));
			Account customerAccount = accountService.findAccountByAccountNumber(request.getAccountNumber()).orElseThrow(() -> {
				return new NoDataFoundException("Account not found.");
			});
			
			customerAccount.setApproved(true);
			customerAccount.setApprovedBy(staffMember);
			customerAccount = accountService.updateAccount(customerAccount);

			ApprovedAccountResponse responseEntity = new ApprovedAccountResponse();
			responseEntity.setAccountNumber(customerAccount.getAccountNumber());
			responseEntity.setAccountType(customerAccount.getAccountType());
			responseEntity.setApproved("yes");
			responseEntity.setDateCreated(customerAccount.getDateOfCreation().toLocalDate());
			responseEntity.setFirstName(customerAccount.getCustomer().getFirstName());
			responseEntity.setLastName(customerAccount.getCustomer().getLastName());
			responseEntity.setStaffUserName(staffMember.getUsername());

			return ResponseEntity.status(200).body(responseEntity);
		} else {
			Map<String, String> responseBodyMap = new HashMap<String, String>();
			responseBodyMap.put("message",
					"Approving of account was not successful or there were no accounts to approve.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyMap);
		}
	}

	// 21
	@GetMapping(value = "/customer")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public ResponseEntity<?> getAllCustomers() {
		List<AllCustomersResponse> responseBody = new ArrayList<AllCustomersResponse>();
		List<Customer> allCustomers = new ArrayList<Customer>();
		allCustomers = customerService.getAllCustomers();
		allCustomers.forEach(e -> {
			boolean isCustomer = false;

			if (e.getRoles() != null && !e.getRoles().isEmpty()) {
				for (Role role : e.getRoles()) {
					if (role.getRoleName() == UserRoles.ROLE_CUSTOMER) {
						isCustomer = true;
						break;
					}
				}
			}
			
			if (isCustomer) {
				AllCustomersResponse entity = new AllCustomersResponse();
				entity.setCustomerId(e.getCustomerID());
				entity.setCustomerName(e.getFullName());
				entity.setStatus(e.getStatus());
				responseBody.add(entity);
			}
		});

		return ResponseEntity.status(200).body(responseBody);
	}

	// 22
	// Enable or disable the customer, based on that the customer should be able to
	// login
	@PutMapping(value = "/customer")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<?> changeCustomerStatus(@Valid @RequestBody UpdateCustomerStatusRequest customerRequest)
			throws NoRecordsFoundException {
		// customer should be able to login
		Long customerId = customerRequest.getCustomerId();
		if (customerService.existsByID(customerId)) {
			Customer customer = customerService.getCustomerByID(customerId).get();
			boolean permissonCus = false;

			for (Role er : customer.getRoles()) {
				if (er.getRoleName().equals(UserRoles.ROLE_CUSTOMER)) {
					permissonCus = true;
					break;
				}
			}

			if (permissonCus) {
				CustomerStatus status = customerRequest.getStatus();
				customer.setStatus(status);
				Customer c = customerService.updateCustomer(customer);

				CustomerResponseFromStaff customerResponse = new CustomerResponseFromStaff();
				customerResponse.setId(c.getCustomerID());
				customerResponse.setFullname(c.getFullName());
				customerResponse.setCustomerStatus(c.getStatus());
				customerResponse.setCreateDate(c.getDateCreated());
				customerResponse.setUsername(c.getUsername());
				return ResponseEntity.status(200).body(customerResponse);
			} else {
				throw new RolePermissionsException("No permissions");
			}
		} else {
			throw new NoDataFoundException("Customer status not changed");
		}

	}

	// 23
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
		customerResponse.setCreateDate(customer.getDateCreated());
		customerResponse.setUsername(customer.getUsername());
		return ResponseEntity.status(200).body(customerResponse);
	}

	// 24
	// To transfer the amount from one account to another account
	@PutMapping(value = "/transfer")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<?> doTransfer(@Valid @RequestBody TransferAmountRequest trans) throws NoRecordsFoundException {
		// From/To Account Number valid
		Long fromAccNumber = trans.getFromAccNumber();
		Long toAccNumber = trans.getToAccNumber();
		BigDecimal amount = trans.getAmount();
		StaffTransactionResponse transactionResponse = new StaffTransactionResponse();
		
		Customer staffMember = customerService.getCustomerByUsername(trans.getByStaff()).orElseThrow(() ->{
			return new NoRecordsFoundException("Staff member not found");
		});

		if (accountService.existsByAccountNumber(fromAccNumber) && accountService.existsByAccountNumber(toAccNumber)) {
			Account fromAcc = accountService.getAccountByAccountNumber(fromAccNumber).get();
			Account toAcc = accountService.getAccountByAccountNumber(toAccNumber).get();
			if(fromAcc.isApproved() && toAcc.isApproved())
			{
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
				transaction1.setInitiatedBy(staffMember);
				Transaction transaction01 = transactionService.addTransaction(transaction1);

				Account toAccount = accountService.findAccountByAccountNumber(toAccNumber).get();
				Transaction transaction2 = new Transaction();
				transaction2.setAccount(toAccount);
				transaction2.setAmount(amount);
				transaction2.setDate(LocalDateTime.now());
				transaction2.setReference(trans.getReason());
				transaction2.setTransactionType(TransactionType.DEBIT);
				transaction2.setInitiatedBy(staffMember);
				transactionService.addTransaction(transaction2);

				
				transactionResponse.setAmount(amount);
				transactionResponse.setFromAccNumber(fromAccNumber);
				transactionResponse.setToAccNumber(toAccNumber);
				transactionResponse.setReason(transaction01.getReference());
				transactionResponse.setByStaff(trans.getByStaff());
			}
			else {
				throw new TransferException("Both accounts must be approved to perform a transfer.");
			}
			return ResponseEntity.status(200).body(transactionResponse);
			
		} else {
			throw new NoDataFoundException("From/To Account Number Not Valid");
		}
	}
}
