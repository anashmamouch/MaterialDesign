package com.benzino.materialdesign.network;

import android.graphics.Bitmap;
//import android.util.LruCache;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.benzino.materialdesign.app.MyApp;

/**
 * Created by Anas on 03/07/2015.
 */
public class VolleySingelton {

    private static VolleySingelton singelton = null ;
    private static ImageLoader imageLoader;
    private static RequestQueue requestQueue ;

    private VolleySingelton(){
        requestQueue = Volley.newRequestQueue(MyApp.getAppContext());
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingelton getInstance(){
        if(singelton == null)
            singelton = new VolleySingelton();

        return singelton;
    }

    public static  RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public static ImageLoader getImageLoader(){
        return imageLoader;
    }
}
