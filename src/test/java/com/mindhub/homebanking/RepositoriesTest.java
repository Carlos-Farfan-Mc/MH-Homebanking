package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void existNumberAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,hasItem(hasProperty("number", is("VIN001"))));
    }

    @Test
    public void existCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    @Test
    public void existTypeCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("type", is(CardType.DEBIT))));
    }

    @Test
    public void existClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
    @Test
    public void existEmailClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,hasItem(hasProperty("email", is("melba@mindhub.com"))));
    }

    @Test
    public void existRepository(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
    @Test
    public void existTypeRepository(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,hasItem(hasProperty("type", is(TransactionType.CREDITO))));
    }
}
