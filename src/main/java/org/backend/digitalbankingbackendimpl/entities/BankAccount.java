package org.backend.digitalbankingbackendimpl.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.digitalbankingbackendimpl.enums.AccountStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(name = "TYPE" ,  discriminatorType = DiscriminatorType.STRING)
@Entity
@Data
@NoArgsConstructor @AllArgsConstructor

public  class BankAccount {

    @Id

    private String id;
    private Date CreatedAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status ;
    private Double balance;
    @ManyToOne
    private  Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private List<AccountOperation> accountOperations;


}
