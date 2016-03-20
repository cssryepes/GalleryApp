package com.kogi.galleryapp.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.kogi.galleryapp.ui.listeners.OnDialogInteractionListener;

public class WebActivity extends BaseActivity implements OnDialogInteractionListener {

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(mFeed.getLink());
        }

        if (!GalleryApp.getInstance().isNetworkAvailable()) {
            showAlertDialog(getString(R.string.prompt_error), getString(R.string.prompt_no_connectivity),
                    getString(R.string.prompt_back));
            return;
        }

        WebView webView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WebActivity.this.showProgressDialog(null, getString(R.string.prompt_loading));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WebActivity.this.dismissDialog();
            }

        });
        webView.loadUrl(mFeed.getLink());

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
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;

            case R.id.action_share:
                shareContent(mFeed.getLink());
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }


    @Override
    public void onPositiveButtonClicked() {
        super.onPositiveButtonClicked();
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

}
