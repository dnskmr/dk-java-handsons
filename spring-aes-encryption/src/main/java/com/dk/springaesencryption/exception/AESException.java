package com.dk.springaesencryption.exception;

/**
 * @author DK
 * @version 1.0
 * @since 10/08/2021
 */
public class AESException extends RuntimeException {

    /**
     *
     * @param message
     */
    public AESException(String message){
        super(message);
    }
}
