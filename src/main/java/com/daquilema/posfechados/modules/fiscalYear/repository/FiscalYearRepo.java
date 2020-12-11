package com.daquilema.posfechados.modules.fiscalYear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.fiscalYear.entity.FiscalYear;

@Repository
public interface FiscalYearRepo extends JpaRepository<FiscalYear, Long> {


	public FiscalYear findByYearFiscal( int yearFiscal);
}
