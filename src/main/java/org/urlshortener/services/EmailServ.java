package org.urlshortener.services;

import jakarta.validation.constraints.Email;

public interface EmailServ{

    void sendMessage(@Email String address, String subj, String message);

    void expiredUrl(@Email String address, String shUrl, String lnUrl);

}
