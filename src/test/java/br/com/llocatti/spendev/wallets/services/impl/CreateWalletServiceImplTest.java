package br.com.llocatti.spendev.wallets.services.impl;

import br.com.llocatti.spendev.sessions.exceptions.AuthenticationException;
import br.com.llocatti.spendev.wallets.entities.Wallet;
import br.com.llocatti.spendev.wallets.mocks.WalletsMock;
import br.com.llocatti.spendev.wallets.repositories.WalletsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CreateWalletServiceImplTest {

  @InjectMocks CreateWalletServiceImpl createWalletService;

  @Mock WalletsRepository walletsRepository;

  @Test
  @DisplayName("Should be able to create new wallet")
  void shouldBeAbleToCreateNewWallet() {
    var uuid = UUID.randomUUID();
    var createWalletRequest = WalletsMock.validCreateWalletRequest(uuid);
    var createdWallet = WalletsMock.validWallet(uuid);

    when(walletsRepository.save(any(Wallet.class))).thenReturn(createdWallet);

    var createWalletResponse = createWalletService.execute(createWalletRequest);

    assertThat(createWalletResponse.getId()).isEqualTo(createdWallet.getId());
    assertThat(createWalletResponse.getName()).isEqualTo(createdWallet.getName());
    assertThat(createWalletResponse.getDescription()).isEqualTo(createdWallet.getDescription());
    assertThat(createWalletResponse.getAmount()).isEqualTo(createdWallet.getAmount());
    assertThat(createdWallet.getUser().getId()).isEqualTo(uuid);
  }

  @Test
  @DisplayName("Should not be able to create new wallet without authenticated user id")
  void shouldNotBeAbleToCreateNewWalletWithoutAuthenticatedUserId() {
    var createWalletRequest = WalletsMock.validCreateWalletRequest();
    var authenticationException = new AuthenticationException("Bad credentials");

    when(walletsRepository.save(any(Wallet.class))).thenThrow(authenticationException);

    var exceptionThrown =
        assertThrows(
            AuthenticationException.class, () -> createWalletService.execute(createWalletRequest));

    assertThat(exceptionThrown.getMessage()).isEqualTo(authenticationException.getMessage());
  }
}
