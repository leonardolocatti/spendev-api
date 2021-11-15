package br.com.llocatti.spendev.common.configs;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile(value = {"prod", "dev"})
@EnableJpaRepositories("br.com.llocatti.spendev")
@EntityScan("br.com.llocatti.spendev")
@SuppressWarnings("unused")
public class PersistenceConfigs {}
