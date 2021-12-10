package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public  AccountDTO getAccounts(@PathVariable Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }
    ///////////////////////
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> create(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size()>2) {
            return new ResponseEntity<>("Client already has 3 accounts", HttpStatus.FORBIDDEN);
        }
        Account account = new Account("VIN-" + CardUtils.getRandomNumber(1,99999999), LocalDateTime.now(), 0.00);
        client.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>("account created", HttpStatus.CREATED);
    }

}
