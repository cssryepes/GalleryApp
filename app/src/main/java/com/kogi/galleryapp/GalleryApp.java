package com.kogi.galleryapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.LruCache;

import com.kogi.galleryapp.ui.helpers.DiskCache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class GalleryApp extends Application {

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    private static GalleryApp instance = null;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskCache mDiskCache;

    public static GalleryApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        try {
            // Initialize disk cache on background thread
            File cacheDir = getDiskCacheDir(this, DISK_CACHE_SUBDIR);
            mDiskCache = DiskCache.open(cacheDir, 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
// but if not mounted, falls back on internal storage.
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !Environment.isExternalStorageRemovable() ? GalleryApp.getInstance().getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }

        // Also add to disk cache
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            mDiskCache.put(key, new ByteArrayInputStream(stream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap == null) {
            try {
                DiskCache.BitmapEntry bitmapEntry = mDiskCache.getBitmap(key);
                if (bitmapEntry != null)
                    return bitmapEntry.getBitmap();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}
