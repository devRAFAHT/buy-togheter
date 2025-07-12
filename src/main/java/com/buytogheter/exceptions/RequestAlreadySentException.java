package com.buytogheter.exceptions;

public class RequestAlreadySentException extends RuntimeException{
    public RequestAlreadySentException(String message) {
        super(message);
    }
}
