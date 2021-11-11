package br.com.llocatti.spendev.users.services.impl;

import br.com.llocatti.spendev.common.exceptions.BusinessException;
import br.com.llocatti.spendev.users.entities.User;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CreateUserServiceImplTest {

  @InjectMocks CreateUserServiceImpl createUserService;

  @Mock UsersRepository usersRepository;

  @Mock PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Should be able to create new user")
  void shouldBeAbleToCreateNewUser() {
    var createUserRequest = UsersMock.validCreateUserRequest();
    var createdUser = UsersMock.validUser();

    when(usersRepository.save(any(User.class))).thenReturn(createdUser);

    var createUserResponse = createUserService.execute(createUserRequest);

    assertThat(createUserResponse.getId()).isEqualTo(createdUser.getId());
    assertThat(createUserResponse.getName()).isEqualTo(createdUser.getName());
    assertThat(createUserResponse.getEmail()).isEqualTo(createdUser.getEmail());
  }

  @Test
  @DisplayName("Should not be able to create a new user with same email from another")
  void shouldNotBeAbleToCreateNewUserWithSameEmailFromAnother() {
    var createUserRequest = UsersMock.validCreateUserRequest();
    var findUser = new User();
    var exception = new BusinessException("Email already used by another user");

    when(usersRepository.findByEmail(createUserRequest.getEmail()))
        .thenReturn(Optional.of(findUser));

    var exceptionThrown =
        assertThrows(BusinessException.class, () -> createUserService.execute(createUserRequest));

    assertThat(exceptionThrown.getMessage()).isEqualTo(exception.getMessage());
  }

  @Test
  @DisplayName("Should not be able to create a new user with wrong passwordConfirmation")
  void shouldNotBeAbleToCreateNewUserWithWrongPasswordConfirmation() {
    var createUserRequest = UsersMock.createUserRequestWithInvalidPassword();
    var exception = new BusinessException("Password and password confirmation do not match");

    var exceptionThrown =
        assertThrows(BusinessException.class, () -> createUserService.execute(createUserRequest));

    assertThat(exceptionThrown.getMessage()).isEqualTo(exception.getMessage());
  }

  @Test
  @DisplayName("Should be able to encrypt the user password")
  void shouldBeAbleToEncryptTheUserPassword() {
    var createUserRequest = UsersMock.validCreateUserRequest();

    var createdUser = UsersMock.validUser();

    when(usersRepository.save(any(User.class))).thenReturn(createdUser);
    when(passwordEncoder.encode(createUserRequest.getPassword()))
        .thenReturn(createUserRequest.getPassword());

    createUserService.execute(createUserRequest);

    verify(passwordEncoder, times(1)).encode(createUserRequest.getPassword());
  }
}
