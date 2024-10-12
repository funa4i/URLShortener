package org.urlshortener.Manager;

public interface HashManager {

    String getHashFrom(String str, String salt);
}
