package it.piattaformaaziendale.u5d10springbootproject.dispositivi;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload.DispositivoRegistrazionePayload;

@Service
public class DispositiviService {

  @Autowired
  DispositiviRepository dispositiviRepo;

  // crea dispositivo
  public Dispositivo create(DispositivoRegistrazionePayload d) {
    Dispositivo nuovoDispositivo = new Dispositivo(d.getDescription(), d.getTipoDispositivo(), d.getStatoDispositivo(),
        d.getUtente());

    return dispositiviRepo.save(nuovoDispositivo);
  }

  // cerca passando ID
  public Dispositivo findById(UUID id) throws NotFoundException {
    // metodo che non mi permette di resitituire un messaggio all'eccezione
    // return dispositiviRepo.findById(id).orElseThrow(() -> new
    // NotFoundException("Dispositivo non trovato!"));

    return dispositiviRepo.findById(id).orElseThrow(() -> new NotFoundException());
  }

  // cerca passando ID e modifica
  public Dispositivo findByIdAndUpdate(UUID id, DispositivoRegistrazionePayload d) throws NotFoundException {
    Dispositivo found = this.findById(id);

    found.setId(id);
    found.setDescription(d.getDescription());
    found.setTipoDispositivo(d.getTipoDispositivo());
    found.setStatoDispositivo(d.getStatoDispositivo());
    found.setUtente(d.getUtente());

    return dispositiviRepo.save(found);
  }

  // elimina passando ID ed elimina
  public void findByIdAndDelete(UUID id) throws NotFoundException {
    Dispositivo found = this.findById(id);
    dispositiviRepo.delete(found);
  }

  // paginazione di tutti i dispositivi
  public Page<Dispositivo> find(int page, int size, String sortBy) {
    if (size < 0)
      size = 10;
    if (size > 100)
      size = 100;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

    return dispositiviRepo.findAll(pageable);
  }

}
