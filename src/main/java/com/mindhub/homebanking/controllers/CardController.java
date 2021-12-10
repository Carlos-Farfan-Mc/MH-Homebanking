package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(
            @RequestParam CardType type, @RequestParam CardColor color, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getCards().stream().filter(c -> c.getType() == type).count()>2) {
        // if (client.getCards().size()>2) {
                return new ResponseEntity<>("Client already has 3 cards of type " + type, HttpStatus.FORBIDDEN);
        }
        Card card = new Card(client.getFirstName()+" "+client.getLastName(), type, color, CardUtils.getCardNumber(), CardUtils.getCVV(), LocalDateTime.now(), LocalDateTime.now().plusYears(5), client);
        client.addCard(card);
        cardRepository.save(card);
        return new ResponseEntity<>("card created", HttpStatus.CREATED);
    }
}
