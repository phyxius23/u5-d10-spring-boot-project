package it.piattaformaaziendale.u5d10springbootproject.auth.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationSuccessfullPayload {
  private String accessToken;
}
