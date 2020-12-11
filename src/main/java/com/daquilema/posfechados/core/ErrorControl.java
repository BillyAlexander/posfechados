package com.daquilema.posfechados.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorControl {
	private String error;
	private int statusCode;
	private Boolean isCustom = true;
}
