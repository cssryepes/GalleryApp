package com.kogi.galleryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
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

public class FeedActivity extends AppCompatActivity implements OnSocialMediaListener, OnGridFragmentInteractionListener, OnFragmentInteractionListener {

    private List<Feed> mFeed;
    private SocialMediaModel mModel;
    private LinearLayout mContainer;
    private GridFeedFragment mGridFeedFragment;
    private PreviewFeedFragment mPreviewFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mFeed = new ArrayList<>();
        mModel = new SocialMediaModel(this);
        mModel.getFeedSocialMedia(SocialMediaType.INSTAGRAM);

        mContainer = (LinearLayout) findViewById(R.id.container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.ic_home_white_48dp);
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
                int newDataLenght = castedData.size();
                for (Object object : castedData) {
                    if (object instanceof Feed) {
                        mFeed.add(0, (Feed) object);
                    }
                }

                if (!mFeed.isEmpty()) {
                    setFragments(newDataLenght);
                }

            }
        }
    }

    private void setFragments(int newDataLenght) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        //Remove last fragment
        Fragment fragment = manager.findFragmentByTag("FragmentBottom");
        if (fragment == null) {
            //Add fragment
            mGridFeedFragment = GridFeedFragment.newInstance(mFeed);
            transaction.add(R.id.fragmentBottom, mGridFeedFragment, "FragmentBottom");

        } else {
            if (fragment instanceof GridFeedFragment) {
                mGridFeedFragment = (GridFeedFragment) fragment;
                notifyDataSetChanged(newDataLenght);
            }
        }


        fragment = manager.findFragmentByTag("FragmentBottom");
        if (fragment == null) {
            //Add fragment
            mPreviewFeedFragment = PreviewFeedFragment.newInstance(mFeed);
            transaction.add(R.id.fragmentTop, mPreviewFeedFragment, "FragmentTop").commit();

        } else {
            if (fragment instanceof PreviewFeedFragment) {
                mPreviewFeedFragment = (PreviewFeedFragment) fragment;
                notifyDataSetChanged(newDataLenght);
            }
        }
    }

    private void notifyDataSetChanged(int newDataLenght) {
        if (mPreviewFeedFragment != null) {
            mPreviewFeedFragment.notifyDataSetChanged(newDataLenght);
        }
        if (mGridFeedFragment != null) {
            mGridFeedFragment.notifyDataSetChanged(newDataLenght);
            mGridFeedFragment.setRefreshLayout(false);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_menu, menu);
        return true;
    }

    @Override
    public void onItemSelected(int position, ImageQuality quality) {
        if (quality.equals(ImageQuality.THUMBNAIL)) {
            if (mPreviewFeedFragment != null) {
                mPreviewFeedFragment.showFeed(position);
            }

        } else if (quality.equals(ImageQuality.LOW)) {
            Intent myIntent = new Intent(FeedActivity.this, DetailFeedActivity.class);
            myIntent.putExtras(Utils.getBundle(mFeed, position));
            startActivity(myIntent);

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
        if (mGridFeedFragment != null) {
            mGridFeedFragment.showFeed(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mModel.getFeedSocialMedia(SocialMediaType.INSTAGRAM);
                mGridFeedFragment.setRefreshLayout(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
