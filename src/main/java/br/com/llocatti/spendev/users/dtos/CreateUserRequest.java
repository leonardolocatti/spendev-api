package br.com.llocatti.spendev.users.dtos;

import br.com.llocatti.spendev.users.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CreateUserRequest {

  @NotEmpty(message = "The 'name' field must be filled")
  private String name;

  @NotEmpty(message = "The 'email' field must be filled")
  @Email(message = "The 'email' field must be filled with a valid email")
  private String email;

  @Setter
  @NotEmpty(message = "The 'password' field must be filled")
  @Size(
      min = 6,
      message = "The 'password' field must be filled in with a password of at least 6 digits")
  private String password;

  @NotEmpty(message = "The 'passwordConfirmation' field must be filled")
  private String passwordConfirmation;

  public User toEntity() {
    return new User(name, email, password);
  }
}
