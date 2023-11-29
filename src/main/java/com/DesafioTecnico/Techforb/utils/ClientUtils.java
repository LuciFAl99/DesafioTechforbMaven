package com.DesafioTecnico.Techforb.utils;

public class ClientUtils {
    public static boolean esNumero(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
