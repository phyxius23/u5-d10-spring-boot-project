package it.piattaformaaziendale.u5d10springbootproject.exceptions;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
