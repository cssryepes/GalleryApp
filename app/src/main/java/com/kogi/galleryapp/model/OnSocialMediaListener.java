package com.kogi.galleryapp.model;

import com.kogi.galleryapp.domain.enums.ResponseStatus;

/**
 * Interfaz encargada de comunicar el modelo con la actividad vinculada
 */
public interface OnSocialMediaListener {
    void onDataReceived(ResponseStatus status, Object data);
}