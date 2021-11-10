package br.com.llocatti.spendev.sessions.services;

import br.com.llocatti.spendev.sessions.dtos.CreateSessionRequest;
import br.com.llocatti.spendev.sessions.dtos.CreateSessionResponse;

public interface CreateSessionService {
  CreateSessionResponse execute(CreateSessionRequest createSessionRequest);
}
