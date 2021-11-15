package br.com.llocatti.spendev.sessions.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CreateSessionResponse {

  private final String token;
}
