package org.backend.digitalbankingbackendimpl.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.backend.digitalbankingbackendimpl.entities.AccountOperation;
import org.backend.digitalbankingbackendimpl.entities.Customer;
import org.backend.digitalbankingbackendimpl.enums.AccountStatus;

import java.util.Date;
@Data
public class SavingBankAccountDto extends BankAccountDto{
    private String id;
    private Date CreatedAt;
    private AccountStatus status ;
    private Double balance;
   private CustomerDto customer;
   private double interestRate ;
}
