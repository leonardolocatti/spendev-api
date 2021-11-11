package br.com.llocatti.spendev.sessions.controllers;

import br.com.llocatti.spendev.common.SpendevApplication;
import br.com.llocatti.spendev.common.utils.JsonUtils;
import br.com.llocatti.spendev.sessions.dtos.CreateSessionRequest;
import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import br.com.llocatti.spendev.sessions.mocks.SessionsMock;
import br.com.llocatti.spendev.sessions.services.CreateSessionService;
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

@WebMvcTest(SessionsController.class)
@ContextConfiguration(classes = SpendevApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SessionsControllerTest {

  static final String SESSIONS_PATH = "/api/v1/sessions";

  @Autowired MockMvc mockMvc;

  @MockBean CreateSessionService createSessionService;

  @Test
  @DisplayName("Should be able to create a new session")
  void shouldBeAbleToCreateNewSession() throws Exception {
    var createSessionRequest = SessionsMock.validCreateSessionRequest();
    var createSessionResponse = SessionsMock.validCreateSessionResponse();

    when(createSessionService.execute(any(CreateSessionRequest.class)))
        .thenReturn(createSessionResponse);

    var request =
        post(SESSIONS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createSessionRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("token").value(createSessionResponse.getToken()))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new session with incomplete data")
  void shouldNotBeAbleToCreateNewSessionWithIncompleteData() throws Exception {
    var createSessionRequest = new CreateSessionRequest();

    var request =
        post(SESSIONS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createSessionRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(jsonPath("message").value("There are fields with validation errors"))
        .andExpect(jsonPath("fields", hasSize(2)))
        .andExpect(
            jsonPath(
                "fields.*",
                containsInAnyOrder(
                    allOf(
                        hasEntry("name", "email"),
                        hasEntry("message", "The 'email' field must be filled")),
                    allOf(
                        hasEntry("name", "password"),
                        hasEntry("message", "The 'password' field must be filled")))))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new session with wrong email")
  void shouldNotBeAbleToCreateNewSessionWithWrongEmail() throws Exception {
    var createSessionRequest = SessionsMock.wrongEmailCreateSessionRequest();
    var exception = new AuthenticationException("Wrong email/password combination");

    when(createSessionService.execute(any(CreateSessionRequest.class))).thenThrow(exception);

    var request =
        post(SESSIONS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createSessionRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("code").value(HttpStatus.UNAUTHORIZED.value()))
        .andExpect(jsonPath("error").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
        .andExpect(jsonPath("message").value("Wrong email/password combination"))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new session with wrong password")
  void shouldNotBeAbleToCreateNewSessionWithWrongPassword() throws Exception {
    var createSessionRequest = SessionsMock.wrongPasswordCreateSessionRequest();
    var exception = new AuthenticationException("Wrong email/password combination");

    when(createSessionService.execute(any(CreateSessionRequest.class))).thenThrow(exception);

    var request =
        post(SESSIONS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createSessionRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("code").value(HttpStatus.UNAUTHORIZED.value()))
        .andExpect(jsonPath("error").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
        .andExpect(jsonPath("message").value("Wrong email/password combination"))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new session with missing request body")
  void shouldNotBeAbleToCreateNewSessionWithMissingRequestBody() throws Exception {
    var request = post(SESSIONS_PATH).accept(MediaType.APPLICATION_JSON);

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
}
