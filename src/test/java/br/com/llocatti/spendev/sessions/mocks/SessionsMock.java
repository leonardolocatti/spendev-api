package br.com.llocatti.spendev.sessions.mocks;

import br.com.llocatti.spendev.sessions.dtos.CreateSessionRequest;
import br.com.llocatti.spendev.sessions.dtos.CreateSessionResponse;

public class SessionsMock {

  private static final String EMAIL = "johndoe@email.com";
  private static final String WRONG_EMAIL = "john@email.com";
  private static final String PASSWORD = "pass123";
  private static final String WRONG_PASSWORD = "pass12";
  private static final String TOKEN = "mocked-token";

  public static CreateSessionRequest validCreateSessionRequest() {
    return new CreateSessionRequest(EMAIL, PASSWORD);
  }

  public static CreateSessionRequest wrongEmailCreateSessionRequest() {
    return new CreateSessionRequest(WRONG_EMAIL, PASSWORD);
  }

  public static CreateSessionRequest wrongPasswordCreateSessionRequest() {
    return new CreateSessionRequest(EMAIL, WRONG_PASSWORD);
  }

  public static CreateSessionResponse validCreateSessionResponse() {
    return new CreateSessionResponse(TOKEN);
  }
}
