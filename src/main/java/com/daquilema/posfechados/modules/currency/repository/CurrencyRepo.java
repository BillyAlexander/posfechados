package com.daquilema.posfechados.modules.currency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.currency.entity.Currency;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long> {
	public List<Currency> findByStatusTrue();
	
	public Currency findByCodeIgnoreCaseAndStatusTrue(String code);
}
