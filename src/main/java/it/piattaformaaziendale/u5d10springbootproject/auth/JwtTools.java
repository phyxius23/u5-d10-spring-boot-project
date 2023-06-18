package it.piattaformaaziendale.u5d10springbootproject.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import it.piattaformaaziendale.u5d10springbootproject.exceptions.UnauthorizedException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;

@Component
public class JwtTools {

	//recupero secretKey e expirationKey dall'application.properties
	private static String secret;
	private static int expiration;

	@Value("${spring.application.jwt.secret}")
	public void setSecret(String s) {
		secret = s;
	}

	@Value("${spring.application.jwt.expiration}")
	public void setExpiration(String e) {
		expiration = Integer.parseInt(e) * 24 * 60 * 60 * 1000; //=> ho trasformato il valore in millisecondi 
	}

	// metodo che crea un token
	static public String createToken(Utente u) {
		String token = Jwts.builder() 																									 //=> Jwts è la libreria che mi serve - builder() per creare il token
				.setSubject(u.getEmail()) 																									 //=> setSubject il proprietario del token
				.setIssuedAt(new Date(System.currentTimeMillis())) 													 //=> setIssuedAt() quando è stato emesso il token (trasformato in millisecondi)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))						 //=> setExpiration() quando scade il token (trasformato in millisecondi)
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())) 														 //=> signWith() aggiungo la firma del token, l'unica cosa che devo sapere di questa riga è che devo passare il "segreto" trasfomato in bytes all'algoritmo HMACSHA256
				.compact(); 																																 //=> compact() ????????
		return token;
	}

	//metodo che verifica se il token è valido 
	static public void isTokenValid(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
		} catch (MalformedJwtException e) { //=> mi lancia una eccezione se il token è malformato (che vuol dire?)
			throw new UnauthorizedException("Il token è stato manipolato, non è valido");
		} catch (ExpiredJwtException e) { //=> mi lancia una eccezione se il token è scaduto 
			throw new UnauthorizedException("Il token è scaduto");
		} catch (Exception e) {
			throw new UnauthorizedException("Problema con il token. Per favore esegui di nuovo l'accesso.");
		}
	}

	//metodo che estrae il subject (in questo caso l'email) dal nostro token
	static public String extractSubject(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
				.getBody().getSubject();
	}

}
