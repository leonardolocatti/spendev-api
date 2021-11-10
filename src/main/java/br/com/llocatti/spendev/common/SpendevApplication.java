package br.com.llocatti.spendev.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.llocatti.spendev")
public class SpendevApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpendevApplication.class, args);
  }
}
