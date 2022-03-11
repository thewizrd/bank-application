package com.learning.banking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.banking.entity.Beneficiary;
import com.learning.banking.repo.BeneficiaryRepository;
import com.learning.banking.service.BeneficiaryService;


@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	@Override
	public List<Beneficiary> getAllBeneficiaries() {
		// TODO Auto-generated method stub
		return beneficiaryRepository.findAll();
	}

	@Override
	public Beneficiary getBeneficiaryById(long id) {
		// TODO Auto-generated method stub
		return beneficiaryRepository.getById(id);
	}

	@Override
	public void saveBeneficiary(Beneficiary beneficiary) {
		// TODO Auto-generated method stub
		beneficiaryRepository.save(beneficiary);
	}

}
