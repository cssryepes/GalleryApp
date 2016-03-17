package com.kogi.galleryapp.ui.listeners;

import com.kogi.galleryapp.domain.enums.ImageQuality;

public interface OnFragmentInteractionListener {
    void onItemSelected(int position, ImageQuality quality);
    void onSwipeItem(int position);
//    void OnItemLongSelected(int position, ImageQuality quality);
}