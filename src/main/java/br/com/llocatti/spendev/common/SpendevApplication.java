package br.com.llocatti.spendev.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("br.com.llocatti.spendev")
@SpringBootApplication
public class SpendevApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpendevApplication.class, args);
  }
}
