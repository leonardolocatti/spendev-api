package br.com.llocatti.spendev.wallets.dtos;

import br.com.llocatti.spendev.wallets.entities.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateWalletResponse {

  private final UUID id;

  private final String name;

  private final String description;

  private final BigDecimal amount;

  public static CreateWalletResponse fromEntity(Wallet wallet) {
    return new CreateWalletResponse(
        wallet.getId(), wallet.getName(), wallet.getDescription(), wallet.getAmount());
  }
}
