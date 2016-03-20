package com.kogi.galleryapp.ui.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.ui.listeners.OnDialogInteractionListener;

public class GalleryDialogFragment extends DialogFragment {

    public static final int STYLE_PROGRESS = 0;
    public static final int STYLE_ALERT = 1;
    public static final int STYLE_INPUT = 2;

    private OnDialogInteractionListener mListener;

    private int mStyle;
    private String mTitle;
    private String mMessage;
    private String mButton;


    public static GalleryDialogFragment newProgressDialog(String title, String message) {
        GalleryDialogFragment fragment = new GalleryDialogFragment();
        fragment.setArguments(Utils.getDialogBundle(STYLE_PROGRESS, title, message, null));
        return fragment;
    }

    public static GalleryDialogFragment newAlertDialog(String title, String message, String button) {
        GalleryDialogFragment fragment = new GalleryDialogFragment();
        fragment.setArguments(Utils.getDialogBundle(STYLE_ALERT, title, message, button));
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            mStyle = getArguments().getInt(Utils.DIALOG_STYLE);
            mTitle = getArguments().getString(Utils.DIALOG_TITLE);
            mMessage = getArguments().getString(Utils.DIALOG_BODY);
            mButton = getArguments().getString(Utils.DIALOG_BUTTON);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (mStyle == STYLE_ALERT) {
            if (mTitle != null) {
                builder.setTitle(mTitle);
            }

            if (mMessage != null) {
                builder.setMessage(mMessage);
            }

            if (mButton != null) {
                builder.setPositiveButton(mButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onPositiveButtonClicked();
                    }
                });
            }

        } else if (mStyle == STYLE_PROGRESS) {
            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, null);
            if (mTitle != null) {
                progressDialog.setTitle(mTitle);
            }

            if (mMessage != null) {
                progressDialog.setMessage(mMessage);
            }

            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            return progressDialog;

        }


        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogInteractionListener) {
            mListener = (OnDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}