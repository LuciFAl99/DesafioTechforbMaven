package com.DesafioTecnico.Techforb.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionService {
    ResponseEntity<Object>  transaction(Authentication authentication, double amount, String description, String accountOriginNumber, String destinationAccountNumber) ;
}
