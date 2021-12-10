package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> create(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        if (loanApplicationDTO.getId() < 0 || loanApplicationDTO.getAmount() < 0 || loanApplicationDTO.getPayment() < 0
                || loanApplicationDTO.getAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        Loan typeLoan;
        try {
            typeLoan = loanRepository.getById(loanApplicationDTO.getId());
        }
        catch (EntityNotFoundException ex)
        {
            return new ResponseEntity<>("loan not found", HttpStatus.FORBIDDEN);

        }
        if (typeLoan.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>(String.format ("Amount (%s) should not exceed loan max amount (%s)", loanApplicationDTO.getAmount(), typeLoan.getMaxAmount()), HttpStatus.FORBIDDEN);
        }
        if (!typeLoan.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("Payment not valid", HttpStatus.FORBIDDEN);
        }

        Account targetAccount = accountRepository.findByNumber(loanApplicationDTO.getAccountNumber());
        if (targetAccount == null)
            return new ResponseEntity<>("Target account does not exist", HttpStatus.FORBIDDEN);
        if (!targetAccount.getClient().getEmail().equals(authentication.getName())){
            return new ResponseEntity<>("Account number does not belong to the authenticated user", HttpStatus.FORBIDDEN);
        }
        Client client = clientRepository.findByEmail(authentication.getName());
//        Crear solicitud de prestamo
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * (1+typeLoan.getPercentage()/100), loanApplicationDTO.getPayment(), client, typeLoan);
        typeLoan.addClientLoan(clientLoan);
        client.addClientLoan(clientLoan);
        clientLoanRepository.save(clientLoan);

        Transaction transaction = new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmount(), typeLoan.getName() + "-préstamo aprobado", LocalDateTime.now());
        targetAccount.addTransaction(transaction);
        transactionRepository.save(transaction);

        targetAccount.setBalance(targetAccount.getBalance()+ loanApplicationDTO.getAmount());

        return new ResponseEntity<>("loan created", HttpStatus.CREATED);
    }
}
