package br.com.llocatti.spendev.wallets.dtos;

import br.com.llocatti.spendev.users.entities.User;
import br.com.llocatti.spendev.wallets.entities.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateWalletRequest {

  @NotEmpty(message = "The 'name' field must be filled")
  private String name;

  private String description;

  @NotNull(message = "The 'initialAmount' field must be filled")
  private BigDecimal initialAmount;

  @JsonIgnore @Setter private UUID userId;

  public Wallet toEntity() {
    var user = new User(userId);

    return new Wallet(name, description, initialAmount, user);
  }
}
