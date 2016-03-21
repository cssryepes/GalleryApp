package com.kogi.galleryapp.ui.listeners;

import com.kogi.galleryapp.domain.enums.ImageQuality;

/**
 * Interfaz encargada de la comunicacion entre los fragment y las actividades
 */
public interface OnFragmentInteractionListener {
    void onSwipeItem(int position);
    void onItemSelected(int position, ImageQuality quality);
    void onItemLongSelected(int position, ImageQuality quality);
}