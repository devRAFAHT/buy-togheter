package com.buytogheter.enums;

public enum AccessRequestType {

    JOIN_REQUEST(0),
    INVITATION(1);

    private int code;

    private AccessRequestType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AccessRequestType valueOf(int code) {
        for (AccessRequestType value : AccessRequestType.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid AccessRequestType code.");
    }
}
