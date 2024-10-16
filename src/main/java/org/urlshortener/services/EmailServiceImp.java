package org.urlshortener.services;


import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailServiceImp implements EmailServ{

    private final JavaMailSender emailSender;

    @Override
    public void sendMessage(String address, String subj, String message) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(address);
        smm.setSubject(subj);
        smm.setText(message);
        emailSender.send(smm);
    }

    @Override
    public void expiredUrl(String address, String shUrl, String lnUrl) {
        Runnable send = () -> {
            sendMessage(address, "The link is expired",
                    "This link is no longer available: \n" + shUrl +
                            "\t ----> \t" + lnUrl);
        };
        var th = new Thread(send);
        th.start();
    }
}
