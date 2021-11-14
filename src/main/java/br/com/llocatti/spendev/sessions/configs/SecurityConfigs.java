package br.com.llocatti.spendev.sessions.configs;

import br.com.llocatti.spendev.sessions.dtos.AuthenticationError;
import br.com.llocatti.spendev.sessions.filters.TokenFilter;
import br.com.llocatti.spendev.sessions.providers.TokenProvider;
import br.com.llocatti.spendev.sessions.providers.impl.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfigs extends WebSecurityConfigurerAdapter {

  private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};

  private static final String[] PUBLIC_MATCHERS_POST = {"/api/v1/users", "/api/v1/sessions"};

  @Autowired private TokenProvider tokenProvider;

  @Autowired private Environment environment;

  @Autowired private UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (Arrays.asList(environment.getActiveProfiles()).contains("dev")
        || Arrays.asList(environment.getActiveProfiles()).contains("test")) {
      http.headers().frameOptions().disable();
      http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll();
    }

    http.cors().and().csrf().disable();

    http.authorizeRequests()
        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
        .permitAll()
        .anyRequest()
        .authenticated();

    http.addFilter(new TokenFilter(authenticationManager(), userDetailsService, tokenProvider));
    http.exceptionHandling().authenticationEntryPoint(new EntryPoint());

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtTokenProvider jwtTokenProvider() {
    return new JwtTokenProvider();
  }

  public static final class EntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        AuthenticationException e)
        throws IOException, ServletException {
      var authenticationError = new AuthenticationError("Bad credentials");

      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse
          .getWriter()
          .append(new ObjectMapper().writeValueAsString(authenticationError));
    }
  }
}
