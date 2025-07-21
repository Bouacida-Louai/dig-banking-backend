package org.backend.digitalbankingbackendimpl.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue("SAV")
@Data
@AllArgsConstructor @NoArgsConstructor
public class SavingAccount extends  BankAccount {

     private double interestRate;


}
