package com.DesafioTecnico.Techforb.services;


import com.DesafioTecnico.Techforb.models.Card;

public interface CardService {
    Card findByNumber (String number);
    void saveCard(Card card);
}
