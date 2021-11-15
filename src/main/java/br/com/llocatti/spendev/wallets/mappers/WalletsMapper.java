package br.com.llocatti.spendev.wallets.mappers;

import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletResponse;
import br.com.llocatti.spendev.wallets.entities.Wallet;

public class WalletsMapper {

  private WalletsMapper() {}

  public static Wallet toEntity(CreateWalletRequest createWalletRequest) {
    return createWalletRequest.toEntity();
  }

  public static CreateWalletResponse toResponse(Wallet wallet) {
    return CreateWalletResponse.fromEntity(wallet);
  }
}
