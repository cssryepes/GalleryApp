package com.kogi.galleryapp.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ResponseStatus;
import com.kogi.galleryapp.domain.enums.SocialMediaType;
import com.kogi.galleryapp.domain.models.OnSocialMediaListener;
import com.kogi.galleryapp.domain.models.SocialMediaModel;
import com.kogi.galleryapp.ui.fragments.GridFeedFragment;
import com.kogi.galleryapp.ui.fragments.ImageFeedFragment;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends BaseActivity implements OnSocialMediaListener, OnGalleryChangeListener {

    private List<Feed> mData;
    private SocialMediaModel mModel;
    private LinearLayout mContainer;
    private ImageFeedFragment mImageFeedFragment;
    private GridFeedFragment mGridFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModel = new SocialMediaModel(this);
        new DownloadFeed().execute();

        FragmentManager manager = getSupportFragmentManager();
        mImageFeedFragment = new ImageFeedFragment();
        mGridFeedFragment = new GridFeedFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentTop, mImageFeedFragment, "FragmentBottom");
        transaction.add(R.id.fragmentBottom, mGridFeedFragment, "FragmentTop" );
        transaction.commit();

        mContainer = (LinearLayout) findViewById(R.id.container);

    }

    @Override
    public void onDataReceived(ResponseStatus status, Object data) {

        if (status == ResponseStatus.ERROR) {
            //append log
        } else if (status == ResponseStatus.OK || status == ResponseStatus.NO_CONNECTION) {
            if (status == ResponseStatus.NO_CONNECTION) {
                Snackbar.make(mContainer, "No tiene conexión", Snackbar.LENGTH_LONG)
                        .setAction("REINTENTAR", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new DownloadFeed().execute();
                            }
                        }).show();
            }

            mData = (List<Feed>) data;
        }
    }

    @Override
    public void onGalleryChange(int position) {

    }

    public List<Feed> getData(){
        return mData;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.removeOnSocialMediaListener();
    }

    private class DownloadFeed extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgress(getString(R.string.msgGetOrders), false);
            mData = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            GalleryApp.print(Log.DEBUG, "getFeedSocialMedia - INSTAGRAM");
            mModel.getFeedSocialMedia(SocialMediaType.INSTAGRAM);
            return null;
        }

        @Override
        protected void onPostExecute(Void arg) {
            super.onPostExecute(arg);
            //removeProgress
        }
    }

}
