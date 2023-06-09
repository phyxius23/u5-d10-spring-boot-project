package it.piattaformaaziendale.u5d10springbootproject.utenti;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

import it.piattaformaaziendale.u5d10springbootproject.utenti.payload.UtenteRegistrazionePayload;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

  @Autowired
  UtentiService utentiService;

  // metodo di test
  // @GetMapping("")
  // public String getUtenti() {
  // return "Lista di utenti";
  // }

  @GetMapping("")
  public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
    return utentiService.find(page, size, sortBy);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Utente saveUser(@RequestBody @Validated UtenteRegistrazionePayload body) {
    return utentiService.create(body);
  }

  @GetMapping("/{utentiId}")
  public Utente getUser(@PathVariable UUID utentiId) throws Exception {
    return utentiService.findById(utentiId);
  }

  @PutMapping("/{utentiId}")
  public Utente updateUser(@PathVariable UUID utentiId, @RequestBody Utente body) throws Exception {
    return utentiService.findByIdAndUpdate(utentiId, body);
  }

  @DeleteMapping("/{utentiId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable UUID utentiId) throws NotFoundException {
    utentiService.findByIdAndDelete(utentiId);
  }

}
