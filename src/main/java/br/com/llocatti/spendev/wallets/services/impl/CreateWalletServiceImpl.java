package br.com.llocatti.spendev.wallets.services.impl;

import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletRequest;
import br.com.llocatti.spendev.wallets.dtos.CreateWalletResponse;
import br.com.llocatti.spendev.wallets.mappers.WalletsMapper;
import br.com.llocatti.spendev.wallets.repositories.WalletsRepository;
import br.com.llocatti.spendev.wallets.services.CreateWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateWalletServiceImpl implements CreateWalletService {

  private final Logger logger = LoggerFactory.getLogger(CreateWalletServiceImpl.class);

  @Autowired private WalletsRepository walletsRepository;

  @Override
  public CreateWalletResponse execute(CreateWalletRequest createWalletRequest) {

    if (createWalletRequest.getUserId() == null) {
      var authenticationException = new AuthenticationException("Bad credentials");

      logger.warn("{} in {}", authenticationException, createWalletRequest);

      throw authenticationException;
    }

    var wallet = WalletsMapper.toEntity(createWalletRequest);

    wallet = walletsRepository.save(wallet);

    return WalletsMapper.toResponse(wallet);
  }
}
