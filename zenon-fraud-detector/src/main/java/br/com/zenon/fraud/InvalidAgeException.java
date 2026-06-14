package br.com.zenon.fraud;

public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
