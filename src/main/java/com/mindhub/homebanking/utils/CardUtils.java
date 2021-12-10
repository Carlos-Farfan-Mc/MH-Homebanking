package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.models.Client;

public final class CardUtils {
    private CardUtils() {
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static String getCardNumber(){
        int grupo1 = getRandomNumber(1,9999);
        int grupo2 = getRandomNumber(1,9999);
        int grupo3 = getRandomNumber(1,9999);
        int grupo4 = getRandomNumber(1,9999);
        return String.format("%04d-%04d-%04d-%04d", grupo1, grupo2, grupo3, grupo4);
    }
    public static int getCVV() {
        return getRandomNumber(1,999);
    }
}
