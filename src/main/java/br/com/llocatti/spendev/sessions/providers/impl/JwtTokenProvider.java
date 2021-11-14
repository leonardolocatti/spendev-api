package br.com.llocatti.spendev.sessions.providers.impl;

import br.com.llocatti.spendev.sessions.providers.TokenProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.Date;

public class JwtTokenProvider implements TokenProvider {

  private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${security.token.secret}")
  private String secret;

  @Value("${security.token.issuer}")
  private String issuer;

  @Value("${security.token.seconds-to-expire}")
  private Integer secondsToExpire;

  @Override
  public String generateToken(String userId) {
    var algorithm = Algorithm.HMAC512(secret);

    return JWT.create()
        .withSubject(userId)
        .withIssuer(issuer)
        .withExpiresAt(calculateExpiresAt())
        .sign(algorithm);
  }

  private Date calculateExpiresAt() {
    var calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.SECOND, secondsToExpire);

    return calendar.getTime();
  }

  @Override
  public String getTokenSubject(String token) {
    try {
      var algorithm = Algorithm.HMAC512(secret);

      var jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();

      var decodedJwt = jwtVerifier.verify(token);

      return decodedJwt.getSubject();
    } catch (JWTVerificationException ex) {
      logger.error("Jwt token parse exception: {}", ex.getMessage());

      return null;
    }
  }
}
