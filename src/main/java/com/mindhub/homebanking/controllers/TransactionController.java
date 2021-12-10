package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transactionsDebit (
            @RequestParam double amount, String description, String sourceNumber, String targetNumber, Authentication authentication) {
        if (amount<=0 || description.isEmpty() || sourceNumber.isEmpty() || targetNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (sourceNumber.equals(targetNumber))
            return new ResponseEntity<>("Source and target account number should not match", HttpStatus.FORBIDDEN);

        Account sourceAccount = accountRepository.findByNumber(sourceNumber);
        if (sourceAccount == null)
            return new ResponseEntity<>("Source account does not exist", HttpStatus.FORBIDDEN);
        if (!sourceAccount.getClient().getEmail().equals(authentication.getName()))
            return new ResponseEntity<>("Source account number does not belong to the authenticated user", HttpStatus.FORBIDDEN);
        if (sourceAccount.getBalance() < amount)
            return new ResponseEntity<>("Account balance is not enough", HttpStatus.FORBIDDEN);

        Account targetAccount = accountRepository.findByNumber(targetNumber);
        if (targetAccount == null)
            return new ResponseEntity<>("Target account does not exist", HttpStatus.FORBIDDEN);

        Transaction transaction = new Transaction(TransactionType.DEBITO, -amount, description + '-' + targetAccount.getNumber(), LocalDateTime.now());
        sourceAccount.addTransaction(transaction);
        transactionRepository.save(transaction);

        Transaction creditTransaction = new Transaction(TransactionType.CREDITO, amount, description + '-' + sourceAccount.getNumber(), LocalDateTime.now());
        targetAccount.addTransaction(creditTransaction);
        transactionRepository.save(creditTransaction);

        sourceAccount.setBalance(sourceAccount.getBalance()-amount);
        accountRepository.save(sourceAccount);

        targetAccount.setBalance(targetAccount.getBalance()+amount);
        accountRepository.save(targetAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
