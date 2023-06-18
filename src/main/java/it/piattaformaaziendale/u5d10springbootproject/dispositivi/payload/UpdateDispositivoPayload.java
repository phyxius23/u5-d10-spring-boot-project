package it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload;

import java.util.UUID;

import it.piattaformaaziendale.u5d10springbootproject.dispositivi.StatoDispositivo;
import it.piattaformaaziendale.u5d10springbootproject.dispositivi.TipoDispositivo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDispositivoPayload {

	@NotNull(message = "Il tipo di dispositivo è obbligatorio")
	@Enumerated(EnumType.STRING)
	private TipoDispositivo tipoDispositivo;

	@NotNull(message = "Lo stato del dispositivo è obbligatorio")
	@Enumerated(EnumType.STRING)
	private StatoDispositivo statoDispositivo;

	@NotNull(message = "L'id dell'utente è obbligatorio")
	private UUID utenteId;
}
