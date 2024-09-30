package org.urlshortener.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ShortUrlManager {

    StringBuilder alphabet = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

    String getNextValue();
}
