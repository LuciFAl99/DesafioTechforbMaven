package com.DesafioTecnico.Techforb.utils;

public class CardUtils {
    public static String getCardNumber() {
        String cardNumber = "";
        for (int i = 0; i < 16; i++) {
            if (i > 0 && i % 4 == 0) {
                cardNumber += " ";
            }
            cardNumber += (int) (Math.random() * 10);
        }
        return cardNumber;
    }
}
