package com.kogi.galleryapp.ui.fragments;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    List<Feed> mFeed;
    Context mContext;
    LayoutInflater mLayoutInflater;
 
    public CustomPagerAdapter(Context context, List<Feed> feed) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.feed_pager_item, container, false);
 
//        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//        imageView.setImageResource(mResources[position]);
 
        container.addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}