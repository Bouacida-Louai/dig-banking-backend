package org.backend.digitalbankingbackendimpl.repositories;

import org.backend.digitalbankingbackendimpl.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
