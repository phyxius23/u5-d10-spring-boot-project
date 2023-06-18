package it.piattaformaaziendale.u5d10springbootproject.utenti.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrazioneUtentePayload {

	// attributi da validare
	@NotNull(message = "Username obbligatorio")
	@Size(min = 3, max = 30, message = "Username min 3 caratteri, massimo 30")
	private String username;
	@NotNull(message = "Nome obbligatorio")
	@Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30")
	private String nome;
	@NotNull(message = "Cognome obbligatorio")
	@Size(min = 3, max = 30, message = "Cognome min 3 caratteri, massimo 30")
	private String cognome;
	@Email(message = "Non hai inserito un indirizzo email valido")
	private String email;
	@NotNull(message = "Password obbligatoria")
	private String password;
}
