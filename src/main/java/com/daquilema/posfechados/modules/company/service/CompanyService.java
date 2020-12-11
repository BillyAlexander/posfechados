package com.daquilema.posfechados.modules.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.daquilema.posfechados.core.BaseService;
import com.daquilema.posfechados.core.ErrorControl;
import com.daquilema.posfechados.modules.company.entity.Company;
import com.daquilema.posfechados.modules.company.repository.CompanyRepo;

@Service
public class CompanyService extends BaseService<Company> {

	public CompanyService() {
		super(Company.class);
	}
	
	@Autowired
	private CompanyRepo companyRepo;
	
	
	public ResponseEntity<?> listSelectCompany() {
		try {
			return new ResponseEntity<List<Company>>(companyRepo.findByStatusTrue(), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
