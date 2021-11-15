package br.com.llocatti.spendev.wallets.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
public class Wallet {

  @Id @GeneratedValue private UUID id;

  private String name;

  private String description;

  private BigDecimal amount;
}
