package com.learning.banking.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Beneficiary
 *
 * @author bryan
 * @date Mar 4, 2022-3:55:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Beneficiary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long beneficiaryID;
	
	@OneToOne
	private Account account; // account belonging to the beneficiary
	
	@ManyToOne
	private Customer beneficiaryOf; // customer - beneficiary relationship

	private LocalDate addedDate;

	private boolean approved;
	
	@Enumerated(EnumType.STRING)
	private BeneficiaryStatus active;
	
	@OneToOne
	private Customer approvedBy; // staff which approved
	
	public Beneficiary(Account account, Customer beneficiaryOf) {
		this.account = account;
		this.beneficiaryOf = beneficiaryOf;
	}
}
