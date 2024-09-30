package org.urlshortener.services;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ShortUrlManagerImpSequence implements ShortUrlManager{
    public ShortUrlManagerImpSequence(StringBuilder stringBuilder){
        currentUrl = stringBuilder;
    }

    private final StringBuilder currentUrl;

    @Override
    public String getNextValue() {
        var newUrl = currentUrl;
        boolean isPlusOne = true;
        for (int i = currentUrl.length() - 1; i >= 0; i--) {
            if (isPlusOne) {
                var newChar = (alphabet.indexOf(String.valueOf(currentUrl.charAt(i))) + 1) % 62;
                isPlusOne = newChar == 0;
                currentUrl.setCharAt(i, alphabet.charAt(newChar));
            }
        }
        if (isPlusOne) {
            currentUrl.setCharAt(currentUrl.length() - 1, alphabet.charAt(1));
        }
        return currentUrl.toString();
    }
}
