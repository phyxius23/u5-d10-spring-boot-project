package it.piattaformaaziendale.u5d10springbootproject.dispositivi;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositiviRepository extends JpaRepository<Dispositivo, UUID> {

}
