package br.com.llocatti.spendev.users.services.impl;

import br.com.llocatti.spendev.common.exceptions.BusinessException;
import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.dtos.CreateUserResponse;
import br.com.llocatti.spendev.users.mappers.UsersMapper;
import br.com.llocatti.spendev.users.repositories.UsersRepository;
import br.com.llocatti.spendev.users.services.CreateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreateUserServiceImpl implements CreateUserService {

  @SuppressWarnings("unused")
  @Autowired
  private UsersRepository usersRepository;

  @SuppressWarnings("unused")
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public CreateUserResponse execute(CreateUserRequest createUserRequest) {
    if (!createUserRequest.getPassword().equals(createUserRequest.getPasswordConfirmation())) {
      var businessException =
          new BusinessException("Password and password confirmation do not match");

      log.debug("{} in {}", businessException.getMessage(), createUserRequest);

      throw businessException;
    }

    var findUser = usersRepository.findByEmail(createUserRequest.getEmail());

    if (findUser.isPresent()) {
      var businessException = new BusinessException("Email already used by another user");

      log.debug("{} in {}", businessException.getMessage(), createUserRequest);

      throw businessException;
    }

    createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

    var user = UsersMapper.toEntity(createUserRequest);

    user = usersRepository.save(user);

    return UsersMapper.toResponse(user);
  }
}
