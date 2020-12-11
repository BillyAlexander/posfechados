package com.daquilema.posfechados.modules.fiscalYear.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.daquilema.posfechados.core.BaseEntity;
import com.daquilema.posfechados.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "fiscal_year", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "yearFiscal" }, name = "year_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class FiscalYear extends BaseEntity {
   private int yearFiscal;
}
