package br.com.llocatti.spendev.wallets.dtos;

import br.com.llocatti.spendev.users.entities.User;
import br.com.llocatti.spendev.wallets.entities.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateWalletRequest {

  @NotEmpty(message = "The 'name' field must be filled")
  private String name;

  private String description;

  @NotNull(message = "The 'initialAmount' field must be filled")
  private BigDecimal initialAmount;

  @JsonIgnore private UUID userId;

  public CreateWalletRequest() {}

  public CreateWalletRequest(
      String name, String description, BigDecimal initialAmount, UUID userId) {
    this.name = name;
    this.description = description;
    this.initialAmount = initialAmount;
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getInitialAmount() {
    return initialAmount;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public Wallet toEntity() {
    var user = new User(userId);

    return new Wallet(name, description, initialAmount, user);
  }
}
