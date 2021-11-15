package br.com.llocatti.spendev.wallets.mocks;

import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletResponse;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletsMock {

  private static final String NAME = "Bank Account";
  private static final String DESCRIPTION = "Account on Dream's Bank";
  private static final BigDecimal AMOUNT = BigDecimal.TEN;
  private static final String INVALID_AMOUNT = "10.0";

  public static CreateWalletRequest validCreateWalletRequest() {
    return new CreateWalletRequest(NAME, DESCRIPTION, AMOUNT);
  }

  public static CreateWalletResponse validCreateWalletResponse() {
    return new CreateWalletResponse(UUID.randomUUID(), NAME, DESCRIPTION, AMOUNT);
  }
}
