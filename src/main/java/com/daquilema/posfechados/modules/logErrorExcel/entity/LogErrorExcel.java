package com.daquilema.posfechados.modules.logErrorExcel.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.daquilema.posfechados.core.BaseEntity;
import com.daquilema.posfechados.modules.bank.entity.Bank;
import com.daquilema.posfechados.modules.client.entity.Client;
import com.daquilema.posfechados.modules.company.entity.Company;
import com.daquilema.posfechados.modules.credit.entity.Credit;
import com.daquilema.posfechados.modules.currency.entity.Currency;
import com.daquilema.posfechados.modules.fiscalYear.entity.FiscalYear;
import com.daquilema.posfechados.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = Const.SCHEMA, name = "error_excel", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "bank_id", "client_id", "company_id", "credit_id", "currency_id", "fiscalYear_id", "account", "checkNumber"  }, name = "log_error_excel_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@ToString
public class LogErrorExcel extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "bank_id", foreignKey = @ForeignKey(name = "log_bank_fk"), nullable = true)
	private Bank bank;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "log_client_fk"), nullable = true)
	private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "log_company_fk"), nullable = true)
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "credit_id", foreignKey = @ForeignKey(name = "log_credit_fk"), nullable = true)
	private Credit credit;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "currency_id", foreignKey = @ForeignKey(name = "log_currency_fk"), nullable = true)
	private Currency currency;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "fiscalYear_id", foreignKey = @ForeignKey(name = "log_fiscalYear_fk"), nullable = true)
	private FiscalYear fiscalYear;
	
	private Long account;
	
	private String checkNumber;
	
	private double amount;
	
	private Date datePayment;
	
	private Date dateRecovery;
	
	private Date dateExpiration;
	
	@Column(name = "information", nullable = true, columnDefinition = "VARCHAR(2000)")
	private String information;
}
