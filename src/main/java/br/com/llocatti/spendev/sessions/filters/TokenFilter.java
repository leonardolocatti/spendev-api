package br.com.llocatti.spendev.sessions.filters;

import br.com.llocatti.spendev.sessions.providers.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends BasicAuthenticationFilter {

  private static final String TOKEN_PREFIX = "Bearer ";

  private final UserDetailsService userDetailsService;

  private final TokenProvider tokenProvider;

  public TokenFilter(
      AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService,
      TokenProvider tokenProvider) {
    super(authenticationManager);

    this.userDetailsService = userDetailsService;
    this.tokenProvider = tokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    var authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
      var token = authorizationHeader.replace(TOKEN_PREFIX, "");
      var authentication = getAuthentication(token);

      if (authentication != null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    var subject = tokenProvider.getTokenSubject(token);

    if (subject != null) {
      var user = userDetailsService.loadUserByUsername(subject);

      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      }
    }

    return null;
  }
}
