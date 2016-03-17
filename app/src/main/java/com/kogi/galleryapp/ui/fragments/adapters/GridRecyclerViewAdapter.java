package com.kogi.galleryapp.ui.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.ui.helpers.DownloadImage;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.helpers.ViewHolder;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Feed> mFeed;
    private final OnFragmentInteractionListener mInteractionListener;
    public int selectedPosition = 0;

    public GridRecyclerViewAdapter(List<Feed> items,
                                   OnFragmentInteractionListener interactionListener) {
        mFeed = items;
        mInteractionListener = interactionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feed_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mView.setSelected(selectedPosition == position);
        holder.mFeed = mFeed.get(position);
        holder.mContentView.setText(mFeed.get(position).getCaption());
        holder.mImageView.setImageResource(R.drawable.ic_photo_library_white_48dp);
        holder.mQuality = ImageQuality.THUMBNAIL;
        List<Image> images = holder.mFeed.getImages();
        for (Image image : images) {
            if (image.getQuality().equals(ImageQuality.THUMBNAIL)) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(image.getWidth(), image.getHeight());
                holder.mImageView.setLayoutParams(layoutParams);
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInteractionListener != null) {
                    mInteractionListener.onItemSelected(position, ImageQuality.THUMBNAIL);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mInteractionListener != null) {
                    mInteractionListener.onItemLongSelected(position, ImageQuality.THUMBNAIL);
                }
                return false;
            }
        });

        new DownloadImage().execute(holder);
    }

    @Override
    public int getItemCount() {
        return mFeed.size();
    }


}
