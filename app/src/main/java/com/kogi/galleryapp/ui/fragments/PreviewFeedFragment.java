package com.kogi.galleryapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.fragments.adapters.CustomPagerAdapter;
import com.kogi.galleryapp.ui.helpers.ZoomOutPageTransformer;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

public class PreviewFeedFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private OnFragmentInteractionListener mListener;
    private List<Feed> mFeed;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;

    public PreviewFeedFragment() {
    }

    public ImageView getCurrentImageView() {
        return mCustomPagerAdapter.getCurrentImageView();
    }

    public void notifyDataSetChanged(int newDataLenght) {
        mCustomPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + newDataLenght);
    }

    public void showFeed(int position) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position, true);
            // TODO REVISAR BUG DEL ESTADO VIEWPAGER Y GRIDRECYCLER DESPUES DEL PULLREFRESH
//            mCustomPagerAdapter.notifyDataSetChanged();
        }
    }

    public static PreviewFeedFragment newInstance(List<Feed> feed) {
        PreviewFeedFragment fragment = new PreviewFeedFragment();
        fragment.setArguments(Utils.getListFeedBundle(feed, 0));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFeed = getArguments().getParcelableArrayList(Utils.FEED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_preview, container, false);
        mCustomPagerAdapter = new CustomPagerAdapter(mListener, mFeed, ImageQuality.LOW);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.addOnPageChangeListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mViewPager.clearOnPageChangeListeners();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mListener != null) {
            mListener.onSwipeItem(position);
//            mListener.onItemSelected(position, ImageQuality.LOW);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
