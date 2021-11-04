package br.com.llocatti.spendev.users.mappers;

import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.dtos.CreateUserResponse;
import br.com.llocatti.spendev.users.entities.User;

public final class UsersMapper {

  private UsersMapper() {}

  public static User toEntity(CreateUserRequest createUserRequest) {
    return createUserRequest.toEntity();
  }

  public static CreateUserResponse toResponse(User user) {
    return CreateUserResponse.fromEntity(user);
  }
}
