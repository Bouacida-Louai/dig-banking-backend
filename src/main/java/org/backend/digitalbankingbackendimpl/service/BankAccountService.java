package org.backend.digitalbankingbackendimpl.service;

import org.backend.digitalbankingbackendimpl.dto.*;
import org.backend.digitalbankingbackendimpl.entities.BankAccount;
import org.backend.digitalbankingbackendimpl.entities.CurrentAccount;
import org.backend.digitalbankingbackendimpl.entities.Customer;
import org.backend.digitalbankingbackendimpl.entities.SavingAccount;
import org.backend.digitalbankingbackendimpl.exceptions.BalanceNotSuuficentException;
import org.backend.digitalbankingbackendimpl.exceptions.BankAccountNotFoundException;
import org.backend.digitalbankingbackendimpl.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {



    CustomerDto saveCustomer(CustomerDto customerDto);

    SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    List<CustomerDto> listCustomers();
    BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuuficentException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuuficentException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuuficentException;
    List<BankAccountDto> bankAccountListe();

    CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(Long customerId);

    public List<AccountOperationDto> accountHistory(String accountId);

    AccountHistoryDto getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException;

//    List<AccountOperationDto> accountHistory(String accountId);
}

