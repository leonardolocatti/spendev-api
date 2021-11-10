package br.com.llocatti.spendev.sessions.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtils {

  @Value("${security.jwt.secret}")
  private String secret;

  public String createToken(String userId) {
    var algorithm = Algorithm.HMAC512(secret);

    return JWT.create()
        .withSubject(userId)
        .withIssuer("spendev")
        .withExpiresAt(calculateExpiresAt())
        .sign(algorithm);
  }

  private Date calculateExpiresAt() {
    var calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, 15);

    return calendar.getTime();
  }
}
