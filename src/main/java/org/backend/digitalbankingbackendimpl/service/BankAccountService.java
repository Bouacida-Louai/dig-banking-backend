package org.backend.digitalbankingbackendimpl.service;

import org.backend.digitalbankingbackendimpl.entities.BankAccount;
import org.backend.digitalbankingbackendimpl.entities.CurrentAccount;
import org.backend.digitalbankingbackendimpl.entities.Customer;
import org.backend.digitalbankingbackendimpl.entities.SavingAccount;
import org.backend.digitalbankingbackendimpl.exceptions.BalanceNotSuuficentException;
import org.backend.digitalbankingbackendimpl.exceptions.BankAccountNotFoundException;
import org.backend.digitalbankingbackendimpl.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    Customer saveCustomer(Customer customer);
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuuficentException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuuficentException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuuficentException;
    List<BankAccount> bankAccountListe();
}

