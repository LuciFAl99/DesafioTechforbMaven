package com.DesafioTecnico.Techforb.services;

import com.DesafioTecnico.Techforb.DTOs.ClientDTO;
import com.DesafioTecnico.Techforb.enums.DniType;
import com.DesafioTecnico.Techforb.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    Client findByName(String name);

    List<ClientDTO> getClients();
    ClientDTO getCurrentClient(Authentication authentication);
    public ResponseEntity<Object> register(String firstName, String lastName, DniType dniType, String dni, String password);

    interface MenuService {

    }
}
