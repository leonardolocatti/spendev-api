package br.com.llocatti.spendev.users.controllers;

import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.dtos.CreateUserResponse;
import br.com.llocatti.spendev.users.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

  @SuppressWarnings("unused")
  @Autowired
  private CreateUserService createUserService;

  @SuppressWarnings("unused")
  @PostMapping
  public ResponseEntity<CreateUserResponse> createUser(
      @RequestBody @Valid CreateUserRequest createUserRequest) {
    var createdUser = createUserService.execute(createUserRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }
}
