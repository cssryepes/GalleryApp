package com.kogi.galleryapp.domain.enums;

public enum SocialMediaType {
    INSTAGRAM(1), FACEBOOK(2), TWITTER(3);

    private final int value;

    SocialMediaType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SocialMediaType get(int value) {

        for (SocialMediaType socialMediaType : SocialMediaType.values()) {
            if (value == socialMediaType.getValue())
                return socialMediaType;
        }

        return null;
    }
}