package it.piattaformaaziendale.u5d10springbootproject.dispositivi;

import java.util.UUID;

import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "dispositivi")
public class Dispositivo {

  // Attributi
  @Id
  @GeneratedValue
  private UUID id;

  @NotNull
  private String description;

  @Enumerated(EnumType.STRING)
  private TipoDispositivo tipoDispositivo;

  @Enumerated(EnumType.STRING)
  private StatoDispositivo statoDispositivo;

  @ManyToOne
  private Utente utente;

  // Costruttore
  public Dispositivo(@NotNull String description, TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo,
      Utente utente) {
    this.description = description;
    this.tipoDispositivo = tipoDispositivo;
    this.statoDispositivo = statoDispositivo;
    this.utente = null;
  }

}
