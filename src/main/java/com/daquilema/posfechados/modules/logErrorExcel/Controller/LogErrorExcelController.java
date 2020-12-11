package com.daquilema.posfechados.modules.logErrorExcel.Controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daquilema.posfechados.modules.logErrorExcel.service.LogErrorExcelService;
import com.daquilema.posfechados.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "logerrorexcel", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogErrorExcelController {

	@Autowired
	private LogErrorExcelService logErrorExcelService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "list")
	public ResponseEntity<?> listLogError(@RequestParam(required = false) Long empresaId,
			@RequestParam(required = false) Long ejercicioId, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate) throws ServletException {
		return logErrorExcelService.listLogs(empresaId,ejercicioId,startDate == null ? new Date(0) : startDate, endDate == null ? new Date() : endDate);
	}

}
