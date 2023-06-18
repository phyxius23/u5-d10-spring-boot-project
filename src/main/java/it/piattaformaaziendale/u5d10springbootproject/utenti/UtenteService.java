package it.piattaformaaziendale.u5d10springbootproject.utenti;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.piattaformaaziendale.u5d10springbootproject.exceptions.BadRequestException;
import it.piattaformaaziendale.u5d10springbootproject.exceptions.NotFoundException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.RegistrazioneUtentePayload;

@Service
public class UtenteService {

	@Autowired
	UtenteRepository utenteRepo;

	// *************** CRUD ***************
	// ***** ***** ************ ***** *****

	// ***** CREATE *****
	public Utente create(RegistrazioneUtentePayload u) {
		// se l'email è già presente nel DB lancio una eccezione
		utenteRepo.findByEmail(u.getEmail()).ifPresent(utente -> {
			throw new BadRequestException("Email " + utente.getEmail() + " already in use!");
		});

		Utente newUtente = new Utente(u.getUsername(), u.getNome(), u.getCognome(), u.getEmail(), u.getPassword());

		return utenteRepo.save(newUtente);
	}

	//***** READ *****
	public Page<Utente> readAll(int page, int size, String sortBy) {
		if (size < 0)
			size = 0;
		if (size > 100)
			size = 100;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return utenteRepo.findAll(pageable);
	}

	// read by Id
	public Utente readById(UUID utenteId) throws NotFoundException {
		return utenteRepo.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
	}

	// read by email
	public Utente readByEmail(String email) throws NotFoundException {
		return utenteRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata!"));
	}

	//***** UPDATE *****
	public Utente update(UUID utenteId, RegistrazioneUtentePayload u) throws NotFoundException {
		Utente utenteFound = this.readById(utenteId);

		utenteFound.setId(utenteId);
		utenteFound.setUsername(u.getUsername());
		utenteFound.setNome(u.getNome());
		utenteFound.setCognome(u.getCognome());
		utenteFound.setEmail(u.getEmail());
		utenteFound.setPassword(u.getPassword());

		return utenteRepo.save(utenteFound);
	}

	//***** DELETE *****
	public void delete(UUID utenteId) throws NotFoundException {
		Utente utenteFound = this.readById(utenteId);

		utenteRepo.delete(utenteFound);
	}

}
