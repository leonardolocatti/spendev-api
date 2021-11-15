package br.com.llocatti.spendev.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public class ApplicationError {

  @JsonIgnore private final HttpStatus httpStatus;
  private final String message;

  @SuppressWarnings("unused")
  public Integer getCode() {
    return httpStatus.value();
  }

  @SuppressWarnings("unused")
  public String getError() {
    return httpStatus.getReasonPhrase();
  }
}
