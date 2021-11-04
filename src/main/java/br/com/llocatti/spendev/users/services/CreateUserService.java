package br.com.llocatti.spendev.users.services;

import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.dtos.CreateUserResponse;

public interface CreateUserService {

  CreateUserResponse execute(CreateUserRequest createUserRequest);
}
