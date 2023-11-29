package com.DesafioTecnico.Techforb.services.Implement;

import com.DesafioTecnico.Techforb.DTOs.LoanApplicationDTO;
import com.DesafioTecnico.Techforb.DTOs.LoanDTO;
import com.DesafioTecnico.Techforb.enums.TransactionState;
import com.DesafioTecnico.Techforb.enums.TransactionType;
import com.DesafioTecnico.Techforb.models.*;
import com.DesafioTecnico.Techforb.repositories.*;
import com.DesafioTecnico.Techforb.services.LoanService;
import com.DesafioTecnico.Techforb.utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImImplement implements LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> loans(Authentication authentication, LoanApplicationDTO loanApplicationDto) {
        Loan loan = this.loanRepository.findById(loanApplicationDto.getLoanId());
        Card card = this.cardRepository.findByNumber(loanApplicationDto.getDestinationCardNumber());
        Client client = this.clientRepository.findByDni(authentication.getName());

        StringBuilder errorMessage = new StringBuilder();
        if (loanApplicationDto.getDestinationCardNumber().isBlank()) {
            errorMessage.append("El número de cuenta destino es requerido\n");
        }
        if (loanApplicationDto.getAmount() <= 0) {
            errorMessage.append("El monto es requerido y debe ser un número válido\n");
        }
        if (loanApplicationDto.getPayments() <= 0) {
            errorMessage.append("Cuotas es requerido y debe ser un número válido\n");
        }
        if (loanApplicationDto.getLoanId() == 0) {
            errorMessage.append("El tipo de préstamo es requerido\n");
        }

        if (errorMessage.length() > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage.toString());
        }

        if (loan == null) {
            return new ResponseEntity<>("No existe el Préstamo", HttpStatus.FORBIDDEN);
        }

        if (card == null) {
            return new ResponseEntity<>("La cuenta destino no existe", HttpStatus.FORBIDDEN);
        }

        if (client == null) {
            return new ResponseEntity<>("El cliente autenticado no existe", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDto.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("El monto solicitado supera el permitido", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDto.getPayments())){
            return new ResponseEntity<>("Cantidad de cuotas incorrectas", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDto.getDestinationCardNumber() == null){
            return new ResponseEntity<>("La cuenta destino no existe", HttpStatus.FORBIDDEN);
        }
        ClientLoan existingLoan = this.clientLoanRepository.findByLoanAndClient(loan, client);
        if (existingLoan != null) {
            if (existingLoan.getPayments() == 0) {
                ClientLoan newLoan = new ClientLoan(loanApplicationDto.getAmount(), loanApplicationDto.getAmount() * loan.getInterest(), loanApplicationDto.getPayments(), loanApplicationDto.getPayments());
                newLoan.setClient(client);
                newLoan.setLoan(loan);
                newLoan.setDate(LocalDateTime.now());
                clientLoanRepository.save(newLoan);
                return new ResponseEntity<>("Nuevo préstamo del mismo tipo solicitado", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("El cliente ya tiene un préstamo del mismo tipo y no ha completado los pagos", HttpStatus.FORBIDDEN);
            }
        }

        if(!card.getClient().equals(client)){
            return new ResponseEntity<>("La cuenta destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDto.getAmount(),loanApplicationDto.getAmount()*loan.getInterest(),loanApplicationDto.getPayments(), loanApplicationDto.getPayments());
        clientLoan.setClient(client);
        clientLoan.setLoan(loan);
        clientLoan.setDate(LocalDateTime.now());
        clientLoanRepository.save(clientLoan);

        Double initialBalanceclientAcc = card.getBalance() + loanApplicationDto.getAmount();
        Transaction creditTransaction = new Transaction(TransactionType.CREDITO, TransactionState.CONFIRMED, loanApplicationDto.getAmount(),"Crédito aprobado", LocalDateTime.now(),initialBalanceclientAcc);
        transactionRepository.save(creditTransaction);


        if (creditTransaction.getTransactionType() == TransactionType.CREDITO) {
            card.setRevenues(card.getRevenues() + loanApplicationDto.getAmount());
        }

        card.addTransaction(creditTransaction);
        card.setBalance(card.getBalance()+creditTransaction.getAmount());
        cardRepository.save(card);

        return  new ResponseEntity<>("Create", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> payLoan(Authentication authentication, long idLoan, String card, double amount) {
        Client client = clientRepository.findByDni(authentication.getName());
        Optional<ClientLoan> clientLoan = clientLoanRepository.findById(idLoan);
        Card cardAuthenticated = cardRepository.findByNumber(card);
        String description = "Pago de préstamo " + clientLoan.get().getLoan().getName();

        if( clientLoan == null ){
            return new ResponseEntity<>("Este préstamo no existe", HttpStatus.FORBIDDEN);
        } else if( client == null){
            return new ResponseEntity<>("No estás registrado", HttpStatus.FORBIDDEN);}
        else if( clientLoan.get().getPayments() == 0 ){
            return new ResponseEntity<>("Este préstamo ya fue pagado", HttpStatus.FORBIDDEN);
        }
        if (card.isBlank()) {
            return new ResponseEntity<>("Por favor ingresa una cuenta. ", HttpStatus.FORBIDDEN);
        } else {
            if (cardAuthenticated == null) {
                return new ResponseEntity<>("Esta cuenta no existe. ", HttpStatus.FORBIDDEN);
            } else if (!client.getCards().contains(cardAuthenticated)) {
                return new ResponseEntity<>("Esta cuenta no te pertenece. ", HttpStatus.FORBIDDEN);
            }
        }
        if (!clientLoan.get().getClient().equals(client)) {
            return new ResponseEntity<>("No solicitaste este préstamo", HttpStatus.FORBIDDEN);
        }

        int payments = LoanUtils.getPayments(clientLoan);
        int roundedAmount = LoanUtils.getRoundedAmount(amount);
        if (roundedAmount != payments) {
            return new ResponseEntity<>("Monto incorrecto de la cuota, debes pagar "+ payments, HttpStatus.FORBIDDEN);
        }

        if ( amount < 1 ){
            return new ResponseEntity<>("Ingresa un valor mayor a 0", HttpStatus.FORBIDDEN);
        }  else if ( cardAuthenticated.getBalance() < amount ){
            return new ResponseEntity<>("Saldo insuficiente en tu cuenta " + cardAuthenticated.getNumber(), HttpStatus.FORBIDDEN);}

        Double initialBalanceaccountAuth = cardAuthenticated.getBalance() - amount;
        cardAuthenticated.setBalance( cardAuthenticated.getBalance() - amount );
        clientLoan.get().setFinalAmount( clientLoan.get().getFinalAmount() - amount);

        Transaction debitLoan = new Transaction(TransactionType.DEBITO, TransactionState.CONFIRMED, amount, description , LocalDateTime.now(), initialBalanceaccountAuth);
        transactionRepository.save(debitLoan);
        cardAuthenticated.addTransaction(debitLoan);

        if (debitLoan.getTransactionType() == TransactionType.DEBITO) {
            cardAuthenticated.setExpenditures(cardAuthenticated.getExpenditures() + amount);
        }

        cardRepository.save(cardAuthenticated);

        if ( amount < clientLoan.get().getFinalAmount()){
            clientLoan.get().setPayments(clientLoan.get().getPayments() - 1 );
        } else {
            clientLoan.get().setPayments(0);
        }
        return new ResponseEntity<>("Pago efectuado correctamente", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> newLoanAdmin(Loan loan) {
        Loan newLoan = new Loan(loan.getName(), loan.getMaxAmount() , loan.getInterest(), loan.getPayments());
        loanRepository.save(newLoan);
        return new ResponseEntity<>("Préstamo creado con éxito", HttpStatus.CREATED);
    }


}
