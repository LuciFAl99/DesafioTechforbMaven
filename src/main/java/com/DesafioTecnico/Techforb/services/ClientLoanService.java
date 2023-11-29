package com.DesafioTecnico.Techforb.services;

import com.DesafioTecnico.Techforb.DTOs.ClientLoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientLoanService {
    public ResponseEntity<List<ClientLoanDTO>> getClientLoan(Authentication authentication);
}
