package com.kogi.galleryapp.domain.enums;

public enum ImageQuality {
    LOW("low_resolution"), THUMBNAIL("thumbnail"), STANDARD("standard_resolution");

    private final String value;

    ImageQuality(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ImageQuality get(String value) {

        for (ImageQuality imageQuality : ImageQuality.values()) {
            if (value.contains(imageQuality.getValue()))
                return imageQuality;
        }

        return null;
    }
}