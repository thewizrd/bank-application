package com.learning.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.banking.entity.Beneficiary;

/**
 * BeneficiaryRepository
 *
 * @author bryan
 * @date Mar 8, 2022-3:22:46 PM
 */
@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

}
