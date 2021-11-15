package br.com.llocatti.spendev.users.services.impl;

import br.com.llocatti.spendev.common.exceptions.BusinessException;
import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.dtos.CreateUserResponse;
import br.com.llocatti.spendev.users.mappers.UsersMapper;
import br.com.llocatti.spendev.users.repositories.UsersRepository;
import br.com.llocatti.spendev.users.services.CreateUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserServiceImpl implements CreateUserService {

  private final Logger logger = LoggerFactory.getLogger(CreateUserServiceImpl.class);

  @Autowired private UsersRepository usersRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public CreateUserResponse execute(CreateUserRequest createUserRequest) {
    if (!createUserRequest.getPassword().equals(createUserRequest.getPasswordConfirmation())) {
      var businessException =
          new BusinessException("Password and password confirmation do not match");

      logger.debug("{} in {}", businessException.getMessage(), createUserRequest);

      throw businessException;
    }

    var findUser = usersRepository.findByEmail(createUserRequest.getEmail());

    if (findUser.isPresent()) {
      var businessException = new BusinessException("Email already used by another user");

      logger.debug("{} in {}", businessException.getMessage(), createUserRequest);

      throw businessException;
    }

    createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

    var user = UsersMapper.toEntity(createUserRequest);

    user = usersRepository.save(user);

    return UsersMapper.toResponse(user);
  }
}
