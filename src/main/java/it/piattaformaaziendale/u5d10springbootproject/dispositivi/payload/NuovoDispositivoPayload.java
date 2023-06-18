package it.piattaformaaziendale.u5d10springbootproject.dispositivi.payload;

import it.piattaformaaziendale.u5d10springbootproject.dispositivi.TipoDispositivo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NuovoDispositivoPayload {

	@NotNull(message = "Selezione del tipo di dispositivo obbligatoria")
	private TipoDispositivo tipoDispositivo;

}
