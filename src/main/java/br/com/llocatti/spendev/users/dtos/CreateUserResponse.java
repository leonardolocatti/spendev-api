package br.com.llocatti.spendev.users.dtos;

import br.com.llocatti.spendev.users.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateUserResponse {

  private final UUID id;

  private final String name;

  private final String email;

  public static CreateUserResponse fromEntity(User user) {
    return new CreateUserResponse(user.getId(), user.getName(), user.getEmail());
  }
}
