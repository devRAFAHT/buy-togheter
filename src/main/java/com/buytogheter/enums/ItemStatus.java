package com.buytogheter.enums;

public enum ItemStatus {

    AVAILABLE(0),
    RESERVED(1),
    PURCHASED(2),
    CANCELED(3),
    NOT_FOUND(4);

    private int code;

    private ItemStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ItemStatus valueOf(int code) {
        for (ItemStatus value : ItemStatus.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid ItemStatus code.");
    }
}