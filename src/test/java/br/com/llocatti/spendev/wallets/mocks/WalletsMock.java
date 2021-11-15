package br.com.llocatti.spendev.wallets.mocks;

import br.com.llocatti.spendev.users.entities.User;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletResponse;
import br.com.llocatti.spendev.wallets.entities.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletsMock {

  private static final String NAME = "Bank Account";
  private static final String DESCRIPTION = "Account on Dream's Bank";
  private static final BigDecimal AMOUNT = BigDecimal.TEN;

  public static CreateWalletRequest validCreateWalletRequest() {
    return new CreateWalletRequest(NAME, DESCRIPTION, AMOUNT, null);
  }

  public static CreateWalletRequest validCreateWalletRequest(UUID useId) {
    return new CreateWalletRequest(NAME, DESCRIPTION, AMOUNT, useId);
  }

  public static CreateWalletResponse validCreateWalletResponse() {
    return new CreateWalletResponse(UUID.randomUUID(), NAME, DESCRIPTION, AMOUNT);
  }

  public static Wallet validWallet(UUID userId) {
    return new Wallet(UUID.randomUUID(), NAME, DESCRIPTION, AMOUNT, new User(userId));
  }
}
