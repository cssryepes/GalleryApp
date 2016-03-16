package com.kogi.galleryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.fragments.DetailFeedFragment;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

public class TestActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private List<Feed> mFeed;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        mFeed = intent.getExtras().getParcelableArrayList(GalleryApp.FEED);
        mPosition = intent.getExtras().getInt(GalleryApp.POSITION);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        DetailFeedFragment detail = DetailFeedFragment.newInstance(mFeed, mPosition);
        transaction.add(R.id.full_fragment, detail, "FullFragment");
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSelected(int position, ImageQuality quality) {

    }

    @Override
    public void onSwipeItem(int position) {

    }
}
