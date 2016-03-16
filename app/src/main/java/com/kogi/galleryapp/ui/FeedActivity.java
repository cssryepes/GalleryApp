package com.kogi.galleryapp.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.domain.enums.ResponseStatus;
import com.kogi.galleryapp.domain.enums.SocialMediaType;
import com.kogi.galleryapp.domain.models.OnSocialMediaListener;
import com.kogi.galleryapp.domain.models.SocialMediaModel;
import com.kogi.galleryapp.ui.fragments.GridFeedFragment;
import com.kogi.galleryapp.ui.fragments.PreviewFeedFragment;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;
import com.kogi.galleryapp.ui.listeners.OnGridFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends BaseActivity implements OnSocialMediaListener, OnGridFragmentInteractionListener, OnFragmentInteractionListener {

    public static final String FEED = "FEED";

    private List<Feed> mData;
    private SocialMediaModel mModel;
    private LinearLayout mContainer;
    private GridFeedFragment mGridFeedFragment;
    private PreviewFeedFragment mPreviewFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModel = new SocialMediaModel(this);
        mModel.getFeedSocialMedia(SocialMediaType.INSTAGRAM);

        mData = new ArrayList<>();


        mContainer = (LinearLayout) findViewById(R.id.container);

    }

    @Override
    public void onDataReceived(ResponseStatus status, Object data) {

        if (status.equals(ResponseStatus.ERROR)) {
            //append log
        } else if (status.equals(ResponseStatus.OK) || status.equals(ResponseStatus.NO_CONNECTION)) {
            if (status.equals(ResponseStatus.NO_CONNECTION)) {
                Snackbar.make(mContainer, status.name(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TASK

                            }
                        }).show();
            }

            if (data instanceof List<?>) {
                List<?> castedData = (List<?>) data;
                for (Object object : castedData) {
                    if (object instanceof Feed) {
                        mData.add(0, (Feed) object);
                    }
                }

                if (!mData.isEmpty()) {
                    setFragments();
                }

            }
        }
    }

    private void setFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        //Remove last fragment
        Fragment fragment = manager.findFragmentByTag("FragmentBottom");
        if (fragment == null) {
            //Add fragment
            mGridFeedFragment = GridFeedFragment.newInstance(mData);
            transaction.add(R.id.fragmentBottom, mGridFeedFragment, "FragmentBottom");

        } else {
            if (fragment instanceof GridFeedFragment) {
                mGridFeedFragment = (GridFeedFragment) fragment;
                notifyDataSetChanged();
            }
        }


        fragment = manager.findFragmentByTag("FragmentBottom");
        if (fragment == null) {
            //Add fragment
            mPreviewFeedFragment = PreviewFeedFragment.newInstance(mData);
            transaction.add(R.id.fragmentTop, mPreviewFeedFragment, "FragmentTop").commit();

        } else {
            if (fragment instanceof PreviewFeedFragment) {
                mPreviewFeedFragment = (PreviewFeedFragment) fragment;
                notifyDataSetChanged();
            }
        }
    }

    private void notifyDataSetChanged() {
        if (mPreviewFeedFragment != null) {
            mPreviewFeedFragment.notifyDataSetChanged();
        }
        if (mGridFeedFragment != null) {
            mGridFeedFragment.notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.removeOnSocialMediaListener();
    }

    @Override
    public void onRefreshGrid() {
        mModel.getFeedSocialMedia(SocialMediaType.INSTAGRAM);
    }

    @Override
    public void onItemSelected(int position, ImageQuality quality) {
        if (quality.equals(ImageQuality.THUMBNAIL)) {
            if (mPreviewFeedFragment != null) {
                mPreviewFeedFragment.showFeed(position);
            }

        } else if (quality.equals(ImageQuality.LOW)) {
            if (mGridFeedFragment != null) {
                mGridFeedFragment.showFeed(position);
            }
            //TODO Mostrar fragment detallado

        } else if (quality.equals(ImageQuality.STANDARD)) {

        } else {
            Snackbar.make(mContainer, position, Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
        }

    }

    @Override
    public void onSwipeItem(int position) {

    }
}
