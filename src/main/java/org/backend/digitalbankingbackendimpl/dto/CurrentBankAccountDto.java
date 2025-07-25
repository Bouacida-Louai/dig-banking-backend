package org.backend.digitalbankingbackendimpl.dto;

import lombok.Data;
import org.backend.digitalbankingbackendimpl.enums.AccountStatus;
import java.util.Date;

@Data
public class CurrentBankAccountDto extends BankAccountDto {
    private String id;
    private Date CreatedAt;
    private AccountStatus status ;
    private Double balance;
   private CustomerDto customer;
   private double overDraft ;
}
