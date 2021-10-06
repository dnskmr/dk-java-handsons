package com.dk.springoktalogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @author Dinesh
 * @version 1.0
 * @since 10/06/2021
 * <p>
 * This application is used to demonstrate the Okta Login
 * </p>
 */
@SpringBootApplication
@EnableOAuth2Sso
public class SpringOktaLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOktaLoginApplication.class, args);
    }
}
