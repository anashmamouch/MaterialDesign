package com.benzino.materialdesign.extras;

/**
 * Created by Anas on 03/07/2015.
 */

import android.os.Build;

/**
 * Created by Windows on 05-02-2015.
 */
public class Util {
    public static boolean isLollipopOrGreater() {
        return Build.VERSION.SDK_INT >= 21 ? true : false;
    }
    public static boolean isJellyBeanOrGreater(){
        return Build.VERSION.SDK_INT>=16?true:false;
    }
}