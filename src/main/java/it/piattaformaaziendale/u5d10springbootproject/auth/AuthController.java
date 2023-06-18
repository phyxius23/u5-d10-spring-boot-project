package it.piattaformaaziendale.u5d10springbootproject.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.piattaformaaziendale.u5d10springbootproject.auth.payloads.AuthenticationSuccessfullPayload;
import it.piattaformaaziendale.u5d10springbootproject.exceptions.UnauthorizedException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import it.piattaformaaziendale.u5d10springbootproject.utenti.UtenteService;
import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.LoginUtentePayload;
import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.RegistrazioneUtentePayload;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UtenteService utenteService;

	@PostMapping("/register")
	public ResponseEntity<Utente> register(@RequestBody @Validated RegistrazioneUtentePayload body) {
		Utente createdUser = utenteService.create(body);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	// questo metodo dovrà ricevere in input email e password
	@PostMapping("/login")
	public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody LoginUtentePayload body)
			throws NotFoundException {

		// 1. verificare che l'email dell'utente sia presente nel DB
		Utente user = utenteService.readByEmail(body.getEmail());

		// 2. In caso affermativo devo verificare che la password corrisponda a quella nel DB
		if (!body.getPassword().matches(user.getPassword()))
			throw new UnauthorizedException("Credenziali non valide");

		// 3. Se tutto OK --> genero JWT Token
		String token = JwtTools.createToken(user);

		return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token), HttpStatus.OK);

		// 4. Altrimenti --> 401 ("Credenziali non valide")
	}
}
