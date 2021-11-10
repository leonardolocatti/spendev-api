package br.com.llocatti.spendev.sessions.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfigs extends WebSecurityConfigurerAdapter {

  private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};

  private static final String[] PUBLIC_MATCHERS_POST = {"/api/v1/users", "/api/v1/sessions"};

  @Autowired Environment environment;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
      http.headers().frameOptions().disable();
      http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll();
    }

    http.cors().and().csrf().disable();

    http.authorizeRequests()
        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
        .permitAll()
        .anyRequest()
        .authenticated();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
