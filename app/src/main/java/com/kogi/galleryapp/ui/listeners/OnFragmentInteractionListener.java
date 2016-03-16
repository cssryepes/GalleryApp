package com.kogi.galleryapp.ui.listeners;

import com.kogi.galleryapp.domain.enums.FeedDetail;

public interface OnFragmentInteractionListener {
    void onItemSelected(int position, FeedDetail detail);
    void onSwipeItem(int position);
}