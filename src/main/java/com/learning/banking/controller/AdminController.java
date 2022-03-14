package com.learning.banking.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.banking.entity.Customer;
import com.learning.banking.entity.Role;
import com.learning.banking.enums.UserRoles;
import com.learning.banking.exceptions.IdNotFoundException;
import com.learning.banking.exceptions.NoDataFoundException;
import com.learning.banking.exceptions.NoRecordsFoundException;
import com.learning.banking.exceptions.RolePermissionsException;
import com.learning.banking.exceptions.UserNameAlreadyExistsException;
import com.learning.banking.payload.request.CreateStaffRequest;
import com.learning.banking.payload.request.SignInRequest;
import com.learning.banking.payload.request.UpdateStaffRequest;
import com.learning.banking.payload.response.JwtResponse;
import com.learning.banking.payload.response.StaffRespose;
import com.learning.banking.security.jwt.JwtUtils;
import com.learning.banking.security.service.UserDetailsImpl;
import com.learning.banking.service.CustomerService;
import com.learning.banking.service.RoleService;

/**
 * 
 * @author Dan
 *
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	CustomerService customerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;// in websecurityconfig

	@Autowired
	private RoleService roleService;

	@Autowired
	private JwtUtils jwtUtils;

	// 25
	// To validate the admin is registered in the system,and get jwt
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> authAdmmin(@Valid @RequestBody SignInRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		// handle singel thread
		// Interface defining the minimum security information associated with the
		// current thread of execution.
		// The security context is stored in a SecurityContextHolder.

		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Changes the currently authenticated principal, or removes the
		// authenticationinformation.
		String jwt = jwtUtils.generateToken(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();// getPrincipal get
		List<String> roles = userDetailsImpl.getAuthorities().stream().map(e -> e.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, userDetailsImpl.getId(), userDetailsImpl.getUsername(), roles)); // userdetails
	}

	// 26
	@PostMapping(value = "/staff")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createStaff(@Valid @RequestBody CreateStaffRequest request) {
		// username already exists or any error
		String userName = request.getUsername();
		if (customerService.getCustomerByUsername(userName).isPresent() == false) {
			Customer customer = new Customer();
			customer.setFullName(request.getFirstName(), request.getLastName());
			customer.setUsername(userName);
			String password = passwordEncoder.encode(request.getPassword());
			customer.setPassword(password);

			// set role to staff
			Set<Role> roles = new HashSet<>();

			Role staffRole = roleService.findByRoleName(UserRoles.ROLE_STAFF)
					.orElseThrow(() -> new IdNotFoundException("role id not found exception"));
			roles.add(staffRole);
			customer.setRoles(roles);

			Customer c = customerService.addCustomer(customer);

			StaffRespose staffRespose = new StaffRespose();
			staffRespose.setStaffId(c.getCustomerID());
			staffRespose.setStaffName(c.getFullName());
			staffRespose.setStatus(c.getStatus());

			return ResponseEntity.ok(staffRespose);
		} else {
			throw new UserNameAlreadyExistsException("username already exits");
		}
	}

	// 27
	// List all the Staff
	@GetMapping(value = "/staff")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllStaff() {
		System.out.println("getAll staff url");
		List<Customer> staffs = customerService.findCustomersByrolesRoleName(UserRoles.ROLE_STAFF);
		List<StaffRespose> staffResposes = new ArrayList<>();
		System.out.println(staffs.size());
		if (staffs.size() > 0) {
			for (Customer c : staffs) {
				StaffRespose staffRespose = new StaffRespose();
				staffRespose.setStaffId(c.getCustomerID());
				staffRespose.setStaffName(c.getFullName());
				staffRespose.setStatus(c.getStatus());
				staffResposes.add(staffRespose);
			}

		}
		return ResponseEntity.ok(staffResposes);
	}

	// 28
	// Enable or disable the staff, based on that the staff should be able to login
	@PutMapping(value = "/staff")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStaffStatus(@Valid @RequestBody UpdateStaffRequest staff) throws NoRecordsFoundException {
		Long id = staff.getStaffId();
		if (customerService.existsByID(id)) {
			boolean permissonCus = false;

			Customer customer = customerService.getCustomerByID(id).get();
			for (Role er : customer.getRoles()) {
				if (er.getRoleName().equals(UserRoles.ROLE_STAFF)) {
					permissonCus = true;
					break;
				}
			}

			if (permissonCus) {
				customer.setStatus(staff.getStatus());
				Customer cust = customerService.updateCustomer(customer);
				StaffRespose staffRespose = new StaffRespose();
				staffRespose.setStaffId(cust.getCustomerID());
				staffRespose.setStatus(cust.getStatus());
				staffRespose.setStaffName(cust.getFullName());
				return ResponseEntity.ok(staffRespose);
			} else {
				throw new RolePermissionsException("No role permission");
			}

		} else {
			throw new NoDataFoundException("Staff status not changed");
		}
	}
}