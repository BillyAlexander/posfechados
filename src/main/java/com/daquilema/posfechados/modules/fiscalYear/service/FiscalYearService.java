package com.daquilema.posfechados.modules.fiscalYear.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.daquilema.posfechados.core.BaseService;
import com.daquilema.posfechados.core.ErrorControl;
import com.daquilema.posfechados.modules.fiscalYear.entity.FiscalYear;
import com.daquilema.posfechados.modules.fiscalYear.repository.FiscalYearRepo;
import com.daquilema.posfechados.modules.logErrorExcel.entity.LogErrorExcel;

@Service
public class FiscalYearService extends BaseService<FiscalYear> {

	public FiscalYearService() {
		super(FiscalYear.class);
	}
	
	@Autowired
	private FiscalYearRepo fiscalYearRepo;
	
	public ResponseEntity<?> listYears() {
		try {
			return new ResponseEntity<List<FiscalYear>>(fiscalYearRepo.findAll(), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
