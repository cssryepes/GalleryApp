package com.kogi.galleryapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.fragments.adapters.CustomPagerAdapter;
import com.kogi.galleryapp.ui.helpers.DepthPageTransformer;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

public class DetailFeedFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private OnFragmentInteractionListener mListener;
    private List<Feed> mFeed;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private int mPosition;

    public DetailFeedFragment() {
    }

    public static DetailFeedFragment newInstance(List<Feed> feed, int position) {
        DetailFeedFragment fragment = new DetailFeedFragment();
        fragment.setArguments(Utils.getListFeedBundle(feed, position));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mFeed = getArguments().getParcelableArrayList(Utils.FEED);
            mPosition = getArguments().getInt(Utils.FEED_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_detail, container, false);
        mCustomPagerAdapter = new CustomPagerAdapter(mListener, mFeed, ImageQuality.STANDARD);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(mPosition);
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
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mListener != null) {
            mListener.onItemSelected(position, ImageQuality.LOW);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
