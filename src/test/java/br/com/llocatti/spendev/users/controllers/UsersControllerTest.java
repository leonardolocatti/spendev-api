package br.com.llocatti.spendev.users.controllers;

import br.com.llocatti.spendev.common.SpendevApplication;
import br.com.llocatti.spendev.common.exceptions.BusinessException;
import br.com.llocatti.spendev.common.utils.JsonUtils;
import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.mocks.UsersMock;
import br.com.llocatti.spendev.users.services.CreateUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@ContextConfiguration(classes = SpendevApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsersControllerTest {

  static final String USERS_PATH = "/api/v1/users";

  @Autowired MockMvc mockMvc;

  @MockBean CreateUserService createUserService;

  @Test
  @DisplayName("Should be able to create a new user")
  void shouldBeAbleToCreateNewUser() throws Exception {
    var createUserRequest = UsersMock.validCreateUserRequest();
    var createUserResponse = UsersMock.validCreateUserResponse();

    when(createUserService.execute(any(CreateUserRequest.class))).thenReturn(createUserResponse);

    var request =
        post(USERS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createUserRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").value(createUserResponse.getId().toString()))
        .andExpect(jsonPath("name").value(createUserRequest.getName()))
        .andExpect(jsonPath("email").value(createUserRequest.getEmail()))
        .andExpect(jsonPath("password").doesNotExist())
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new user with incomplete data")
  void shouldNotBeAbleToCreateNewUserWithIncompleteData() throws Exception {
    var createUserRequest = new CreateUserRequest();

    var request =
        post(USERS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createUserRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(jsonPath("message").value("There are fields with validation errors"))
        .andExpect(jsonPath("fields", hasSize(4)))
        .andExpect(
            jsonPath(
                "fields.*",
                containsInAnyOrder(
                    allOf(
                        hasEntry("name", "name"),
                        hasEntry("message", "The 'name' field must be filled")),
                    allOf(
                        hasEntry("name", "email"),
                        hasEntry("message", "The 'email' field must be filled")),
                    allOf(
                        hasEntry("name", "password"),
                        hasEntry("message", "The 'password' field must be filled")),
                    allOf(
                        hasEntry("name", "passwordConfirmation"),
                        hasEntry("message", "The 'passwordConfirmation' field must be filled")))))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new user with invalid email")
  void shouldNotBeAbleToCreateNewUserWithInvalidEmail() throws Exception {
    var createUserRequest = UsersMock.createUserRequestWithInvalidEmail();

    var request =
        post(USERS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createUserRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(jsonPath("message").value("There are fields with validation errors"))
        .andExpect(jsonPath("fields", hasSize(1)))
        .andExpect(
            jsonPath(
                "fields[0]",
                allOf(
                    hasEntry("name", "email"),
                    hasEntry("message", "The 'email' field must be filled with a valid email"))))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new user with a password of less than 6 digits")
  void shouldNotBeAbleToCreateNewUserWithPasswordOfLessThan6Digits() throws Exception {
    var createUserRequest = UsersMock.createUserRequestWithInvalidPassword();

    var request =
        post(USERS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createUserRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(jsonPath("message").value("There are fields with validation errors"))
        .andExpect(jsonPath("fields", hasSize(1)))
        .andExpect(
            jsonPath(
                "fields[0]",
                allOf(
                    hasEntry("name", "password"),
                    hasEntry(
                        "message",
                        "The 'password' field must be filled in with a password of at least 6 digits"))))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new user with missing request body")
  void shouldNotBeAbleToCreateNewUserWithMissingRequestBody() throws Exception {
    var request = post(USERS_PATH).accept(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(
            jsonPath("message")
                .value("Poorly formed request. Check request parameters and try again"))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new user with business exception")
  void shouldNotBeAbleToCreateNewUserWithBusinessException() throws Exception {
    var createUserRequest = UsersMock.createUserRequestWithWrongPasswordConfirmation();
    var exception = new BusinessException("Business Exception");

    when(createUserService.execute(any(CreateUserRequest.class))).thenThrow(exception);

    var request =
        post(USERS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createUserRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(jsonPath("message").value(exception.getMessage()))
        .andDo(print());
  }
}
