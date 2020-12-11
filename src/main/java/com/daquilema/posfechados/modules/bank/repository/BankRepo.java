package com.daquilema.posfechados.modules.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.bank.entity.Bank;

@Repository
public interface BankRepo extends JpaRepository<Bank, Long> {
	public List<Bank> findByStatusTrue();
	
	public Bank findByNameIgnoreCaseAndStatusTrue(String name);
}
