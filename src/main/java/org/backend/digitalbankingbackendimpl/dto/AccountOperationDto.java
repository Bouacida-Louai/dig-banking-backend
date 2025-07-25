package org.backend.digitalbankingbackendimpl.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.digitalbankingbackendimpl.entities.BankAccount;
import org.backend.digitalbankingbackendimpl.enums.OperationType;

import java.util.Date;


@Data

public class AccountOperationDto {


    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private String description;
}
