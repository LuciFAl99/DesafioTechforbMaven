package com.DesafioTecnico.Techforb.controllers;

import com.DesafioTecnico.Techforb.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Transactional
@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @PostMapping("/api/clients/current/transactions")
    public ResponseEntity<Object> transaction(Authentication authentication, @RequestParam double amount, @RequestParam String description, @RequestParam String cardOriginNumber, @RequestParam String destinationCardNumber) {
        return transactionService.transaction(authentication, amount, description, cardOriginNumber, destinationCardNumber);
    }
}
