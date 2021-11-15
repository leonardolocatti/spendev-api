package br.com.llocatti.spendev.wallets.entities;

import br.com.llocatti.spendev.users.entities.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
public class Wallet {

  @Id @GeneratedValue private UUID id;

  private final String name;

  private final String description;

  private final BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private final User user;

  public Wallet(UUID id, String name, String description, BigDecimal amount, User user) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.amount = amount;
    this.user = user;
  }

  public Wallet(String name, String description, BigDecimal amount, User user) {
    this.name = name;
    this.description = description;
    this.amount = amount;
    this.user = user;
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

  public User getUser() {
    return user;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
