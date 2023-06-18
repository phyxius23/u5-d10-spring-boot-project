package it.piattaformaaziendale.u5d10springbootproject.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorsPayload {

	// Attributi
	private String message;
	private Date timestamp;
	private int internalCode;
}
