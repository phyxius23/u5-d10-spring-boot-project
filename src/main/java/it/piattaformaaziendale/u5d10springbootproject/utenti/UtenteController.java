package it.piattaformaaziendale.u5d10springbootproject.utenti;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.RegistrazioneUtentePayload;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

	@Autowired
	UtenteService utenteService;

	// *************** UTILIZZO DI SERVICE, CONTROLLER E ***************
	// *************** REPOSITORY CON JPA E UN DATABASE  ***************
	// ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Utente createUtente(@RequestBody @Validated RegistrazioneUtentePayload u) {
		return utenteService.create(u);
	}

	//@GetMapping("")
	//public List<Utente> readUtenti() {
	//	return utenteService.readAll();
	//}

	@GetMapping("")
	public Page<Utente> readUtenti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return utenteService.readAll(page, size, sortBy);
	}

	@GetMapping("/{utenteId}")
	public Utente readUtente(@PathVariable UUID utenteId) throws Exception {
		return utenteService.readById(utenteId);
	}

	@PutMapping("/{utenteId}")
	public Utente updateUtente(@PathVariable UUID utenteId, @RequestBody RegistrazioneUtentePayload u) throws Exception {
		return utenteService.update(utenteId, u);
	}

	@DeleteMapping("/{utenteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUtente(@PathVariable UUID utenteId) throws Exception {
		utenteService.delete(utenteId);
	}

}
