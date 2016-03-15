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
import com.kogi.galleryapp.domain.enums.ResponseStatus;
import com.kogi.galleryapp.domain.enums.SocialMediaType;
import com.kogi.galleryapp.domain.models.OnSocialMediaListener;
import com.kogi.galleryapp.domain.models.SocialMediaModel;
import com.kogi.galleryapp.ui.fragments.GridFeedFragment;
import com.kogi.galleryapp.ui.fragments.OnGridFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends BaseActivity implements OnSocialMediaListener, OnGridFragmentInteractionListener {

    private List<Feed> mData;
    private SocialMediaModel mModel;
    private LinearLayout mContainer;
    //    private ImageFeedFragment mImageFeedFragment;
    private GridFeedFragment mGridFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModel = new SocialMediaModel(this);
        mModel.getFeedSocialMedia(SocialMediaType.INSTAGRAM);

        mData = new ArrayList<>();

//        FragmentManager manager = getSupportFragmentManager();
//        mImageFeedFragment = new ImageFeedFragment();
//        mGridFeedFragment = new GridFeedFragment();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.fragmentTop, mImageFeedFragment, "FragmentTop");
//        transaction.add(R.id.fragmentBottom, mGridFeedFragment, "FragmentBottom");
//        transaction.commit();

        mContainer = (LinearLayout) findViewById(R.id.container);

    }

    @Override
    public void onDataReceived(ResponseStatus status, Object data) {

        if (status == ResponseStatus.ERROR) {
            //append log
        } else if (status == ResponseStatus.OK || status == ResponseStatus.NO_CONNECTION) {
            if (status == ResponseStatus.NO_CONNECTION) {
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
                        mData.add(0,(Feed) object);
                    }
                }

                if (!mData.isEmpty()) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    //Remove last fragment
                    Fragment fragment = manager.findFragmentByTag("FragmentBottom");
                    if (fragment == null) {
                        //Add fragment
                        mGridFeedFragment = GridFeedFragment.newInstance(mData);
                        transaction.add(R.id.fragmentBottom, mGridFeedFragment, "FragmentBottom").commit();

                    } else {
                        if (fragment instanceof GridFeedFragment) {
                            mGridFeedFragment = (GridFeedFragment) fragment;
                            mGridFeedFragment.notifyDataSetChanged();
                        }
                    }

                }

//                printData((Bitmap) data);
            }
        }
    }


//    public List<Feed> getData() {
//        return mData;
//    }

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
    public void onItemSelected(Feed item) {
        Snackbar.make(mContainer, item.toString(), Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();

    }
}
