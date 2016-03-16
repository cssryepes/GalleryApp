package com.kogi.galleryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.fragments.DetailFeedFragment;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

public class DetailFeedActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private List<Feed> mFeed;
    private int mPosition;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        Intent intent = getIntent();
        mFeed = intent.getExtras().getParcelableArrayList(GalleryApp.FEED);
        mPosition = intent.getExtras().getInt(GalleryApp.POSITION);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        DetailFeedFragment detail = DetailFeedFragment.newInstance(mFeed, mPosition);
        transaction.add(R.id.full_fragment, detail, "FullFragment");
        transaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle( mFeed.get(mPosition).getCaption());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSelected(int position, ImageQuality quality) {
        if (mPosition == position) {
            mPosition = position;

        } else {
            mPosition = position;
            if (mActionBar != null) {
                mActionBar.setTitle( mFeed.get(mPosition).getCaption());
            }
        }
    }

    @Override
    public void onSwipeItem(int position) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_feed_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareContent();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void shareContent() {
        String imageUrl = null;
        List<Image> images = mFeed.get(mPosition).getImages();
        for (Image image : images) {
            if (image.getQuality().equals(ImageQuality.STANDARD))
                imageUrl = image.getUrl();
        }

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.intent_extra_subject));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,imageUrl);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.intent_extra_chooser)));
    }
}
