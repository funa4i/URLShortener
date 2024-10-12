package org.urlshortener.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class ShortUrlManagerImpRandom implements ShortUrlManager {
    @Override
    public String getNextValue() {
        var sr = new SecureRandom();
        StringBuilder newUrl = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            newUrl.append(alphabet.charAt(sr.nextInt(alphabet.length() + 1) - 1));
        }
        return newUrl.toString();
    }
}
