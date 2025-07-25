package org.backend.digitalbankingbackendimpl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.digitalbankingbackendimpl.dto.CustomerDto;
import org.backend.digitalbankingbackendimpl.entities.Customer;
import org.backend.digitalbankingbackendimpl.exceptions.CustomerNotFoundException;
import org.backend.digitalbankingbackendimpl.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/customers")
@AllArgsConstructor
//@Slf4j
public class CustomerRestController {

    BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDto> Customers(){

        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable (name = "id") Long CustomerId) throws CustomerNotFoundException {
        return  bankAccountService.getCustomer(CustomerId);
    }
    @PostMapping("/addCustomer")
  public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {

        return   bankAccountService.saveCustomer(customerDto);
    }

    @PutMapping("/updateCustomer/{id}")
    public CustomerDto updateCustomer(@PathVariable(name = "id") Long customerId,
                            @RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
        customerDto.setId(customerId);
        return bankAccountService.updateCustomer(customerDto);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
         bankAccountService.deleteCustomer(customerId);

}

}


