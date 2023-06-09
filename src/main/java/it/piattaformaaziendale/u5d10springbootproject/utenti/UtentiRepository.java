package it.piattaformaaziendale.u5d10springbootproject.utenti;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {
  Optional<Utente> findByEmail(String email);
}
