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

import it.piattaformaaziendale.u5d10springbootproject.exceptions.UnauthorizedException;
import it.piattaformaaziendale.u5d10springbootproject.utenti.Utente;
import it.piattaformaaziendale.u5d10springbootproject.utenti.UtentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

  @Autowired
  UtentiService utentiService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // 0. Questo metodo verrà invocato per ogni request
    // 1. Prima di tutto dovrò estrarre il token dall'Authorization Header
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer "))
      throw new UnauthorizedException("Manca il token all'authorization header");

    String accessToken = authHeader.substring(7);

    // 2. Verifico che il token non sia stato nè manipolato nè sia scaduto
    JwtTools.isTokenValid(accessToken);

    // 3. Se OK

    // 3.0 Estraggo l'email dal token e cerco l'utente
    String email = JwtTools.extractSubject(accessToken);
    System.out.println("******************************** " + email);
    Utente utente = utentiService.findByEmail(email);

    // 3.1 Aggiungo l'utente al SecurityContextHolder

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(utente, null,
        utente.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authToken);

    // 3.2 puoi procedere al prossimo blocco della filterChain
    filterChain.doFilter(request, response);

    // 4. Se non OK -> 401 ("Per favore effettua di nuovo il login")
  }

  // Per evitare che il filtro venga eseguito per OGNI richiesta

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return new AntPathMatcher().match("/auth/**", request.getServletPath());
  }

}
