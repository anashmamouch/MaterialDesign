package com.benzino.materialdesign.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Anas on 03/07/2015.
 */
public class MyApp extends Application {

    public static final String API_KEY = "";
    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static MyApp getInstance(){
        return app;
    }

    public static Context getAppContext(){
        return app.getApplicationContext();
    }
}
