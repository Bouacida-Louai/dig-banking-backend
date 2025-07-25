package org.backend.digitalbankingbackendimpl.mappers;


import lombok.AllArgsConstructor;
import org.backend.digitalbankingbackendimpl.dto.*;
import org.backend.digitalbankingbackendimpl.entities.AccountOperation;
import org.backend.digitalbankingbackendimpl.entities.CurrentAccount;
import org.backend.digitalbankingbackendimpl.entities.Customer;
import org.backend.digitalbankingbackendimpl.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class BankAccountMapperImpl {

   public CustomerDto fromCustomerToCustomerDto( Customer customer){
       CustomerDto customerDto = new CustomerDto();
       BeanUtils.copyProperties(customer,customerDto);
       return customerDto;
   }

    public Customer fromCustomerDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

    public SavingBankAccountDto fromSavingBankAccount(SavingAccount savingAccount){
       SavingBankAccountDto savingBankAccountDto = new SavingBankAccountDto();
       BeanUtils.copyProperties(savingAccount , savingBankAccountDto);
       savingBankAccountDto.setCustomer(fromCustomerToCustomerDto(savingAccount.getCustomer()));
       savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());
       return savingBankAccountDto;
    }

    public SavingAccount FromSavingAccountDto(SavingBankAccountDto savingBankAccountDto){
       SavingAccount savingAccount  = new SavingAccount();
         BeanUtils.copyProperties( savingBankAccountDto , savingAccount );
        savingAccount.setCustomer(fromCustomerDtoToCustomer(savingBankAccountDto.getCustomer()));
         return savingAccount;
    }

    public CurrentBankAccountDto fromCurrentBankAccount(CurrentAccount currentAccount){
       CurrentBankAccountDto currentBankAccountDto = new CurrentBankAccountDto();
       BeanUtils.copyProperties(currentAccount, currentBankAccountDto);
       currentBankAccountDto.setCustomer(fromCustomerToCustomerDto(currentAccount.getCustomer()));
        currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
       return currentBankAccountDto;
    }

    public CurrentAccount fromCurrentBankAccountDto(CurrentBankAccountDto currentBankAccountDto){
       CurrentAccount currentAccount = new CurrentAccount();
       BeanUtils.copyProperties(currentBankAccountDto , currentAccount);
        currentAccount.setCustomer(fromCustomerDtoToCustomer(currentBankAccountDto.getCustomer()));
       return currentAccount;

    }

    public AccountOperationDto fromAccountOperation (AccountOperation   accountOperation){

       AccountOperationDto accountOperationDto = new AccountOperationDto();
       BeanUtils.copyProperties(accountOperation , accountOperationDto);
       return accountOperationDto;

    }

}
