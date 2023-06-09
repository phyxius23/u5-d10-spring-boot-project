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
import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.UtenteRegistrazionePayload;

@Service
public class UtentiService {

  @Autowired
  UtentiRepository utentiRepo;

  // crea utente
  public Utente create(UtenteRegistrazionePayload u) {
    utentiRepo.findByEmail(u.getEmail()).ifPresent(utente -> {
      throw new BadRequestException("Email " + utente.getEmail() + " già usata!");
    });
    Utente nuovoUtente = new Utente(u.getUsername(), u.getNome(), u.getCognome(), u.getEmail(), u.getPassword());
    return utentiRepo.save(nuovoUtente);
  }

  // cerca passando ID
  public Utente findById(UUID id) throws NotFoundException {
    // metodo che non mi permette di resitituire un messaggio all'eccezione
    return utentiRepo.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato!"));

    // return utentiRepo.findById(id).orElseThrow(() -> new NotFoundException());
  }

  // cerca passando ID e modifica
  public Utente findByIdAndUpdate(UUID id, Utente u) throws NotFoundException {
    Utente found = this.findById(id);

    found.setId(id);
    found.setUsername(u.getUsername());
    found.setNome(u.getNome());
    found.setCognome(u.getCognome());
    found.setEmail(u.getEmail());
    found.setPassword(u.getPassword());

    return utentiRepo.save(found);
  }

  // cerca passando ID ed elimina
  public void findByIdAndDelete(UUID id) throws NotFoundException {
    Utente found = this.findById(id);
    utentiRepo.delete(found);
  }

  // paginazione di tutti i dispositivi
  public Page<Utente> find(int page, int size, String sortBy) {
    if (size < 0)
      size = 10;
    if (size > 100)
      size = 100;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

    return utentiRepo.findAll(pageable);
  }

  // cerca per email => metodo implementato per poter gestire il login
  public Utente findByEmail(String email) throws NotFoundException {
    return utentiRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata"));
  }

}
