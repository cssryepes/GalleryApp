package com.kogi.galleryapp.ui.fragments;

import com.kogi.galleryapp.domain.entities.Feed;

public interface OnGridFragmentInteractionListener {
    void onRefreshGrid();
    void onItemSelected(Feed item);
}