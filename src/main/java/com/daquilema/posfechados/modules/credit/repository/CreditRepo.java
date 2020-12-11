package com.daquilema.posfechados.modules.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.credit.entity.Credit;

@Repository
public interface CreditRepo extends JpaRepository<Credit, Long> {
	public Credit findByCodeIgnoreCaseAndStatusTrue(String code);
	
	public List<Credit> findByStatusTrue();
}
