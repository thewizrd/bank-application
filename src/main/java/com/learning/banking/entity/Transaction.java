package com.learning.banking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transaction
 *
 * @author bryan
 * @date Mar 4, 2022-3:55:26 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionID;
	private LocalDateTime date;
	private String reference;
	private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@OneToOne
	private Customer initiatedBy; // add customer who initiated transaction
}