package com.rideflowauthservice.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String email) {
        super("A passenger with email " + email + " already exists");
    }
}
