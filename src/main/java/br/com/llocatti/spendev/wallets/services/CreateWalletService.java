package br.com.llocatti.spendev.wallets.services;

import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletResponse;

public interface CreateWalletService {
  CreateWalletResponse execute(CreateWalletRequest createWalletRequest);
}
