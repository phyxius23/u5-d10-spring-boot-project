package it.piattaformaaziendale.u5d10springbootproject.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.piattaformaaziendale.u5d10springbootproject.exceptions.UnauthorizedException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTools {

  // Attributi
  private static String secret;
  private static int expiration;

  @Value("${spring.application.jwt.secret}")
  public void setSecret(String secretKey) {
    secret = secretKey;
  }

  @Value("${spring.application.jwt.expirationindays}")
  public void setExpiration(String expirationInDays) {
    expiration = Integer.parseInt(expirationInDays) * 24 * 60 * 60 * 1000;
  }

  static public String createToken(Utente u) {
    String token = Jwts.builder().setSubject(u.getEmail()).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    return token;
  }

  static public void isTokenValid(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);

    } catch (MalformedJwtException e) {
      throw new UnauthorizedException("Il token non è valido");
    } catch (ExpiredJwtException e) {
      throw new UnauthorizedException("Il token è scaduto");
    } catch (Exception e) {
      throw new UnauthorizedException("Problemi col token, per favore effettua di nuovo il login");
    }
  }

  static public String extractSubject(String token) { // Nel nostro caso il subject è l'email dell'utente
    return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
        .getBody().getSubject();
  }

}
