package br.com.llocatti.spendev.wallets.repositories;

import br.com.llocatti.spendev.wallets.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletsRepository extends JpaRepository<Wallet, UUID> {}
