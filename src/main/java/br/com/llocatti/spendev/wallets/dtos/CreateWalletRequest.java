package br.com.llocatti.spendev.wallets.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateWalletRequest {

  @NotEmpty(message = "The 'name' field must be filled")
  private String name;

  private String description;

  @NotNull(message = "The 'initialAmount' field must be filled")
  private BigDecimal initialAmount;

  public CreateWalletRequest() {}

  public CreateWalletRequest(String name, String description, BigDecimal initialAmount) {
    this.name = name;
    this.description = description;
    this.initialAmount = initialAmount;
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
}
