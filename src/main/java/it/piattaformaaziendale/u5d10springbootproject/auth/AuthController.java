package it.piattaformaaziendale.u5d10springbootproject.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.piattaformaaziendale.u5d10springbootproject.auth.payloads.AuthenticationSuccessfullPayload;
import it.piattaformaaziendale.u5d10springbootproject.exceptions.UnauthorizedException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import it.piattaformaaziendale.u5d10springbootproject.utenti.UtentiService;
import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.UtenteLoginPayload;
import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.UtenteRegistrazionePayload;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  UtentiService utentiService;

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public String findPage() {
    return "Hai appena passato il test!!!!!";
  }

  @PostMapping("/register")
  public ResponseEntity<Utente> register(@RequestBody @Validated UtenteRegistrazionePayload body) {
    Utente createdUser = utentiService.create(body);
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody UtenteLoginPayload body)
      throws NotFoundException {

    // 1. Verificare che l'email dell'utente sia presente nel db
    Utente Utente = utentiService.findByEmail(body.getEmail());
    // 2. In caso affermativo devo verificare che la pw corrisponda a quella trovata
    // nel db
    if (!body.getPassword().matches(Utente.getPassword()))
      throw new UnauthorizedException("Credenziali non valide");
    // 3. Se tutto ok --> genero
    String token = JwtTools.createToken(Utente);
    // 4. Altrimenti --> 401 ("Credenziali non valide")

    return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token),
        HttpStatus.OK);
  }

  // ore 16.46 ho risolto questo problema
  // @PostMapping("/login")
  // public String login(@RequestBody UtenteLoginPayload body) throws
  // NotFoundException {

  // Utente u = new Utente("aldobaglio", "aldo", "baglio", "aldo@baglio.com",
  // "test");
  // String token = JwtTools.createToken(u);
  // return token;
  // }

}
