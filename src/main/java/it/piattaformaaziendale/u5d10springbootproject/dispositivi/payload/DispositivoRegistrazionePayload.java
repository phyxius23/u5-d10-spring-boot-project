package it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload;

import it.piattaformaaziendale.u5d10springbootproject.dispositivi.StatoDispositivo;
import it.piattaformaaziendale.u5d10springbootproject.dispositivi.TipoDispositivo;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DispositivoRegistrazionePayload {

  @NotNull(message = "La descrizione del prodotto è obbligatoria")
  @Size(min = 3, max = 30, message = "Marca min 10 caratteri, massimo 30")
  String description;

  @NotNull(message = "È obbligatorio scegliere il tipo di dispositivo")
  TipoDispositivo tipoDispositivo;

  @NotNull(message = "È obbligatorio impostare lo stato del dispositivo")
  StatoDispositivo statoDispositivo;

  // @Null
  Utente utente;

}
