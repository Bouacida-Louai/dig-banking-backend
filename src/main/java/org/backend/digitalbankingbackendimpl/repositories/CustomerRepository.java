package org.backend.digitalbankingbackendimpl.repositories;

import org.backend.digitalbankingbackendimpl.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
