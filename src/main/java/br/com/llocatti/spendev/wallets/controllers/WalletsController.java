package br.com.llocatti.spendev.wallets.controllers;

import br.com.llocatti.spendev.sessions.utils.SessionsUtils;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletResponse;
import br.com.llocatti.spendev.wallets.services.CreateWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletsController {

  @Autowired private CreateWalletService createWalletService;

  @PostMapping
  public ResponseEntity<CreateWalletResponse> createWallet(
      @RequestBody @Valid CreateWalletRequest createWalletRequest) {
    createWalletRequest.setUserId(SessionsUtils.getAuthenticatedUserId());

    var createdWallet = createWalletService.execute(createWalletRequest);

    return ResponseEntity.created(null).body(createdWallet);
  }
}
