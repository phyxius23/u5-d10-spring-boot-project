package it.piattaformaaziendale.u5d10springbootproject.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import it.piattaformaaziendale.u5d10springbootproject.exceptions.NotFoundException;
import it.piattaformaaziendale.u5d10springbootproject.exceptions.UnauthorizedException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import it.piattaformaaziendale.u5d10springbootproject.utenti.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	UtenteService utenteService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 0. Questo metodo verrà invocato per ogni request
		// 1. Prima di tutto dovrò estrarre il token dall'Authorization Header
		String authHeader = request.getHeader("Authorization"); // estraggo l'Header dal token

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore aggiungi il token all'authorization header");

		String accessToken = authHeader.substring(7); // estraggo dall'header tutti i caratteri esclusi i primi 7, vado ad eliminare "Bearer "

		// 2. Verifico che il token non sia stato nè manipolato nè sia scaduto
		JwtTools.isTokenValid(accessToken); // prende il token e mi ritorna true/false

		// 3. Se OK --> puoi procedere al prossimo blocco della filterChain

		// 3.1 Estraggo l'email dal token in modo da sapere chi è l'utente
		String email = JwtTools.extractSubject(accessToken);
		try {
			Utente utente = utenteService.readByEmail(email);

			// 3.2 Aggiungo l'utente al SecurityContextHolder //=> è lo scatolone che contiene gli utenti loggati
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(utente, null,
					utente.getAuthorities());

			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);

			// 3.3  Puoi procedere al prossimo blocco del filterChain
			filterChain.doFilter(request, response);
		} catch (NotFoundException e) {
			throw new NotFoundException("Richiesta non valida");
		}

		// Se il punto 2 lancia l'eccezione passa al punto 4
		// 4. Se NON OK --> 401 ("Per favore effettua nuovamente il login")
	}

	// *************** PER EVITARE CHE IL FILTRO VENGA ESEGUITO PER OGNI RICHIESTA ***************
	// *************** ************* *************** *************** ************* ***************
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}
