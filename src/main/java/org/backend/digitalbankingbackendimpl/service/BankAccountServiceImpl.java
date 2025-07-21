package org.backend.digitalbankingbackendimpl.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.digitalbankingbackendimpl.entities.*;

//import org.backend.digitalbankingbackendimpl.exceeptions.CustomerNotFounfException;
import org.backend.digitalbankingbackendimpl.enums.OperationType;
import org.backend.digitalbankingbackendimpl.exceptions.BalanceNotSuuficentException;
import org.backend.digitalbankingbackendimpl.exceptions.BankAccountNotFoundException;
import org.backend.digitalbankingbackendimpl.exceptions.CustomerNotFoundException;
import org.backend.digitalbankingbackendimpl.repositories.AccountOperationRepository;
import org.backend.digitalbankingbackendimpl.repositories.BankAccountRepository;
import org.backend.digitalbankingbackendimpl.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j

public class BankAccountServiceImpl implements BankAccountService {


    CustomerRepository customerRepository;

    BankAccountRepository  bankAccountRepository;

     AccountOperationRepository  accountOperationRepository;


    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving a new Customer");
        Customer savedCustomer = customerRepository.save(customer);
        return  savedCustomer;

    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: ");
        }

        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);


        return savedBankAccount;



    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: ");
        }

        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);

        return bankAccountRepository.save(currentAccount);
    }





    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank Account Not Found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException ,BalanceNotSuuficentException {

        BankAccount bankAccount =getBankAccount(accountId);

        if (bankAccount.getBalance()< amount)
            throw new BalanceNotSuuficentException("Balance Not Suuficent");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);


    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount =getBankAccount(accountId);


        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotSuuficentException, BankAccountNotFoundException {

        debit(accountIdSource,amount,"transfer to " + accountIdDestination);
        credit(accountIdDestination,amount,"transfer from " + accountIdSource);

    }
    @Override
    public List<BankAccount> bankAccountListe(){
        return bankAccountRepository.findAll();
    }
}
