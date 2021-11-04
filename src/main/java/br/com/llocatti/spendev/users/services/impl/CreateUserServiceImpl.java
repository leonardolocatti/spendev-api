package br.com.llocatti.spendev.users.services.impl;

import br.com.llocatti.spendev.common.exceptions.BusinessException;
import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.dtos.CreateUserResponse;
import br.com.llocatti.spendev.users.mappers.UsersMapper;
import br.com.llocatti.spendev.users.repositories.UsersRepository;
import br.com.llocatti.spendev.users.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserServiceImpl implements CreateUserService {

  @Autowired private UsersRepository usersRepository;

  @Override
  public CreateUserResponse execute(CreateUserRequest createUserRequest) {
    if (!createUserRequest.getPassword().equals(createUserRequest.getPasswordConfirmation())) {
      throw new BusinessException("Password and password confirmation do not match");
    }

    var findUser = usersRepository.findByEmail(createUserRequest.getEmail());

    if (findUser.isPresent()) {
      throw new BusinessException("Email already used by another user");
    }

    var user = UsersMapper.toEntity(createUserRequest);

    user = usersRepository.save(user);

    return UsersMapper.toResponse(user);
  }
}
