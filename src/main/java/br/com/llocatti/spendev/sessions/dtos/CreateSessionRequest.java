package br.com.llocatti.spendev.sessions.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CreateSessionRequest {

  @NotEmpty(message = "The 'email' field must be filled")
  private String email;

  @NotEmpty(message = "The 'password' field must be filled")
  private String password;
}
