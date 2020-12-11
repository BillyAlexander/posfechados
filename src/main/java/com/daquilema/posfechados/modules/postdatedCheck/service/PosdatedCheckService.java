package com.daquilema.posfechados.modules.postdatedCheck.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.daquilema.posfechados.core.BaseService;
import com.daquilema.posfechados.core.ErrorControl;
import com.daquilema.posfechados.helper.ExcelHelper;
import com.daquilema.posfechados.helper.FilePostdated;
import com.daquilema.posfechados.helper.validacionidentificacion.ValidarIdentificacion;
import com.daquilema.posfechados.modules.bank.entity.Bank;
import com.daquilema.posfechados.modules.bank.repository.BankRepo;
import com.daquilema.posfechados.modules.client.entity.Client;
import com.daquilema.posfechados.modules.client.repository.ClientRepo;
import com.daquilema.posfechados.modules.company.entity.Company;
import com.daquilema.posfechados.modules.company.repository.CompanyRepo;
import com.daquilema.posfechados.modules.credit.entity.Credit;
import com.daquilema.posfechados.modules.credit.repository.CreditRepo;
import com.daquilema.posfechados.modules.currency.entity.Currency;
import com.daquilema.posfechados.modules.currency.repository.CurrencyRepo;
import com.daquilema.posfechados.modules.fiscalYear.entity.FiscalYear;
import com.daquilema.posfechados.modules.fiscalYear.repository.FiscalYearRepo;
import com.daquilema.posfechados.modules.logErrorExcel.entity.LogErrorExcel;
import com.daquilema.posfechados.modules.logErrorExcel.repository.LogErrorExcelRepo;
import com.daquilema.posfechados.modules.postdatedCheck.entity.PosdatedCheck;
import com.daquilema.posfechados.modules.postdatedCheck.repository.PostdatedCheckRepo;

@Service
public class PosdatedCheckService extends BaseService<PosdatedCheck> {

	public PosdatedCheckService() {
		super(PosdatedCheck.class);

	}

	@Autowired
	private PostdatedCheckRepo postdatedCheckRepo;
	
	@Autowired
	private ClientRepo clientRepo;
	
	@Autowired
	private CreditRepo creditRepo;
	
	@Autowired
	private BankRepo bankRepo;
	
	@Autowired
	private CurrencyRepo currencyRepo;
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private FiscalYearRepo fiscalYearRepo;

	@Autowired
	private LogErrorExcelRepo logErrorExcelRepo;
	
	public ResponseEntity<?> upload(MultipartFile file, Long companyId) {
		try {
			if (ExcelHelper.hasExcelFormat(file)) {
				//data from excel
				List<FilePostdated> data = ExcelHelper.excelToTutorials(file.getInputStream());
				
				//information to save
				List<FilePostdated> listOk = new ArrayList<>();
				List<FilePostdated> listFail = new ArrayList<>();
				
				//catalogos
				List<Credit> listCredit = creditRepo.findByStatusTrue();
				List<Bank> listCBank = bankRepo.findByStatusTrue();
				List<Currency> listCurrency = currencyRepo.findByStatusTrue();
				
				
				//validations list OK
				listOk =data.stream()
						.filter(d -> validaRuc(d.getCliente())==true)
						.filter(d -> listCredit.stream().map(Credit::getCode).anyMatch(code -> code.equalsIgnoreCase(d.getSeg_credito())))
						.filter(d -> listCBank.stream().map(Bank::getName).anyMatch(name -> name.equalsIgnoreCase(d.getBanco())))
						.filter(d -> listCurrency.stream().map(Currency::getCode).anyMatch(code -> code.equalsIgnoreCase(d.getMoneda())))
						.filter(d -> org.apache.commons.lang3.StringUtils.isNumeric(d.getNo_cheque()))
						.filter(d -> d.getFecha_cobro().after(new Date()))
						.filter(d -> d.getFecha_vence().after(new Date()) && d.getFecha_vence().after(d.getFecha_cobro()) && d.getFecha_vence().before(DateUtils.addMonths(d.getFecha_cobro(), 13) ))
						.collect(Collectors.toList());
				
				
				//validations list fail
				listFail = data;
				listFail.removeAll(listOk);
				
				
				List<PosdatedCheck> listTosave = parseToSave(listOk,companyId);
				postdatedCheckRepo.saveAll(listTosave);
				
				List<LogErrorExcel> listoErrorTosave = parseToSaveLog(listFail,companyId);
				logErrorExcelRepo.saveAll(listoErrorTosave);
				
				return new ResponseEntity<String>("ok", HttpStatus.OK);
			}
			return new ResponseEntity<ErrorControl>(new ErrorControl("El archivo no es un documento excel",
					HttpStatus.INTERNAL_SERVER_ERROR.value(), true), HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (IOException e) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("fail to store excel data: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value(), true), HttpStatus.INTERNAL_SERVER_ERROR);

		} catch(Exception e){
			return new ResponseEntity<ErrorControl>(new ErrorControl("fail to store excel data: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value(), true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	private List<PosdatedCheck> parseToSave(List<FilePostdated> old, Long companyId){
		List<PosdatedCheck> list = new ArrayList<>();
		for(FilePostdated f: old) {
			PosdatedCheck p = new PosdatedCheck();
			p.setClient(clientRepo.findByRucIgnoreCaseAndStatusTrue(f.getCliente()));
			p.setBank(bankRepo.findByNameIgnoreCaseAndStatusTrue(f.getBanco()));
			p.setCompany(companyRepo.findById(companyId).orElse(null));
			p.setCredit(creditRepo.findByCodeIgnoreCaseAndStatusTrue(f.getSeg_credito()));
			p.setCurrency(currencyRepo.findByCodeIgnoreCaseAndStatusTrue(f.getMoneda()));
			FiscalYear fis= fiscalYearRepo.findByYearFiscal(LocalDate.now().getYear());
			p.setFiscalYear(fis);
			p.setAccount((long)f.getCuenta());
			p.setCheckNumber(f.getNo_cheque());
			p.setAmount(f.getMonto());
			p.setDatePayment(new Date());
			p.setDateRecovery(f.getFecha_cobro());
			p.setDateExpiration(f.getFecha_vence());
			list.add(p);
		}
		
		return list;
	}
	
	private List<LogErrorExcel> parseToSaveLog(List<FilePostdated> old,Long companyId){
		List<LogErrorExcel> list = new ArrayList<>();
		for(FilePostdated f: old) {
			LogErrorExcel p = new LogErrorExcel();
			Client cl = clientRepo.findByRucIgnoreCaseAndStatusTrue(f.getCliente());
			String obs="";
			
			if(cl==null)
				obs=obs+" No existe cliente ";
			else
			{
				obs+=validaRuc(f.getCliente())?"":" El RUC no es Valido";
			}
			p.setClient(cl);
			
			Bank bank=bankRepo.findByNameIgnoreCaseAndStatusTrue(f.getBanco());
			obs+=(bank==null)?" No existe el Banco "+f.getBanco():"";			
			p.setBank(bank);
			
			Company comp= companyRepo.findById(companyId).orElse(null);			
			p.setCompany(comp);
			
			Credit credit= creditRepo.findByCodeIgnoreCaseAndStatusTrue(f.getSeg_credito());
			obs+=(credit==null)?" No existe el credito "+f.getSeg_credito():"";
			p.setCredit(credit);
			
			Currency curr= currencyRepo.findByCodeIgnoreCaseAndStatusTrue(f.getMoneda());
			obs+=(curr==null)?" No existe la moneda "+f.getMoneda():"";
			p.setCurrency(curr);
						
			FiscalYear fis= fiscalYearRepo.findByYearFiscal(LocalDate.now().getYear());
			p.setFiscalYear(fis);
			
			p.setAccount((long)f.getCuenta());
			
			boolean cheq=org.apache.commons.lang3.StringUtils.isNumeric(f.getNo_cheque());
			obs+=(cheq)?"":f.getNo_cheque()+"no es numerico";
			p.setCheckNumber(f.getNo_cheque());
			
			p.setAmount(f.getMonto());
			p.setDatePayment(new Date());
			
			obs+=(f.getFecha_cobro().after(new Date()))?"":"Fecha de 'cobro' con errores";
			p.setDateRecovery(f.getFecha_cobro());
			
			obs+=(f.getFecha_vence().after(new Date()) && f.getFecha_vence().after(f.getFecha_cobro()) && f.getFecha_vence().before(DateUtils.addMonths(f.getFecha_cobro(), 13)))?"":"Fecha 'vence' con errores";
			p.setDateExpiration(f.getFecha_vence());
			p.setInformation(obs);
			list.add(p);
			
		}
		
		return list;
	}

	private boolean validaRuc(String ruc) {
		ValidarIdentificacion val = new ValidarIdentificacion();
		try {
			return (val.validarRucPersonaNatural(ruc) || val.validarRucPersonaNatural(ruc));
		} catch (Exception e) {			
			return false;
		}
		
	}
}
