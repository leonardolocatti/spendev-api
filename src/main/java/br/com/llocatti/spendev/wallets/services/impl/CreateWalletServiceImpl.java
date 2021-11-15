package br.com.llocatti.spendev.wallets.services.impl;

import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletResponse;
import br.com.llocatti.spendev.wallets.mappers.WalletsMapper;
import br.com.llocatti.spendev.wallets.repositories.WalletsRepository;
import br.com.llocatti.spendev.wallets.services.CreateWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreateWalletServiceImpl implements CreateWalletService {

  @SuppressWarnings("unused")
  @Autowired
  private WalletsRepository walletsRepository;

  @Override
  public CreateWalletResponse execute(CreateWalletRequest createWalletRequest) {

    if (createWalletRequest.getUserId() == null) {
      var authenticationException = new AuthenticationException("Bad credentials");

      log.warn("{} in {}", authenticationException, createWalletRequest);

      throw authenticationException;
    }

    var wallet = WalletsMapper.toEntity(createWalletRequest);

    wallet = walletsRepository.save(wallet);

    return WalletsMapper.toResponse(wallet);
  }
}
