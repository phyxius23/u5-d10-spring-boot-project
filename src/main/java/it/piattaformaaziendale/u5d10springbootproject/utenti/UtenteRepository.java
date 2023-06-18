package it.piattaformaaziendale.u5d10springbootproject.utenti;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {

	public Optional<Utente> findByEmail(String email);

}
