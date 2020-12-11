package com.daquilema.posfechados.modules.logErrorExcel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.daquilema.posfechados.core.BaseService;
import com.daquilema.posfechados.core.ErrorControl;
import com.daquilema.posfechados.modules.logErrorExcel.entity.LogErrorExcel;
import com.daquilema.posfechados.modules.logErrorExcel.repository.LogErrorExcelRepo;

@Service
public class LogErrorExcelService extends BaseService<LogErrorExcel> {

	public LogErrorExcelService() {
		super(LogErrorExcel.class);		
	}
	
	@Autowired
	private LogErrorExcelRepo logErrorExcelRepo;
	
	public ResponseEntity<?> listLogs(Long empresaId,Long ejercicioId,Date startDate, Date endDate) {
		try {
			return new ResponseEntity<List<LogErrorExcel>>(logErrorExcelRepo.findLogBySearch( empresaId, ejercicioId, startDate,  endDate), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
