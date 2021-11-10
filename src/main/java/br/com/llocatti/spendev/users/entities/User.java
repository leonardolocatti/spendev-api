package br.com.llocatti.spendev.users.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table
public class User {

  @Id @GeneratedValue private UUID id;

  private String name;

  private String email;

  private String password;

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

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
