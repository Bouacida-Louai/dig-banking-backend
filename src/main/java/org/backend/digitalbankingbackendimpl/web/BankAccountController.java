package org.backend.digitalbankingbackendimpl.web;

import lombok.AllArgsConstructor;
import org.backend.digitalbankingbackendimpl.dto.AccountHistoryDto;
import org.backend.digitalbankingbackendimpl.dto.AccountOperationDto;
import org.backend.digitalbankingbackendimpl.dto.BankAccountDto;
import org.backend.digitalbankingbackendimpl.exceptions.BankAccountNotFoundException;
import org.backend.digitalbankingbackendimpl.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor

public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping("/account/{id}")
    public BankAccountDto getBankAccount(@PathVariable  String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);

    }

    @GetMapping("/accounts")

    public List<BankAccountDto> listeAccount() {

        return bankAccountService.bankAccountListe();
    }

    @GetMapping("/account/{id}/operations")
    public List<AccountOperationDto> getHistory(@PathVariable String id) throws BankAccountNotFoundException {
        return
            bankAccountService.accountHistory(id);
    }

@GetMapping("/account/{id}/pageOperations")
    public AccountHistoryDto getAccountHistory(
            @PathVariable String id,
            @RequestParam(name = "page" ,defaultValue = "0") int page,
            @RequestParam(name = "size" , defaultValue = "5") int size                                               )
            throws BankAccountNotFoundException {
        return
                bankAccountService.getAccountHistory(id,page,size);
    }




}
