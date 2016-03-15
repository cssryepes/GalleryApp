package com.kogi.galleryapp.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.enums.ResponseStatus;
import com.kogi.galleryapp.domain.enums.SocialMediaType;
import com.kogi.galleryapp.domain.models.OnSocialMediaListener;
import com.kogi.galleryapp.domain.models.SocialMediaModel;

public class TestActivity extends AppCompatActivity implements OnSocialMediaListener {

    LinearLayout container = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        container = (LinearLayout) findViewById(R.id.test_container);

        SocialMediaModel model = new SocialMediaModel(this);
        model.getFeedSocialMedia(SocialMediaType.INSTAGRAM);
    }

    public void printData(Bitmap bitmap){
        ImageView imageView = new ImageView(this);
        //setting image resource
        imageView.setImageBitmap(bitmap);
        //setting image position
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        container.addView(imageView);
    }

    @Override
    public void onDataReceived(ResponseStatus status, Object data) {
        if (data instanceof Bitmap) {
            printData((Bitmap) data);
        } else {
            Snackbar.make(container, status.toString() + " " + data, Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}
