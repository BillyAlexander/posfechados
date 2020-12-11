package com.daquilema.posfechados.modules.company.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.daquilema.posfechados.core.BaseEntity;
import com.daquilema.posfechados.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "company", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name" }, name = "company_name_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class Company extends BaseEntity {
	private String name;
}
