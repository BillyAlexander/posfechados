package com.daquilema.posfechados.helper;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString()
public class FilePostdated {
	private String cliente;
	private String seg_credito;
	private String banco;
	private double cuenta;
	private String no_cheque;
	private double monto;
	private String moneda;
	private Date fecha_cobro;
	private Date fecha_vence;
}
