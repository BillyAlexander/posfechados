package com.daquilema.posfechados.modules.currency.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.daquilema.posfechados.core.BaseEntity;
import com.daquilema.posfechados.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "currency", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "code" }, name = "currency_code_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class Currency extends BaseEntity {
	private String code;
	private String description;
}
