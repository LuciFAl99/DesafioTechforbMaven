package com.DesafioTecnico.Techforb.services.Implement;

import com.DesafioTecnico.Techforb.DTOs.ClientDTO;
import com.DesafioTecnico.Techforb.enums.DniType;
import com.DesafioTecnico.Techforb.models.Card;
import com.DesafioTecnico.Techforb.models.Client;
import com.DesafioTecnico.Techforb.repositories.CardRepository;
import com.DesafioTecnico.Techforb.repositories.ClientRepository;
import com.DesafioTecnico.Techforb.services.ClientService;
import com.DesafioTecnico.Techforb.utils.CardUtils;
import com.DesafioTecnico.Techforb.utils.ClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Client findByName(String name) {
        return clientRepository.findByName(name);
    }


    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByDni(authentication.getName()));
    }

    @Override
    public ResponseEntity<Object> register(String firstName, String lastName, DniType dniType, String dni, String password) {

        StringBuilder errors = new StringBuilder();

        if (firstName.isBlank()) {
            errors.append("Nombre es requerido\n");
        }

        if (lastName.isBlank()) {
            errors.append("Apellido es requerido\n");
        }
        if (dniType == null) {
            errors.append("Email es requerido\n");
        }

        if (dni == null || dni.isBlank()) {
            errors.append("Número de DNI es requerido\n");
        } else {
            if (!ClientUtils.esNumero(dni) || Long.parseLong(dni) <= 1000000) {
                errors.append("El número de DNI debe ser un número válido y mayor a 1,000,000\n");
            }
        }

        if (password.isBlank()) {
            errors.append("La contraseña es requerida\n");
        } else if (password.length() < 8) {
            errors.append("La contraseña debe tener al menos 8 caracteres\n");
        }

        if (errors.length() > 0) {
            return new ResponseEntity<>(errors.toString(), HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByDni(dni) != null) {

            return new ResponseEntity<>("El dni ya existe", HttpStatus.FORBIDDEN);

        }

        LocalDateTime fromDate = LocalDateTime.now();
        LocalDateTime thruDate = fromDate.plusYears(5);
        String cardNumber = CardUtils.getCardNumber();
        Client newClient = new Client(firstName, lastName, dniType, dni, passwordEncoder.encode(password));
        Card newCard = new Card(cardNumber, 0, 0, 0, fromDate, thruDate);
        clientRepository.save(newClient);
        newClient.addCard(newCard);
        cardRepository.save(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
