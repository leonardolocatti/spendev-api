package br.com.llocatti.spendev.wallets.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateWalletResponse {

  private final UUID id;

  private final String name;

  private final String description;

  private final BigDecimal amount;

  public CreateWalletResponse(UUID id, String name, String description, BigDecimal amount) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.amount = amount;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
