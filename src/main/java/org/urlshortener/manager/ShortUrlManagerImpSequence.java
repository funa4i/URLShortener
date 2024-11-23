package org.urlshortener.manager;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ShortUrlManagerImpSequence implements ShortUrlManager{
    public ShortUrlManagerImpSequence(StringBuilder stringBuilder){
        currentUrl = stringBuilder;
    }

    private StringBuilder currentUrl;

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
