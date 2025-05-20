package com.buytogheter.enums;

public enum RequestStatus {

    PENDING(0),
    ACCEPTED(1),
    REJECTED(2);

    private int code;

    private RequestStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RequestStatus valueOf(int code) {
        for (RequestStatus value : RequestStatus.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid RequestStatus code.");
    }
}
