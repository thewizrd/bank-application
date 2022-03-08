package com.learning.banking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Account
 *
 * @author bryan
 * @date Mar 4, 2022-3:55:20 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "transactions" })
@Entity
@Table
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountNumber;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	private boolean approved = false;
	
	private BigDecimal accountBalance;
	private LocalDateTime dateOfCreation;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;
	
	@OneToMany(/*mappedBy = "account", */cascade = CascadeType.ALL)
	private List<Transaction> transactions = new ArrayList<>();
	
	@ManyToOne(optional = false)
	private Customer customer; // M:1 - account owned by single customer
	
	@OneToOne
	private Customer approvedBy; // approved by staff
}