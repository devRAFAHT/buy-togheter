package com.buytogheter.exceptions;

public class RequestAlreadyAcceptedException extends RuntimeException {
    public RequestAlreadyAcceptedException(String message) {
        super(message);
    }
}
