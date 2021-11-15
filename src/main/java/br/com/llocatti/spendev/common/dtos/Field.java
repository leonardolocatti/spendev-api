package br.com.llocatti.spendev.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Field {

  private final String name;

  private final String message;
}
