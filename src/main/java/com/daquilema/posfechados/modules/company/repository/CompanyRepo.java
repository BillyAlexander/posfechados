package com.daquilema.posfechados.modules.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.company.entity.Company;
@Repository
public interface CompanyRepo extends JpaRepository<Company, Long>{
	public List<Company> findByStatusTrue();
	
	public Company findByNameIgnoreCaseAndStatusTrue(String name);
}
