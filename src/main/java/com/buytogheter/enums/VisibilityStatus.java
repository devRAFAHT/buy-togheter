package com.buytogheter.enums;

public enum VisibilityStatus {

    PRIVATE(0),
    PUBLIC(1);

    private int code;

    private VisibilityStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static VisibilityStatus valueOf(int code) {
        for (VisibilityStatus value : VisibilityStatus.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid VisibilityStatus code.");
    }
}