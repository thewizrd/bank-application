package com.learning.banking.service;

import java.util.List;

import com.learning.banking.entity.Beneficiary;

public interface BeneficiaryService {
	public List<Beneficiary> getAllBeneficiaries();
	public Beneficiary getBeneficiaryById(long id);
	public void saveBeneficiary(Beneficiary beneficiary);
}
