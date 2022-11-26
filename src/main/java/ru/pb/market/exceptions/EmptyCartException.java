package ru.pb.market.exceptions;

public class EmptyCartException extends RuntimeException{

    public EmptyCartException(String message) {
        super(message);
    }
}
