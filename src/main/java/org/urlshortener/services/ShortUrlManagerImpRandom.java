package org.urlshortener.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ShortUrlManagerImpRandom implements ShortUrlManager {
    @Override
    public String getNextValue() {
        var r = new Random();
        StringBuilder newUrl = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            newUrl.append(String.valueOf(alphabet.charAt(r.nextInt(alphabet.length()))));
        }
        return newUrl.toString();
    }
}
