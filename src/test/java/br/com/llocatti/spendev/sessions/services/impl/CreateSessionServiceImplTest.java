package br.com.llocatti.spendev.sessions.services.impl;

import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import br.com.llocatti.spendev.sessions.mocks.SessionsMock;
import br.com.llocatti.spendev.sessions.providers.TokenProvider;
import br.com.llocatti.spendev.users.mocks.UsersMock;
import br.com.llocatti.spendev.users.repositories.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CreateSessionServiceImplTest {

  @InjectMocks CreateSessionServiceImpl createSessionService;

  @Mock UsersRepository usersRepository;

  @Mock PasswordEncoder passwordEncoder;

  @Mock TokenProvider tokenProvider;

  @Test
  @DisplayName("Should be able to create new session")
  void shouldBeAbleToCreateNewSession() {
    var createSessionRequest = SessionsMock.validCreateSessionRequest();
    var findUser = UsersMock.validUser();

    when(usersRepository.findByEmail(createSessionRequest.getEmail()))
        .thenReturn(Optional.of(findUser));

    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(Boolean.TRUE);

    when(tokenProvider.generateToken(anyString())).thenReturn("token");

    var createSessionResponse = createSessionService.execute(createSessionRequest);

    assertThat(createSessionResponse.getToken()).isNotEmpty();
  }

  @Test
  @DisplayName("Should not be able to create a new session with nonexistent email")
  void shouldNotBeAbleToCreateNewSessionWithNonexistentEmail() {
    var createSessionRequest = SessionsMock.wrongEmailCreateSessionRequest();
    var exception = new AuthenticationException("Wrong email/password combination");

    var exceptionThrow =
        assertThrows(
            AuthenticationException.class,
            () -> createSessionService.execute(createSessionRequest));

    assertThat(exceptionThrow.getMessage()).isEqualTo(exception.getMessage());
  }

  @Test
  @DisplayName("Should not be able to create a new session with nonexistent email")
  void shouldNotBeAbleToCreateNewSessionWithWrongPassword() {
    var createSessionRequest = SessionsMock.wrongPasswordCreateSessionRequest();
    var findUser = UsersMock.validUser();
    var exception = new AuthenticationException("Wrong email/password combination");

    when(usersRepository.findByEmail(createSessionRequest.getEmail()))
        .thenReturn(Optional.of(findUser));

    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(Boolean.FALSE);

    var exceptionThrow =
        assertThrows(
            AuthenticationException.class,
            () -> createSessionService.execute(createSessionRequest));

    assertThat(exceptionThrow.getMessage()).isEqualTo(exception.getMessage());
  }
}
