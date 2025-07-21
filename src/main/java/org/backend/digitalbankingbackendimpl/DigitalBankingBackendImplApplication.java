package org.backend.digitalbankingbackendimpl;

import org.backend.digitalbankingbackendimpl.entities.AccountOperation;
import org.backend.digitalbankingbackendimpl.entities.BankAccount;
import org.backend.digitalbankingbackendimpl.entities.Customer;
import org.backend.digitalbankingbackendimpl.entities.SavingAccount;
import org.backend.digitalbankingbackendimpl.enums.AccountStatus;
import org.backend.digitalbankingbackendimpl.repositories.AccountOperationRepository;
import org.backend.digitalbankingbackendimpl.repositories.BankAccountRepository;
import org.backend.digitalbankingbackendimpl.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendImplApplication.class, args);



        }


}



