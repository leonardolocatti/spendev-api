package br.com.llocatti.spendev.common.handlers;

import br.com.llocatti.spendev.common.dtos.ApplicationError;
import br.com.llocatti.spendev.common.dtos.BusinessError;
import br.com.llocatti.spendev.common.dtos.Field;
import br.com.llocatti.spendev.common.dtos.ValidationError;
import br.com.llocatti.spendev.common.exceptions.BusinessException;
import br.com.llocatti.spendev.sessions.dtos.AuthenticationError;
import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@SuppressWarnings("unused")
public class ExceptionsHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationError> methodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    var validationError = new ValidationError();

    ex.getFieldErrors()
        .forEach(
            fieldError ->
                validationError.addField(
                    new Field(fieldError.getField(), fieldError.getDefaultMessage())));

    log.debug("Validation error occurred: {}", validationError);

    return ResponseEntity.status(validationError.getHttpStatus()).body(validationError);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApplicationError> httpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    var applicationError =
        new ApplicationError(
            HttpStatus.BAD_REQUEST,
            "Poorly formed request. Check request parameters and try again");

    log.warn("Received poor request. Exception message: {}", ex.getMessage());

    return ResponseEntity.status(applicationError.getHttpStatus()).body(applicationError);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BusinessError> businessException(BusinessException ex) {
    var businessError = new BusinessError(ex.getMessage());

    log.debug("Business exception occurred: {}", businessError.getMessage());

    return ResponseEntity.status(businessError.getHttpStatus()).body(businessError);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<AuthenticationError> authenticationException(AuthenticationException ex) {
    var authenticationError = new AuthenticationError(ex.getMessage());

    log.debug("Authentication exception occurred: {}", ex.getMessage());

    return ResponseEntity.status(authenticationError.getHttpStatus()).body(authenticationError);
  }
}
