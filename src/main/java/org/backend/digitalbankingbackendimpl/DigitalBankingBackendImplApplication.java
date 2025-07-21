package org.backend.digitalbankingbackendimpl;

import org.backend.digitalbankingbackendimpl.entities.*;
import org.backend.digitalbankingbackendimpl.enums.AccountStatus;
import org.backend.digitalbankingbackendimpl.enums.OperationType;
import org.backend.digitalbankingbackendimpl.exceptions.BalanceNotSuuficentException;
import org.backend.digitalbankingbackendimpl.exceptions.BankAccountNotFoundException;
import org.backend.digitalbankingbackendimpl.exceptions.CustomerNotFoundException;
import org.backend.digitalbankingbackendimpl.repositories.AccountOperationRepository;
import org.backend.digitalbankingbackendimpl.repositories.BankAccountRepository;
import org.backend.digitalbankingbackendimpl.repositories.CustomerRepository;
import org.backend.digitalbankingbackendimpl.service.BankAccountService;
import org.backend.digitalbankingbackendimpl.service.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendImplApplication.class, args);

    }
@Bean
        CommandLineRunner init(BankAccountService  bankAccountService)  {
            return args -> {
               Stream.of("hassane","imane","mouhamed").forEach(name ->{
                   Customer customer= new Customer();
                   customer.setName(name);
                   customer.setEmail(name +"gmail.com");
                   bankAccountService.saveCustomer(customer);
               });
               bankAccountService.listCustomers().forEach(cust ->{

                   try {
                       bankAccountService.saveSavingBankAccount(Math.random()*8000,5,cust.getId());
                       bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000,cust.getId());
                       List<BankAccount> bankAccounts = bankAccountService.bankAccountListe();
                       for (BankAccount bankAccount : bankAccounts) {
                           for (int i = 0; i <10 ; i++) {
                               bankAccountService.credit(bankAccount.getId(), Math.random() * 9000, "Credit Operation");
                               bankAccountService.debit(bankAccount.getId(), 1000*Math.random()*9000 , "Debit Operation");
                           }
                       }

                   } catch (CustomerNotFoundException | BankAccountNotFoundException | BalanceNotSuuficentException e) {
                       throw new RuntimeException(e);
                   }


               });

               };
        };
}

////    @Bean
//        CommandLineRunner start(CustomerRepository customerRepository,
//                            BankAccountRepository bankAccountRepository,
//                            AccountOperationRepository accountOperationRepository) {
//        return args -> {
//            Stream.of("hassan","yassinr","Aicha").forEach(name -> {
//                Customer customer = new Customer();
//                customer.setName(name);
//                customer.setEmail(name+"@gmail.com");
//                customerRepository.save(customer);
//            });
//
//            customerRepository.findAll().forEach(cust->{
//
//                CurrentAccount currentAccount = new CurrentAccount();
//                currentAccount.setId(UUID.randomUUID().toString());
//                currentAccount.setBalance(Math.random()*9000);
//                currentAccount.setCreatedAt(new Date());
//                currentAccount.setStatus(AccountStatus.CREATED);
//                currentAccount.setCustomer(cust);
//                currentAccount.setOverDraft(9000);
//                bankAccountRepository.save(currentAccount);
//
//                SavingAccount savingAccount = new SavingAccount();
//                savingAccount.setId(UUID.randomUUID().toString());
//                savingAccount.setBalance(Math.random()*9000);
//                savingAccount.setCreatedAt(new Date());
//                savingAccount.setStatus(AccountStatus.CREATED);
//                savingAccount.setCustomer(cust);
//                savingAccount.setInterestRate(5.5);
//                bankAccountRepository.save(savingAccount);
//
//            });
//            bankAccountRepository.findAll().forEach(acc->{
//
//                for (int i=0 ;i<=10 ;i++) {
//                    AccountOperation accountOperation = new AccountOperation();
//                    accountOperation.setOperationDate(new Date());
//                    accountOperation.setAmount(Math.random()*9000);
//                    accountOperation.setOperationType(Math.random()> 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
//                    accountOperation.setBankAccount(acc);
//
//                    accountOperationRepository.save(accountOperation);
//
//                }
//            });
//
//        };
//        }
//}




