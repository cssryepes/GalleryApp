package com.kogi.galleryapp.ui.fragments.adapters.helpers;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final ImageView mImageView;
    public final TextView mContentView;
    public Bitmap mBitmap;
    public Feed mFeed;
    public ImageQuality mQuality;

    public ViewHolder(View view) {
        super(view);
        mView = view;
        mImageView = (ImageView) view.findViewById(R.id.image_view);
        mContentView = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mFeed.getCaption() + "'";
    }
}