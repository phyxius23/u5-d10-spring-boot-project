package it.piattaformaaziendale.u5d10springbootproject.utenti.payload;

import lombok.Getter;

@Getter
public class UtenteLoginPayload {
  String email;
  String password;
}
