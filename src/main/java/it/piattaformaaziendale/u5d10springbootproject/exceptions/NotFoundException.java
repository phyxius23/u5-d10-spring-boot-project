package it.piattaformaaziendale.u5d10springbootproject.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String text) {
		super(text);
	}

	public NotFoundException(UUID id) {
		super("Utente con id " + id + " non trovato!");
	}

}
