package it.piattaformaaziendale.u5d10springbootproject.dispositivi;

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

import it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload.NuovoDispositivoPayload;
import it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload.UpdateDispositivoPayload;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {

	@Autowired
	DispositivoService dispositivoService;

	//*************** UTILIZZO DI SERVICE, CONTROLLER E ***************
	// *************** REPOSITORY CON JPA E UN DATABASE  ***************
	// ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Dispositivo createDispositivo(@RequestBody @Validated NuovoDispositivoPayload d) {
		return dispositivoService.create(d);
	}

	//@GetMapping("")
	//public List<Dispositivo> readDispositivi() {
	//	return dispositivoService.readAll();
	//}

	@GetMapping("")
	public Page<Dispositivo> readDispositivi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return dispositivoService.readAll(page, size, sortBy);
	}

	@GetMapping("/{dispositivoId}")
	public Dispositivo readDispositivo(@PathVariable UUID dispositivoId) throws Exception {
		return dispositivoService.readById(dispositivoId);
	}

	@PutMapping("/{dispositivoId}")
	public Dispositivo updateDispositivo(@PathVariable UUID dispositivoId, @RequestBody UpdateDispositivoPayload d)
			throws Exception {
		return dispositivoService.update(dispositivoId, d);
	}

	@DeleteMapping("/{dispositivoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDispositivo(@PathVariable UUID dispositivoId) throws Exception {
		dispositivoService.delete(dispositivoId);
	}
}
