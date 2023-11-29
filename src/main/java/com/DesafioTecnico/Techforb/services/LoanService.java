package com.DesafioTecnico.Techforb.services;

import com.DesafioTecnico.Techforb.DTOs.LoanApplicationDTO;
import com.DesafioTecnico.Techforb.DTOs.LoanDTO;
import com.DesafioTecnico.Techforb.models.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    ResponseEntity<Object> loans(Authentication authentication, LoanApplicationDTO loanApplicationDto);
    public ResponseEntity<Object> payLoan(Authentication authentication, long idLoan, String card, double amount);
    ResponseEntity<Object> newLoanAdmin(@RequestBody Loan loan);
}
