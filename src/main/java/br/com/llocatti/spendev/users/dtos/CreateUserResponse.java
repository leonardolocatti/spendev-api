package br.com.llocatti.spendev.users.dtos;

import br.com.llocatti.spendev.users.entities.User;

import java.util.UUID;

public class CreateUserResponse {

  private final UUID id;

  private final String name;

  private final String email;

  public CreateUserResponse(UUID id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public static CreateUserResponse fromEntity(User user) {
    return new CreateUserResponse(user.getId(), user.getName(), user.getEmail());
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}
