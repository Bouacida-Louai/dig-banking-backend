package org.backend.digitalbankingbackendimpl.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.digitalbankingbackendimpl.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(name = "typeee" ,  discriminatorType = DiscriminatorType.STRING)
@Entity
@Data
@NoArgsConstructor @AllArgsConstructor

public class BankAccount {

    @Id
    private String id;
    private Double balance;
    private Date CreatedAt;
    private AccountStatus status ;
    @ManyToOne
    private  Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AccountOperation> accountOperations;


}
