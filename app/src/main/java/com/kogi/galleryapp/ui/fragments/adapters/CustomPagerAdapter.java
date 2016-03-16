package com.kogi.galleryapp.ui.fragments.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.FeedDetail;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.fragments.adapters.helpers.DownloadImage;
import com.kogi.galleryapp.ui.fragments.adapters.helpers.ViewHolder;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    private final List<Feed> mFeed;
    private final LayoutInflater mLayoutInflater;
    private final OnFragmentInteractionListener mInteractionListener;
 
    public CustomPagerAdapter(OnFragmentInteractionListener interactionListener, List<Feed> feed) {
        mInteractionListener = interactionListener;
        mLayoutInflater = (LayoutInflater) GalleryApp.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFeed = feed;
    }
 
    @Override
    public int getCount() {
        return mFeed.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.feed_pager_item, container, false);
 
        ViewHolder holder = new ViewHolder(itemView);
        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        holder.mFeed = mFeed.get(position);
        holder.mQuality = ImageQuality.LOW;
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInteractionListener != null) {
                    mInteractionListener.onItemSelected(position, FeedDetail.LOW);
                }
            }
        });

        new DownloadImage().execute(holder);

        container.addView(itemView);
 
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}