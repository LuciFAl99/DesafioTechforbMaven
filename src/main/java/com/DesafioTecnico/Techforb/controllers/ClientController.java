package com.DesafioTecnico.Techforb.controllers;

import com.DesafioTecnico.Techforb.DTOs.ClientDTO;
import com.DesafioTecnico.Techforb.enums.DniType;
import com.DesafioTecnico.Techforb.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    @Autowired
    ClientService clientService;
    @GetMapping("/api/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }
    @GetMapping("/api/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return clientService.getCurrentClient(authentication);
    }
    @PostMapping("/api/clients")
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam DniType dniType, @RequestParam String dni, @RequestParam String password){
        return clientService.register(firstName, lastName, dniType, dni, password);
    }


}
