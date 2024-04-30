package org.example.service;

public class IncorrectAnimalCostException extends RuntimeException{
    public IncorrectAnimalCostException(String message) {
        super(message);
    }
}
