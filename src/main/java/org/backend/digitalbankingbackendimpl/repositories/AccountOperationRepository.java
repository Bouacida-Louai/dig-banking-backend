package org.backend.digitalbankingbackendimpl.repositories;

import org.backend.digitalbankingbackendimpl.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
