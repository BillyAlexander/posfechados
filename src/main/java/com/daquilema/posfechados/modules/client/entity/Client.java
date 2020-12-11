package com.daquilema.posfechados.modules.client.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.daquilema.posfechados.core.BaseEntity;
import com.daquilema.posfechados.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "client", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "ci" }, name = "ci_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class Client extends BaseEntity {
	private String ci;
	private String ruc;
	private String name;
}
