package ru.war.robots.giftshop.config;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }
}
