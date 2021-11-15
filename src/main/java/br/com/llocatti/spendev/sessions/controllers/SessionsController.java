package br.com.llocatti.spendev.sessions.controllers;

import br.com.llocatti.spendev.sessions.dtos.CreateSessionRequest;
import br.com.llocatti.spendev.sessions.dtos.CreateSessionResponse;
import br.com.llocatti.spendev.sessions.services.CreateSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

  @SuppressWarnings("unused")
  @Autowired
  private CreateSessionService createSessionService;

  @SuppressWarnings("unused")
  @PostMapping
  public ResponseEntity<CreateSessionResponse> createSession(
      @RequestBody @Valid CreateSessionRequest createSessionRequest) {
    var createdSession = createSessionService.execute(createSessionRequest);

    return ResponseEntity.ok(createdSession);
  }
}
