package br.com.llocatti.spendev.sessions.providers;

public interface TokenProvider {

  String generateToken(String userId);

  String getTokenSubject(String token);
}
