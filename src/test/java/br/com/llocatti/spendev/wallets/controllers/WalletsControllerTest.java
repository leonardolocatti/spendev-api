package br.com.llocatti.spendev.wallets.controllers;

import br.com.llocatti.spendev.common.SpendevApplication;
import br.com.llocatti.spendev.common.utils.JsonUtils;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.mocks.WalletsMock;
import br.com.llocatti.spendev.wallets.services.CreateWalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
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

@WebMvcTest(WalletsController.class)
@ContextConfiguration(classes = SpendevApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser("c580bf79-577c-433e-b663-1d4e80841013")
class WalletsControllerTest {

  static final String WALLETS_PATH = "/api/v1/wallets";

  @Autowired MockMvc mockMvc;

  @MockBean CreateWalletService createWalletService;

  @Test
  @DisplayName("Should be able to create a new wallet")
  void shouldBeAbleToCreateNewWallet() throws Exception {
    var createWalletRequest = WalletsMock.validCreateWalletRequest();
    var createWalletResponse = WalletsMock.validCreateWalletResponse();

    when(createWalletService.execute(any(CreateWalletRequest.class)))
        .thenReturn(createWalletResponse);

    var request =
        post(WALLETS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createWalletRequest));

    mockMvc
        .perform(request)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").value(createWalletResponse.getId().toString()))
        .andExpect(jsonPath("name").value(createWalletRequest.getName()))
        .andExpect(jsonPath("description").value(createWalletRequest.getDescription()))
        .andExpect(jsonPath("amount").value(createWalletRequest.getInitialAmount()))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new wallet with incomplete data")
  void shouldNotBeAbleToCreateNewWalletWithIncompleteData() throws Exception {
    var createWalletRequest = new CreateWalletRequest();

    var request =
        post(WALLETS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.objectToJson(createWalletRequest));

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
                        hasEntry("name", "name"),
                        hasEntry("message", "The 'name' field must be filled")),
                    allOf(
                        hasEntry("name", "initialAmount"),
                        hasEntry("message", "The 'initialAmount' field must be filled")))))
        .andDo(print());
  }

  @Test
  @DisplayName("Should not be able to create a new wallet with invalid initialAmount")
  void shouldNotBeAbleToCreateNewWalletWithInvalidAmount() throws Exception {
    var createWalletRequest = "{\"name\":\"a\", \"initialAmount\":\"10.a\"}";

    var request =
        post(WALLETS_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createWalletRequest);

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
  @DisplayName("Should not be able to create a new wallet with missing request body")
  void shouldNotBeAbleToCreateNewWalletWithMissingRequestBody() throws Exception {
    var request = post(WALLETS_PATH).accept(MediaType.APPLICATION_JSON);

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
  @DisplayName("Should not be able to create a new wallet without being authenticated")
  @WithAnonymousUser
  void shouldNotBeAbleToCreateNewWalletWithoutBeingAuthenticated() throws Exception {
    var request = post(WALLETS_PATH).accept(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(request)
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("code").value(HttpStatus.UNAUTHORIZED.value()))
        .andExpect(jsonPath("error").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
        .andExpect(jsonPath("message").value("Bad credentials"))
        .andDo(print());
  }
}
