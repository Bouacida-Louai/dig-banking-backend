package org.backend.digitalbankingbackendimpl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.digitalbankingbackendimpl.enums.OperationType;

import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    @ManyToOne
    private BankAccount bankAccount;
}
