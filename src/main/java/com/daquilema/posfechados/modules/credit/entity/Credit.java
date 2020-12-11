package com.daquilema.posfechados.modules.credit.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.daquilema.posfechados.core.BaseEntity;
import com.daquilema.posfechados.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "credit", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "code" }, name = "credit_code_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class Credit extends BaseEntity{
	private String code;
	private String description;
}
