package com.kogi.galleryapp.ui.fragments.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.helpers.DownloadImage;
import com.kogi.galleryapp.ui.helpers.ViewHolder;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    private final List<Feed> mFeed;
    private final LayoutInflater mLayoutInflater;
    private final OnFragmentInteractionListener mInteractionListener;
    private final ImageQuality mQuality;

    public CustomPagerAdapter(OnFragmentInteractionListener interactionListener, List<Feed> feed, ImageQuality quality) {
        mInteractionListener = interactionListener;
        mLayoutInflater = (LayoutInflater) GalleryApp.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFeed = feed;
        mQuality = quality;
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
        View itemView;
        if (mQuality.equals(ImageQuality.STANDARD)) {
            itemView = mLayoutInflater.inflate(R.layout.feed_pager_item_standard_quality, container, false);
        } else {
            itemView = mLayoutInflater.inflate(R.layout.feed_pager_item_low_quality, container, false);

        }

        Feed feed = mFeed.get(position);

        ViewHolder holder = new ViewHolder(itemView);
//        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        holder.mFeed = feed;
        holder.mQuality = mQuality;
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInteractionListener != null) {
                    mInteractionListener.onItemSelected(position, mQuality);
                }
            }
        });

        if (mQuality.equals(ImageQuality.STANDARD)) {
            StringBuilder sb = new StringBuilder();
            List<String> tags = feed.getTags();
            for (int i = 0; i < tags.size(); i++) {
                if (i != 0)
                    sb.append(", ");
                sb.append("#").append(tags.get(i));
            }
            long timestamp = Long.parseLong(feed.getCreatedTime());
            holder.mPublishDate.setText(Utils.getDate(timestamp));
            holder.mTag.setText(sb.toString());
            if (feed.getUser() != null)
                holder.mAuthor.setText(feed.getUser().getUsername());
        }

        new DownloadImage().execute(holder);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}