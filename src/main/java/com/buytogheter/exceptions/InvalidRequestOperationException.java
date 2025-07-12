package com.buytogheter.exceptions;

public class InvalidRequestOperationException extends RuntimeException{
    public InvalidRequestOperationException(String message) {
        super(message);
    }
}
