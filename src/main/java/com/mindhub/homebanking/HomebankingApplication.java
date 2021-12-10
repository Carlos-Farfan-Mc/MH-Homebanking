package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.*;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client clientMelba = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000d);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500.5d);

			Transaction transaction1 = new Transaction(TransactionType.DEBITO, 3000, "transferencia", LocalDateTime.now().plusDays(1));
			Transaction transaction2 = new Transaction(TransactionType.CREDITO, 9000, "pago", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.DEBITO, 333, "transferencia", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.CREDITO, 999, "pago", LocalDateTime.now());

			Loan hipotecario = new Loan("Hipotecario", 500000, 20, List.of(12, 24, 36, 48, 60));
			Loan personal = new Loan("Personal", 100000, 30, List.of(6, 12, 24));
			Loan automotriz = new Loan("Automotriz", 300000, 40, List.of(6, 12, 24, 36));
			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);

			ClientLoan clientLoan1 = new ClientLoan(400000, hipotecario.getPayments().get(4), clientMelba, hipotecario);
			clientRepository.save(clientMelba);
			hipotecario.addClientLoan(clientLoan1);
			clientMelba.addClientLoan(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(50000, personal.getPayments().get(1), clientMelba, personal);
			clientRepository.save(clientMelba);
			personal.addClientLoan(clientLoan2);
			clientMelba.addClientLoan(clientLoan2);

			Card cardGold = new Card(clientMelba.getFirstName()+" "+clientMelba.getLastName(), CardType.DEBIT, CardColor.GOLD, "5566455489982332", 777, LocalDateTime.now(), LocalDateTime.now().plusYears(5), clientMelba);
			cardRepository.save(cardGold);
			Card cardTitanium = new Card(clientMelba.getFirstName()+" "+clientMelba.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "4554622635537887", 333, LocalDateTime.now(), LocalDateTime.now().plusYears(5), clientMelba);
			cardRepository.save(cardTitanium);
//			Card cardSilver = new Card(clientMelba.getFirstName()+" "+clientMelba.getLastName(), CardType.CREDIT, CardColor.SILVER, "4554622635537887", 333, LocalDateTime.now(), LocalDateTime.now().plusYears(5), clientMelba);
//			cardRepository.save(cardSilver);

			clientMelba.addAccount(account1);
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);

			accountRepository.save(account1);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			clientMelba.addAccount(account2);
			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			accountRepository.save(account2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			Client clientCarlos = new Client("Carlos", "Farfan", "charly@mindhub.com", passwordEncoder.encode("bono123"));
			Account account3 = new Account("VIN003", LocalDateTime.now(), 6500.3d);
			Account account4 = new Account("MIV004", LocalDateTime.now().plusDays(1), 9500d);

			clientCarlos.addAccount(account3);
			clientCarlos.addAccount(account4);

			clientRepository.save(clientCarlos);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Card cardSilver2 = new Card("Carlos Farfan", CardType.CREDIT, CardColor.SILVER, "6226622635533443", 999, LocalDateTime.now(), LocalDateTime.now().plusYears(5), clientCarlos);
			cardRepository.save(cardSilver2);

//			clientRepository.save(new Client("Melba", "Morel", "melba@mindhub.com"));
//
//			accountRepository.save(new Account("MIV001", LocalDateTime.now(), 5000d));
//			accountRepository.save(new Account("MIV002", LocalDateTime.now().plusDays(1), 7500d));

//			clientRepository.save(new Client("Carlos", "Farfan", "charly@mindhub.com"));
		};
	}
}
