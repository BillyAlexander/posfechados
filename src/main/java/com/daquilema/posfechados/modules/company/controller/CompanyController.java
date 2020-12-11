package com.daquilema.posfechados.modules.company.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daquilema.posfechados.modules.company.service.CompanyService;
import com.daquilema.posfechados.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@GetMapping(value = "list")
	public ResponseEntity<?> listSelectCompany() throws ServletException {
		return companyService.listSelectCompany();
	}
}
