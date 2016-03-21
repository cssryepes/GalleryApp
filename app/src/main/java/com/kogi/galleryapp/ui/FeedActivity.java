package com.kogi.galleryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.SynchronizeService;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ImageQuality;
import com.kogi.galleryapp.domain.enums.ResponseStatus;
import com.kogi.galleryapp.domain.enums.SocialMediaType;
import com.kogi.galleryapp.model.OnSocialMediaListener;
import com.kogi.galleryapp.model.SocialMediaModel;
import com.kogi.galleryapp.ui.fragments.GridFeedFragment;
import com.kogi.galleryapp.ui.fragments.PreviewFeedFragment;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;
import com.kogi.galleryapp.ui.listeners.OnGridFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad encargada de la visualizacion de la galeria usando PreviewFeedFragment & GridFeedFragment.
 * Sirve como puente de interacción entre los fragments y el modelo.
 *
 * Eventos:
 * - onRefreshGrid():
 *      Disparado por el evento SwipeRefreshLayout del GridFeedFragment
 * - onItemSelected(int position, ImageQuality quality):
 *      Disparado por el evento OnClickListener de la vista del item del RecyclerView en GridFeedFragment.
 *      Disparado por el evento OnClickListener de la vista del item del ViewPager en PreviewFeedFragment.
 * - onSwipeItem(int position):
 *      Disparado por el evento onPageSelected del PreviewFeedFragment.
 *
 */
public class FeedActivity extends BaseActivity implements OnSocialMediaListener,
        OnGridFragmentInteractionListener, OnFragmentInteractionListener {

    private List<Feed> mFeed;
    private SocialMediaModel mModel;
    private LinearLayout mContainer;
    private GridFeedFragment mGridFeedFragment;
    private PreviewFeedFragment mPreviewFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mContainer = (LinearLayout) findViewById(R.id.container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.ic_home_white_48dp);

        showProgressDialog(null, getString(R.string.prompt_downloading_feed));

        mFeed = new ArrayList<>();
        mModel = new SocialMediaModel(this);
        mModel.getFeedSocialMedia(SocialMediaType.INSTAGRAM);
    }

    @Override
    public void onDataReceived(ResponseStatus status, Object data) {

        dismissDialog();

        /**
         * Del modelo recibo tanto el estado de la operacion como el dato.
         * -    Si el estado es ERROR se muestra una advertencia.
         * -    Si el estado es OK, se verifica que el dato sea un listado de feed. Se inicia el
         *      servicio para la descarga de imagenes en segundo plano.
         * -    Si el estado es NO_CONNECTION se chequea el dato.
         *      -   Si el dato es String se despliega advertencia.
         *      -   Si el dato es un listado (de la cache) se imprime en la cuadricula.
         */
        if (status.equals(ResponseStatus.ERROR)) {
            showAlertDialog(getString(R.string.prompt_error), (String) data,
                    getString(R.string.prompt_ok));
            if (mGridFeedFragment != null) {
                mGridFeedFragment.setRefreshLayout(false);
            }

        } else if (status.equals(ResponseStatus.OK) || status.equals(ResponseStatus.NO_CONNECTION)) {
            if (status.equals(ResponseStatus.NO_CONNECTION)) {
                String message = null;
                if (data instanceof String) {
                    message = (String) data;
                } else if (data instanceof List<?>) {
                    message = getString(R.string.prompt_no_connectivity);
                }
                showAlertDialog(getString(R.string.prompt_error), message,
                        getString(R.string.prompt_ok));
            }

            if (data instanceof List<?>) {
                List<?> castedData = (List<?>) data;
                List<Feed> feedReceived = new ArrayList<>();
                int newDataLength = castedData.size();

                // Se castea objeto por objeto
                for (Object object : castedData) {
                    if (object instanceof Feed) {
                        feedReceived.add((Feed) object);
                    }
                }

                // Si el feed que llega es nuevo se lanza el servicio de actualizacion de imagenes
                if (!feedReceived.isEmpty() && status.equals(ResponseStatus.OK)) {
                    Intent intent = new Intent(this, SynchronizeService.class);
                    intent.putExtras(Utils.getListFeedBundle(feedReceived, 0));
                    startService(intent);
                }

                mFeed.addAll(0, feedReceived);

                // Solo muestro los fragments si tengo al menos un feed
                if (!mFeed.isEmpty()) {
                    setFragments(newDataLength);
                }

            }
        }
    }

    /**
     * Se envia la longitud de los nuevos datos, actualizando PreviewFeedFragment & GridFeedFragment
     * si existen, de lo contrario crea nueva instancia de cada uno
     */
    private void setFragments(int newDataLength) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment = manager.findFragmentByTag(getString(R.string.fragment_bottom));
        if (fragment == null) {
            mGridFeedFragment = GridFeedFragment.newInstance(mFeed);
            transaction.add(R.id.fragmentBottom, mGridFeedFragment, getString(R.string.fragment_bottom));
        }

        fragment = manager.findFragmentByTag(getString(R.string.fragment_top));
        if (fragment == null) {
            mPreviewFeedFragment = PreviewFeedFragment.newInstance(mFeed);
            transaction.add(R.id.fragmentTop, mPreviewFeedFragment, getString(R.string.fragment_top)).commit();
        }

        if (fragment != null) {
            notifyDataSetChanged(newDataLength);
        }
    }

    /**
     * Notifica a cada fragment la nueva cantidad de datos
     */
    private void notifyDataSetChanged(int newDataLength) {
        if (mPreviewFeedFragment != null) {
            mPreviewFeedFragment.notifyDataSetChanged(newDataLength);
        }
        if (mGridFeedFragment != null) {
            mGridFeedFragment.notifyDataSetChanged(newDataLength);
            mGridFeedFragment.setRefreshLayout(false);
        }
    }

    @Override
    public void onRefreshGrid() {
        mModel.refreshFeedSocialMedia(SocialMediaType.INSTAGRAM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_menu, menu);
        return true;
    }

    @Override
    public void onItemSelected(int position, ImageQuality quality) {

        // GridFeedFragment - imagenes calidad THUMBNAIL
        // PreviewFeedFragment - imagenes calidad LOW
        if (quality.equals(ImageQuality.THUMBNAIL)) {
            if (mPreviewFeedFragment != null) {
                mPreviewFeedFragment.showFeed(position);
            }

        } else if (quality.equals(ImageQuality.LOW)) {
            Intent intent = new Intent(FeedActivity.this, DetailFeedActivity.class);
            intent.putExtras(Utils.getListFeedBundle(mFeed, position));
            startActivity(intent);
            startSlideAnimation(true);

        }

    }

    @Override
    public void onSwipeItem(int position) {
        if (mGridFeedFragment != null) {
            mGridFeedFragment.showFeed(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.removeOnSocialMediaListener();
    }

    @Override
    public void onItemLongSelected(int position, ImageQuality quality) {
        Snackbar.make(mContainer, mFeed.get(position).getCaption(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.prompt_dismiss, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mModel.refreshFeedSocialMedia(SocialMediaType.INSTAGRAM);
                showProgressDialog(null, getString(R.string.prompt_downloading_feed));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
