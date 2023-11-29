package com.DesafioTecnico.Techforb.DTOs;


import com.DesafioTecnico.Techforb.models.Menu;

public class MenuDTO {
    private String menu;
    private String cards;
    private String loans;
    private String operations;
    private String offers;
    private String insurance;
    private String points;
    private String help;
    private String logout;
    public MenuDTO(Menu menu){
        this.menu = menu.getMenu();
        this.cards = menu.getCards();
        this.loans = menu.getLoans();
        this.operations = menu.getOperations();
        this.offers = menu.getOffers();
        this.insurance = menu.getInsurance();
        this.points = menu.getPoints();
        this.help = menu.getHelp();
        this.logout = menu.getLogout();
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getLoans() {
        return loans;
    }

    public void setLoans(String loans) {
        this.loans = loans;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }
}
