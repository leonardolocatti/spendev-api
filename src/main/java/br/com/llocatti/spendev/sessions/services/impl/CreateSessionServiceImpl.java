package br.com.llocatti.spendev.sessions.services.impl;

import br.com.llocatti.spendev.sessions.dtos.CreateSessionRequest;
import br.com.llocatti.spendev.sessions.dtos.CreateSessionResponse;
import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import br.com.llocatti.spendev.sessions.providers.TokenProvider;
import br.com.llocatti.spendev.sessions.services.CreateSessionService;
import br.com.llocatti.spendev.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateSessionServiceImpl implements CreateSessionService {

  @SuppressWarnings("unused")
  @Autowired
  private UsersRepository usersRepository;

  @SuppressWarnings("unused")
  @Autowired
  private PasswordEncoder passwordEncoder;

  @SuppressWarnings("unused")
  @Autowired
  private TokenProvider tokenProvider;

  @Override
  public CreateSessionResponse execute(CreateSessionRequest createSessionRequest) {
    var findUser = usersRepository.findByEmail(createSessionRequest.getEmail());

    if (findUser.isEmpty()) {
      throw new AuthenticationException("Wrong email/password combination");
    }

    if (!passwordEncoder.matches(
        createSessionRequest.getPassword(), findUser.get().getPassword())) {
      throw new AuthenticationException("Wrong email/password combination");
    }

    var token = tokenProvider.generateToken(findUser.get().getId().toString());

    return new CreateSessionResponse(token);
  }
}
