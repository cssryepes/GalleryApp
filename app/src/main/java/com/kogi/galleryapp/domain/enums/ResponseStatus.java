package com.kogi.galleryapp.domain.enums;

public enum ResponseStatus {
    OK(1), ERROR(2), NO_CONNECTION(3);

    private final int value;

    private ResponseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}