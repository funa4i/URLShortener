package org.urlshortener.services;

import java.util.Random;

public class ShortUrlManagerImpRandom implements ShortUrlManager {
    @Override
    public String getNextValue() {
        var r = new Random();
        var newurl = "";
        for (int i = 0; i < 7; i++) {
            newurl += String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return newurl;
    }
}
