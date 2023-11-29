package com.DesafioTecnico.Techforb.DTOs;

import com.DesafioTecnico.Techforb.enums.DniType;
import com.DesafioTecnico.Techforb.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private DniType dniType;
    private String dni;
    private List<ClientLoanDTO> loans;
    private List<CardDTO> cards;
    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getName();
        this.lastName = client.getLastname();
        this.dniType = client.getDniType();
        this.dni = client.getDni();
        this.loans = client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toList());
        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DniType getDniType() {
        return dniType;
    }

    public void setDniType(DniType dniType) {
        this.dniType = dniType;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }
}
