package com.ky3h.farmwork.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lipengcheng on 2016/6/1.
 * you are sb
 */
public class SharedUtils {
    public static void putString(String key,String value,Context cotext ){
        SharedPreferences sharedPreferences=cotext.getSharedPreferences("farmwork",0);
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static String getString(String key,String defaultvalue,Context cotext ){
        SharedPreferences sharedPreferences=cotext.getSharedPreferences("farmwork",0);
        String content=sharedPreferences.getString(key,defaultvalue);
        return content;
    }
}
