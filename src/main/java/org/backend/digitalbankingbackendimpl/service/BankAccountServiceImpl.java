package org.backend.digitalbankingbackendimpl.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.digitalbankingbackendimpl.dto.*;
import org.backend.digitalbankingbackendimpl.entities.*;

//import org.backend.digitalbankingbackendimpl.exceeptions.CustomerNotFounfException;
import org.backend.digitalbankingbackendimpl.enums.OperationType;
import org.backend.digitalbankingbackendimpl.exceptions.BalanceNotSuuficentException;
import org.backend.digitalbankingbackendimpl.exceptions.BankAccountNotFoundException;
import org.backend.digitalbankingbackendimpl.exceptions.CustomerNotFoundException;
import org.backend.digitalbankingbackendimpl.mappers.BankAccountMapperImpl;
import org.backend.digitalbankingbackendimpl.repositories.AccountOperationRepository;
import org.backend.digitalbankingbackendimpl.repositories.BankAccountRepository;
import org.backend.digitalbankingbackendimpl.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.resource.ResourceResolver;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j

public class BankAccountServiceImpl implements BankAccountService {


    private final ResourceResolver resourceResolver;
    CustomerRepository customerRepository;

    BankAccountRepository bankAccountRepository;

    AccountOperationRepository accountOperationRepository;

    BankAccountMapperImpl dtoMapper;


    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        log.info("Saving a new Customer");
        Customer customer = dtoMapper.fromCustomerDtoToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomerToCustomerDto(savedCustomer);


    }

    @Override
    public SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
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


        return dtoMapper.fromSavingBankAccount(savedBankAccount);


    }

    @Override
    public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
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

        return dtoMapper.fromCurrentBankAccount(savedBankAccount);

    }


    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> dtoMapper
                        .fromCustomerToCustomerDto(customer)).collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(() ->
                        new BankAccountNotFoundException("Bank Account Not Found"));


        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            {
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }
    }


    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuuficentException {

        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(() ->
                        new BankAccountNotFoundException("Bank Account Not Found"));

        if (bankAccount.getBalance() < amount)
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
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(() ->
                        new BankAccountNotFoundException("Bank Account Not Found"));


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

        debit(accountIdSource, amount, "transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "transfer from " + accountIdSource);

    }

    @Override
    public List<BankAccountDto> bankAccountListe() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();


        List<BankAccountDto> bankAccountDtos = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountDtos;

    }

    @Override
    public CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found "));
        return dtoMapper.fromCustomerToCustomerDto(customer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        log.info("Saving a new Customer");
        Customer customer = dtoMapper.fromCustomerDtoToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomerToCustomerDto(savedCustomer);


    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }



    @Override
    public List<AccountOperationDto> accountHistory(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
              return   accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());

    }

    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {

        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) throw  new BankAccountNotFoundException("Bank Account not found");

         Page <AccountOperation> accountOperation= accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));

                AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
                 List<AccountOperationDto> accountOperationDtos = accountOperation.getContent().stream()
                         .map(op-> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
                 accountHistoryDto.setAccountOperationDtos(accountOperationDtos);

                 accountHistoryDto.setAccountId(bankAccount.getId());
                 accountHistoryDto.setBalance(bankAccount.getBalance());
                 accountHistoryDto.setCurrentPage(page);
                 accountHistoryDto.setPageSize(size);
                 accountHistoryDto.setTotalPage(accountOperation.getTotalPages());

                 return  accountHistoryDto;

    }
}
