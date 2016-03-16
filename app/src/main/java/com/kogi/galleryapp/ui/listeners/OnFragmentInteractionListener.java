package com.kogi.galleryapp.ui.listeners;

import com.kogi.galleryapp.domain.entities.Feed;

public interface OnFragmentInteractionListener {
    void onItemSelected(Feed item);
    void onSwipeItem(int position);
}