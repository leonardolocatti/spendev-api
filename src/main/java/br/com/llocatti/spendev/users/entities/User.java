package br.com.llocatti.spendev.users.entities;

import br.com.llocatti.spendev.wallets.entities.Wallet;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class User {

  @Id @GeneratedValue private UUID id;

  private String name;

  private String email;

  private String password;

  @OneToMany(mappedBy = "user")
  private final List<Wallet> wallets = new ArrayList<>();

  public User() {}

  public User(String name, String email, String password) {
    id = null;

    this.name = name;
    this.email = email;
    this.password = password;
  }

  public User(UUID id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public User(UUID id) {
    this.id = id;
  }
}
