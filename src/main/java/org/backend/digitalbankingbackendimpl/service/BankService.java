package org.backend.digitalbankingbackendimpl.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.backend.digitalbankingbackendimpl.entities.BankAccount;
import org.backend.digitalbankingbackendimpl.entities.CurrentAccount;
import org.backend.digitalbankingbackendimpl.entities.SavingAccount;
import org.backend.digitalbankingbackendimpl.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
@Transactional
@Service
public class BankService {

    @Autowired

    private BankAccountRepository bankAccountRepository;
    public void consulter(){

            BankAccount bankAccount =bankAccountRepository.
                    findById("59ab00a0-fde3-47b2-afd3-4f0e1f88a77b").orElse(null);
            if (bankAccount != null) {
                System.out.println(bankAccount.getId());
                System.out.println(bankAccount.getBalance());
                System.out.println(bankAccount.getStatus());
                System.out.println(bankAccount.getCreatedAt());
                System.out.println(bankAccount.getCustomer().getName());
                System.out.println(bankAccount.getClass().getSimpleName());
                if (bankAccount instanceof CurrentAccount) {
                    System.out.println("Overdraft: " + ((CurrentAccount) bankAccount).getOverDraft());
                } else if (bankAccount instanceof SavingAccount) {
                    System.out.println("Interest Rate: " + ((SavingAccount) bankAccount).getInterestRate());
                }
                bankAccount.getAccountOperations().forEach(op->{
                    System.out.println(op.getOperationType() + "/" + op.getOperationDate());
                });
            }


        };

}
