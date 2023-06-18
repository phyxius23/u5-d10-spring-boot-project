package it.piattaformaaziendale.u5d10springbootproject.dispositivi;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload.NuovoDispositivoPayload;
import it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload.UpdateDispositivoPayload;
import it.piattaformaaziendale.u5d10springbootproject.exceptions.NotFoundException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import it.piattaformaaziendale.u5d10springbootproject.utenti.UtenteService;

@Service
public class DispositivoService {

	@Autowired
	DispositivoRepository dispositivoRepo;

	@Autowired
	UtenteService utenteService;

	//*************** CRUD ***************
	// ***** ***** ************ ***** *****

	// ***** CREATE *****
	public Dispositivo create(NuovoDispositivoPayload d) {
		// TODO: implemento i dati senza nessuna validazione
		Dispositivo newDispositivo = new Dispositivo(d.getTipoDispositivo());

		return dispositivoRepo.save(newDispositivo);
	}

	//***** READ *****
	public Page<Dispositivo> readAll(int page, int size, String sortBy) {

		if (size < 0)
			size = 0;
		if (size > 100)
			size = 100;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return dispositivoRepo.findAll(pageable);
	}

	// read by Id
	public Dispositivo readById(UUID dispositivoId) throws NotFoundException {
		return dispositivoRepo.findById(dispositivoId).orElseThrow(() -> new NotFoundException(dispositivoId));
	}

	//***** UPDATE *****
	public Dispositivo update(UUID dispositivoId, UpdateDispositivoPayload d) throws NotFoundException {
		Dispositivo dispositivoFound = this.readById(dispositivoId);

		Utente utenteFound = utenteService.readById(d.getUtenteId());
		dispositivoFound.setId(dispositivoId);
		dispositivoFound.setTipoDispositivo(d.getTipoDispositivo());
		dispositivoFound.setStatoDispositivo(d.getStatoDispositivo());
		dispositivoFound.setUtente(utenteFound);

		return dispositivoRepo.save(dispositivoFound);
	}

	//***** DELETE *****
	public void delete(UUID dispositivoId) throws NotFoundException {
		Dispositivo dispositivoFound = this.readById(dispositivoId);

		dispositivoRepo.delete(dispositivoFound);
	}
}
