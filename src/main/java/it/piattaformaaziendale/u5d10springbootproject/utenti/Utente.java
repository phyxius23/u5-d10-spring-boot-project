package it.piattaformaaziendale.u5d10springbootproject.utenti;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.piattaformaaziendale.u5d10springbootproject.dispositivi.Dispositivo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "utenti")
public class Utente implements UserDetails {

  // Attributi
  @Id
  @GeneratedValue
  private UUID id;
  private String username;
  private String nome;
  private String cognome;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "utente")
  List<Dispositivo> dispositivi;

  // Costruttore
  public Utente(String username, String nome, String cognome, String email, String password) {
    this.username = username;
    this.nome = nome;
    this.cognome = cognome;
    this.email = email;
    this.password = password;
    // this.dispositivi = dispositivi;
    this.role = Role.USER;

  }

  // ******* METODI DA TESTARE *************
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

}
