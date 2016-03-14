package com.kogi.galleryapp.domain.models;

import com.kogi.galleryapp.domain.enums.ResponseStatus;

public interface OnSocialMediaListener {
    void onDataReceived(ResponseStatus status, Object data);
}