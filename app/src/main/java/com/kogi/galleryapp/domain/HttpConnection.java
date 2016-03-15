package com.kogi.galleryapp.domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    public static String getResponse(String URL) throws IOException {
        String result = "";
        InputStream is;

        URL url = new URL(URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(60000);
        urlConnection.setConnectTimeout(10000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(false);
        urlConnection.setDoInput(true);

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            is = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            String temp;

            while ((temp = bReader.readLine()) != null) {
                result += temp;
            }
        }

        urlConnection.disconnect();

        return result;
    }

    public static Bitmap getBitmapFromURL(String URL) throws IOException {
        URL url = new URL(URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setReadTimeout(60000);
        urlConnection.setConnectTimeout(10000);
        urlConnection.connect();
        InputStream input = urlConnection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        urlConnection.disconnect();
        return myBitmap;
    }

}
