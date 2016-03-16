package com.kogi.galleryapp.ui.fragments.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.fragments.adapters.helpers.DownloadImage;
import com.kogi.galleryapp.ui.fragments.adapters.helpers.ViewHolder;

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
 
        ViewHolder holder = new ViewHolder(itemView);
        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        holder.mFeed = mFeed.get(position);
        holder.mQuality = ImageQuality.LOW;
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GalleryApp.getInstance(),"ImageClicked", Toast.LENGTH_LONG).show();
//                if (mInteractionListener != null) {
//                    mInteractionListener.onItemSelected(mFeed.get(position));
//                }

            }
        });

        new DownloadImage().execute(holder);

        container.addView(itemView);
 
        return itemView;
    }

//    public class ViewHolder {
//        public View mView;
//        public ImageView mImageView;
//        public TextView mContentView;
//        public Bitmap mBitmap;
//        public Feed mFeed;
//
//        public ViewHolder(View view) {
//            super();
//            mView = view;
//            mImageView = (ImageView) view.findViewById(R.id.image_view);
//            mContentView = (TextView) view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final ImageView mImageView;
//        public final TextView mContentView;
//        public Bitmap mBitmap;
//        public Feed mFeed;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mImageView = (ImageView) view.findViewById(R.id.image_view);
//            mContentView = (TextView) view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }

}