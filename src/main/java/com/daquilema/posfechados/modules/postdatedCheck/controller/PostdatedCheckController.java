package com.daquilema.posfechados.modules.postdatedCheck.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daquilema.posfechados.modules.postdatedCheck.service.PosdatedCheckService;
import com.daquilema.posfechados.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "posdatedcheck", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostdatedCheckController {

	@Autowired
	private PosdatedCheckService posdatedCheckService;
	
	@PostMapping("upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,@RequestParam("companyId") Long companyId) throws ServletException {
		return posdatedCheckService.upload(file,companyId);
	}
}
