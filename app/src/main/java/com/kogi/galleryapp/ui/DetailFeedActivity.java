package com.kogi.galleryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.ui.fragments.DetailFeedFragment;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;

import java.util.List;

/**
 * Actividad encargada de albergar DetailFeedFragment quien administra el detalle de la galeria.
 * Cuenta con opcion de compartir el enlace de la imagen que se muestra y acceder a su pagina web.
 */
public class DetailFeedActivity extends BaseActivity implements OnFragmentInteractionListener {

    private List<Feed> mFeed;
    private int mPosition;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mFeed = extras.getParcelableArrayList(Utils.FEED);
            mPosition = extras.getInt(Utils.FEED_POSITION);
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment = manager.findFragmentByTag(getString(R.string.fragment_full));
        if (fragment == null) {

            // DetailFeedFragment se encarga de toda la presentacion y administracion de la galeria
            DetailFeedFragment detail = DetailFeedFragment.newInstance(mFeed, mPosition);
            transaction.add(R.id.full_fragment, detail, getString(R.string.fragment_full));
            transaction.commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(mFeed.get(mPosition).getCaption());
        }

    }

    @Override
    public void onItemSelected(int position, ImageQuality quality) {

        // Se chequea si se da click a la imagen en la que esta posicionada la galeria
        if (mPosition == position & quality.equals(ImageQuality.STANDARD)) {
            mPosition = position;

            // Si se tiene conexion a internet se viaja a WebActivity
            if (!GalleryApp.getInstance().isNetworkAvailable()) {
                return;
            }

            Intent intent = new Intent(DetailFeedActivity.this, WebActivity.class);
            intent.putExtras(Utils.getFeedBundle(mFeed.get(position)));
            startActivity(intent);
            startSlideAnimation(true);

        } else {
            // Cuando DetailFeedFragment cambia de imagen mostrada, se debe cambiar el
            // texto del toolbar
            mPosition = position;
            if (mActionBar != null) {
                mActionBar.setTitle(mFeed.get(mPosition).getCaption());
            }
        }
    }

    @Override
    public void onSwipeItem(int position) {
    }

    @Override
    public void onItemLongSelected(int position, ImageQuality quality) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_feed_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startSlideAnimation(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startSlideAnimation(true);
                return true;

            case R.id.action_share:
                for (Image image : mFeed.get(mPosition).getImages()) {
                    if (image.getQuality().equals(ImageQuality.STANDARD)) {
                        shareContent(image.getUrl());
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
