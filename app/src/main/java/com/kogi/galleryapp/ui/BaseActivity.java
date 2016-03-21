package com.kogi.galleryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.ui.fragments.GalleryDialogFragment;
import com.kogi.galleryapp.ui.listeners.OnDialogInteractionListener;

public class BaseActivity extends AppCompatActivity implements OnDialogInteractionListener {

    private GalleryDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    protected void showAlertDialog(String title, String message, String button) {
        dismissDialog();
        mDialogFragment = GalleryDialogFragment.newAlertDialog(title, message, button);
        mDialogFragment.show(getSupportFragmentManager(), getString(R.string.fragment_dialog));
    }

    protected void showProgressDialog(String title, String message) {
        dismissDialog();
        mDialogFragment = GalleryDialogFragment.newProgressDialog(title, message);
        mDialogFragment.show(getSupportFragmentManager(), getString(R.string.fragment_dialog));
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

    @Override
    public void onPositiveButtonClicked() {
        dismissDialog();
    }

    @Override
    public void onNegativeButtonClicked() {
        dismissDialog();
    }
}
