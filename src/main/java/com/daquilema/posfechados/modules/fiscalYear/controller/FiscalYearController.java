package com.daquilema.posfechados.modules.fiscalYear.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daquilema.posfechados.modules.fiscalYear.service.FiscalYearService;
import com.daquilema.posfechados.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "fiscalyear", produces = MediaType.APPLICATION_JSON_VALUE)
public class FiscalYearController {
	@Autowired
	private FiscalYearService fiscalYearService;

	@GetMapping(value = "list")
	public ResponseEntity<?> listFiscalYear()  throws ServletException {
		return fiscalYearService.listYears();
	}
}
