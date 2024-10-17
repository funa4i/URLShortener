package org.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.urlshortener.Enums.Roles;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SpringBootApplication
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
