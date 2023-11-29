package com.DesafioTecnico.Techforb.services.Implement;

import com.DesafioTecnico.Techforb.models.Card;
import com.DesafioTecnico.Techforb.repositories.CardRepository;
import com.DesafioTecnico.Techforb.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }


}
