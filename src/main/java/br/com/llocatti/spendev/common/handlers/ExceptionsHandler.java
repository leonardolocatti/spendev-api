package br.com.llocatti.spendev.common.handlers;

import br.com.llocatti.spendev.common.dtos.ApplicationError;
import br.com.llocatti.spendev.common.dtos.BusinessError;
import br.com.llocatti.spendev.common.dtos.Field;
import br.com.llocatti.spendev.common.dtos.ValidationError;
import br.com.llocatti.spendev.common.exceptions.BusinessException;
import br.com.llocatti.spendev.sessions.dtos.AuthenticationError;
import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

  private final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationError> methodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    var validationError = new ValidationError();

    ex.getFieldErrors()
        .forEach(
            fieldError ->
                validationError.addField(
                    new Field(fieldError.getField(), fieldError.getDefaultMessage())));

    logger.debug("Validation error occurred: {}", validationError);

    return ResponseEntity.status(validationError.getHttpStatus()).body(validationError);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApplicationError> httpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    var applicationError =
        new ApplicationError(
            HttpStatus.BAD_REQUEST,
            "Poorly formed request. Check request parameters and try again");

    logger.warn("Received poor request. Exception message: {}", ex.getMessage());

    return ResponseEntity.status(applicationError.getHttpStatus()).body(applicationError);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BusinessError> businessException(BusinessException ex) {
    var businessError = new BusinessError(ex.getMessage());

    logger.debug("Business exception occurred: {}", businessError.getMessage());

    return ResponseEntity.status(businessError.getHttpStatus()).body(businessError);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<AuthenticationError> authenticationException(AuthenticationException ex) {
    var authenticationError = new AuthenticationError(ex.getMessage());

    logger.debug("Authentication exception occurred: {}", ex.getMessage());

    return ResponseEntity.status(authenticationError.getHttpStatus()).body(authenticationError);
  }
}
