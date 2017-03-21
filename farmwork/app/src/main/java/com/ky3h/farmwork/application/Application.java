package com.ky3h.farmwork.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;

import com.ky3h.farmwork.bean.AppInfo;
import com.ky3h.farmwork.bean.UserInfo;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;


public class Application extends android.app.Application {
    public static int screenWidth;
    public static int screenHeight;
    private static Application _instance;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        mContext=getApplicationContext();

        NoHttp.initialize(this);

        Logger.setTag("NoHttpSample");
        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志
        DisplayMetrics dm = getResources().getDisplayMetrics();

        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        AppConfig.getInstance();
    }

    public static Application getInstance() {
        return _instance;
    }
    public static Context  getContext(){
        return mContext;
    }

}
