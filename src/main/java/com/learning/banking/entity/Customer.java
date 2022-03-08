package com.learning.banking.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Customer
 *
 * @author bryan
 * @date Mar 4, 2022-3:52:55 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "accounts", "roles", "beneficiaries" })
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
// Look at inheritance mapping (Staff, Customers)??
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerID;
	
	private String firstName;
	private String lastName;
	
	@Column(unique = true)
	private String username;
	private String password;
	
	@ManyToMany
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
	
	private String secretQuestion;
	private String secretAnswer;
	
	private String phone;
	
	private String pan;
	private String aadhar;
	
	private LocalDateTime dateCreated;
	
	@Enumerated(EnumType.STRING)
	private CustomerStatus status = CustomerStatus.ENABLED;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL/*, orphanRemoval = true*/)
	private Set<Account> accounts = new HashSet<>(); // 1:N - customer can have multiple accounts
	
	@OneToMany(mappedBy = "beneficiaryOf", cascade = CascadeType.ALL)
	/*
	@ManyToMany
	@JoinTable(
			name = "account_beneficiaries",
			joinColumns = @JoinColumn(name = "account_no"),
			inverseJoinColumns = @JoinColumn(name = "beneficiary_id")
	)
	*/
	private Set<Beneficiary> beneficiaries = new HashSet<>();

	
	public String getFullName() {
		return String.format("%s %s", firstName, lastName);
	}
}
