package com.DesafioTecnico.Techforb.services.Implement;

import com.DesafioTecnico.Techforb.enums.TransactionState;
import com.DesafioTecnico.Techforb.enums.TransactionType;
import com.DesafioTecnico.Techforb.models.Card;
import com.DesafioTecnico.Techforb.models.Client;
import com.DesafioTecnico.Techforb.models.Transaction;
import com.DesafioTecnico.Techforb.repositories.TransactionRepository;
import com.DesafioTecnico.Techforb.services.CardService;
import com.DesafioTecnico.Techforb.services.ClientService;
import com.DesafioTecnico.Techforb.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public ResponseEntity<Object> transaction(Authentication authentication, double amount, String description, String originCardNumber, String destinationCardNumber) {
        Card originCard = cardService.findByNumber(originCardNumber.toUpperCase());
        Card destinationCard = cardService.findByNumber(destinationCardNumber.toUpperCase());

        StringBuilder errorMessage = new StringBuilder();
        if (description.isBlank()) {
            errorMessage.append("Description es requerido\n");
        }
        if (originCardNumber.isBlank()) {
            errorMessage.append("El número de cuenta es requerido\n");
        }
        if (destinationCardNumber.isBlank()) {
            errorMessage.append("La cuenta de destino es requerida\n");
        }
        if (amount == 0.0 || Double.isNaN(amount)) {
            errorMessage.append("Monto es requerido y debe ser un número válido\n");
        }

        if (errorMessage.length() > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage.toString());
        }

        if (amount < 1) {
            return new ResponseEntity<>("Monto inválido", HttpStatus.FORBIDDEN);
        }

        if (originCardNumber.equals(destinationCardNumber)) {
            return new ResponseEntity<>("Los números de cuenta son iguales", HttpStatus.FORBIDDEN);
        }

        if (originCard == null) {
            return new ResponseEntity<>("La cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        if (authentication == null || !originCard.getClient().getDni().equals(authentication.getName())) {
            return new ResponseEntity<>("La cuenta de origen no te pertenece", HttpStatus.FORBIDDEN);
        }

        if (destinationCard == null) {
            return new ResponseEntity<>("La cuenta destino no existe", HttpStatus.FORBIDDEN);
        }

        if (originCard.getBalance() < amount) {
            return new ResponseEntity<>("No posees fondos suficientes para realizar esta transacción", HttpStatus.FORBIDDEN);
        }
        Client originCardOwner = originCard.getClient();
        String originCardOwnerName = originCardOwner != null ? originCardOwner.getName() + " " + originCardOwner.getLastname() : "Propietario no encontrado";
        Client destinationCardOwner = destinationCard.getClient();
        String destinationCardOwnerName = destinationCardOwner != null ? destinationCardOwner.getName() + " " + destinationCardOwner.getLastname() : "Propietario no encontrado";


        Double initialOriginBalanceAccount = originCard.getBalance() - amount;
        Transaction debitTransaction = new Transaction(TransactionType.DEBITO, TransactionState.CONFIRMED, amount, destinationCardOwnerName, LocalDateTime.now(), initialOriginBalanceAccount);
        Double initialDestinBalanceAccount = destinationCard.getBalance() + amount;
        Transaction creditTransaction = new Transaction(TransactionType.CREDITO, TransactionState.CONFIRMED, amount, originCardOwnerName, LocalDateTime.now(), initialDestinBalanceAccount);

        originCard.addTransaction(debitTransaction);
        destinationCard.addTransaction(creditTransaction);

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        originCard.setBalance(originCard.getBalance() - amount);
        destinationCard.setBalance(destinationCard.getBalance() + amount);

        if (debitTransaction.getTransactionType() == TransactionType.DEBITO) {
            originCard.setExpenditures(originCard.getExpenditures() + amount);
        } else if (creditTransaction.getTransactionType() == TransactionType.CREDITO) {
            originCard.setRevenues(originCard.getRevenues() + amount);
        }

        if (creditTransaction.getTransactionType() == TransactionType.CREDITO) {
            destinationCard.setRevenues(destinationCard.getRevenues() + amount);
        } else if (debitTransaction.getTransactionType() == TransactionType.DEBITO) {
            destinationCard.setExpenditures(destinationCard.getExpenditures() + amount);
        }

        cardService.saveCard(originCard);
        cardService.saveCard(destinationCard);


        cardService.saveCard(originCard);
        cardService.saveCard(destinationCard);

        return new ResponseEntity<>("Transacción exitosa", HttpStatus.CREATED);
    }
}
