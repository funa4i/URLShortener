package org.urlshortener.Manager;

public interface ShortUrlManager {

    StringBuilder alphabet = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-");

    String getNextValue();
}
