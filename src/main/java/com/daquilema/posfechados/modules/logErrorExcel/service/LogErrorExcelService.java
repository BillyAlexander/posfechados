package com.daquilema.posfechados.modules.logErrorExcel.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.daquilema.posfechados.core.BaseService;
import com.daquilema.posfechados.core.ErrorControl;
import com.daquilema.posfechados.helper.GeneratePdfReport;
import com.daquilema.posfechados.modules.logErrorExcel.entity.LogErrorExcel;
import com.daquilema.posfechados.modules.logErrorExcel.repository.LogErrorExcelRepo;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

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
	
	public ResponseEntity<?> reportLogs(Long empresaId,Long ejercicioId,Date startDate, Date endDate) {
		try {


			PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{1, 3, 3, 2, 2, 2, 2, 2, 2});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Banco", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Cliente", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Compania", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Credito", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Moneda", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Cuenta", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Nº Cheque", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Observación", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
			
            
            List<LogErrorExcel> logs = logErrorExcelRepo.findLogBySearch( empresaId, ejercicioId, startDate,  endDate);
            for (LogErrorExcel log : logs) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(log.getId().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((log.getBank()==null)?"":log.getBank().getName()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((log.getClient()==null)?"":log.getClient().getName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase((log.getCompany()==null)?"":log.getCompany().getName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase((log.getCredit()==null)?"":log.getCredit().getCode()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase((log.getCurrency()==null)?"":log.getCurrency().getCode()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(log.getAccount().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(log.getCheckNumber()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(log.getInformation()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }
            
            ByteArrayInputStream bis = GeneratePdfReport.generateReport(table);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
            
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
            
		} catch (DocumentException err) {
			err.printStackTrace();
			return new ResponseEntity<ErrorControl>(					
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);		
            
		} catch (Exception err) {
			err.printStackTrace();
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
