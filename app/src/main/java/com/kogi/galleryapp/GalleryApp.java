package com.kogi.galleryapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import com.kogi.galleryapp.ui.helpers.DiskCache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Cache manager
 */
public class GalleryApp extends Application {

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIRECTORY = "GalleryApp";

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

        /**
         * Cache de memoria
         */
        // Obtenemos el maximo de memoria disponible en la VM, si se excede esta cantidad se
        // generara un OutOfMemory exception. Medida en kilobytes
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //Solo usamos 1/8 de la memoria disponible para el cache en memoria
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                 //El tamaño de la cache es medida en kilobytes
                return bitmap.getByteCount() / 1024;
            }
        };

        /**
         * Cache de disco
         */
        try {
            File cacheDir = getDiskCacheDir(this, DISK_CACHE_SUBDIRECTORY);
            mDiskCache = DiskCache.open(cacheDir, 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            Utils.print(Log.ERROR, Utils.getStackTrace(e));
        }

    }

    /**
     * Crea un subdirectorio destinado para el cache de la aplicacion. Primero se busca alojar en
     * el almacenamiento externo, si no se encuentra se hace sobre el interno.
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !Environment.isExternalStorageRemovable() ? getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * Revision del estado de conectividad.
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Guarda un bitmap en cache, primero lo hace en memoria, luego en disco.
     */
    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
        addBitmapToDiskCache(key, bitmap);
    }

    /**
     * Guardar bitmap en cache de disco.
     */
    public void addBitmapToDiskCache(String key, Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            mDiskCache.put(key, new ByteArrayInputStream(stream.toByteArray()));
        } catch (IOException e) {
            Utils.print(Log.ERROR, Utils.getStackTrace(e));
        }
    }

    /**
     * Obtener bitmap de la cache. Primero busca en la memoria, si no lo encuentra busca en el disco
     */
    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap == null) {
            try {
                DiskCache.BitmapEntry bitmapEntry = mDiskCache.getBitmap(key);
                if (bitmapEntry != null) {
                    return bitmapEntry.getBitmap();
                }
            } catch (IOException e) {
                Utils.print(Log.ERROR, Utils.getStackTrace(e));
            }
        }
        return bitmap;
    }

    /**
     * Guardar String en la cache de disco
     */
    public void addJSONToCache(String key, String json) {
        try {
            mDiskCache.put(key, json);
        } catch (IOException e) {
            Utils.print(Log.ERROR, Utils.getStackTrace(e));
        }
    }

    /**
     * Obtener String de la cache
     */
    public String getJSONFromCache(String key) {
        try {
            DiskCache.StringEntry entry = mDiskCache.getString(key);
            if (entry != null) {
                return entry.getString();
            }
        } catch (IOException e) {
            Utils.print(Log.ERROR, Utils.getStackTrace(e));
        }
        return null;
    }

}
