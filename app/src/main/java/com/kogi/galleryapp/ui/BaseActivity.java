package com.kogi.galleryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.ui.fragments.GalleryDialogFragment;
import com.kogi.galleryapp.ui.listeners.OnDialogInteractionListener;

/**
 * Actividad padre para facilitar el uso de los popup y las animaciones
 */
public class BaseActivity extends AppCompatActivity implements OnDialogInteractionListener {

    private GalleryDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    protected void showAlertDialog(String title, String message, String button) {
        showDialogFragment(GalleryDialogFragment.STYLE_ALERT, title, message, button);
    }

    protected void showProgressDialog(String title, String message) {
        showDialogFragment(GalleryDialogFragment.STYLE_PROGRESS, title, message, null);
    }

    private void showDialogFragment(int style, String title, String message, String button) {
        dismissDialog();

        if (style == GalleryDialogFragment.STYLE_ALERT) {
            mDialogFragment = GalleryDialogFragment.newAlertDialog(title, message, button);
        } else if (style == GalleryDialogFragment.STYLE_PROGRESS) {
            mDialogFragment = GalleryDialogFragment.newProgressDialog(title, message);
        }

        if (mDialogFragment != null) {
            mDialogFragment.show(getSupportFragmentManager(), getString(R.string.fragment_dialog));
        }
    }

    protected void shareContent(String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.intent_extra_subject));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.intent_extra_chooser)));
    }

    protected void dismissDialog() {
        if (mDialogFragment != null) {
            mDialogFragment.dismiss();
        }
    }

    protected void startSlideAnimation(boolean entering) {
        if (entering) {
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
    }

    @Override
    public void onPositiveButtonClicked() {
        dismissDialog();
    }

    @Override
    public void onNegativeButtonClicked() {
        dismissDialog();
    }
}
