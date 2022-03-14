package com.learning.banking.utils;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.learning.banking.entity.Customer;
import com.learning.banking.entity.Role;
import com.learning.banking.enums.CustomerStatus;
import com.learning.banking.enums.UserRoles;
import com.learning.banking.service.CustomerService;
import com.learning.banking.service.RoleService;

/**
 * DbInit
 *
 * @author bryan
 * @date Mar 14, 2022-12:00:10 AM
 */
@Component
public class DbInit {
	private static final String DEFAULT_ADMIN_USERNAME = "admin@admin.com";
	private static final String DEFAULT_ADMIN_PASSWORD = "secret@123";
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	private void initializeDB() {
		initializeRoles();
		initializeAdmin();
	}

	private void initializeRoles() {
		final UserRoles[] roles = UserRoles.values();

		for (UserRoles role : roles) {
			Optional<Role> roleEntity = roleService.findByRoleName(role);
			if (!roleEntity.isPresent()) {
				roleService.addRole(new Role(role));
			}
		}
	}

	private void initializeAdmin() {
		if (!customerService.existsByUsername(DEFAULT_ADMIN_USERNAME)) {
			Customer admin = new Customer();
			admin.setFirstName("Administrator");
			admin.setLastName("");
			admin.setUsername(DEFAULT_ADMIN_USERNAME);
			admin.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
			admin.getRoles().add(roleService.findByRoleName(UserRoles.ROLE_ADMIN).get());
			admin.setSecretQuestion("What's your favorite color?");
			admin.setSecretAnswer("Blue");
			admin.setPhone("123-456-7890");
			admin.setDateCreated(LocalDateTime.now());
			admin.setStatus(CustomerStatus.ENABLED);
			
			customerService.addCustomer(admin);
		}
	}
}
