package com.ky3h.farmwork.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.ky3h.farmwork.application.Application;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by lipengcheng on 2016/8/1.
 */
public class NoHttpUtil {
    public static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = NoHttp.newRequestQueue();
            return requestQueue;
        }
        return requestQueue;
    }

    public static int getVersion() {
        Context context = Application.getInstance().getBaseContext();
        int versionCode = 0;
        String packageName = context.getPackageName();
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 取消这个sign标记的所有请求.
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求.
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    /**
     * 退出app时停止所有请求.
     */
    public void stopAll() {
        requestQueue.stop();
    }

}
