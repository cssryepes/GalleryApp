package com.kogi.galleryapp.model;

import com.kogi.galleryapp.domain.enums.ResponseStatus;

public interface OnSocialMediaListener {
    void onDataReceived(ResponseStatus status, Object data);
}