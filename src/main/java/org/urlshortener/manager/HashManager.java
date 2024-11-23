package org.urlshortener.manager;

public interface HashManager {

    String getHashFrom(String str, String salt);
}
