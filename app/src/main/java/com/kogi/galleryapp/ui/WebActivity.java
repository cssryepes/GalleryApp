package com.kogi.galleryapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.enums.ImageQuality;

import java.util.List;

public class WebActivity extends AppCompatActivity {

    private Feed mFeed;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mFeed = extras.getParcelable(Utils.FEED);
        }

        WebView webView= (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(mFeed.getLink());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.ic_search_white_48dp);
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
        List<Image> images = mFeed.getImages();
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
