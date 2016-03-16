package com.kogi.galleryapp.domain.enums;

public enum FeedDetail {
    LOW(0), THUMBNAIL(1), STANDARD(2), FULL(3);

    private final int value;

    FeedDetail(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FeedDetail get(int value) {

        for (FeedDetail feedDetail : FeedDetail.values()) {
            if (value == feedDetail.getValue())
                return feedDetail;
        }

        return null;
    }
}