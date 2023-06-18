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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dispositivi")
public class Dispositivo {

	// attributi
	@Id
	@GeneratedValue
	private UUID id;

	@Enumerated(EnumType.STRING)
	private TipoDispositivo tipoDispositivo;

	@Enumerated(EnumType.STRING)
	private StatoDispositivo statoDispositivo;

	@ManyToOne
	private Utente utente;

	// costruttore senza utente perchè la presenza del dispositivo nel DB non è vincolata 
	// alla sua assegnazione ad un utente
	public Dispositivo(TipoDispositivo tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
		this.statoDispositivo = StatoDispositivo.DISPONIBILE;
	}

	public Dispositivo(TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo, Utente utente) {
		this.tipoDispositivo = tipoDispositivo;
		this.statoDispositivo = statoDispositivo;
		this.utente = utente;
	}

}
