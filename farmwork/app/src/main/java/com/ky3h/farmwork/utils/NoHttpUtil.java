package com.ky3h.farmwork.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.ky3h.farmwork.application.Application;
import com.ky3h.farmwork.widget.LoadingDialog;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by lipengcheng on 2016/8/1.
 */
public class NoHttpUtil {
    public static RequestQueue requestQueue;
    public static String result;

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

    public static void addLisenter(int what, Request request, final Context context, final Handler handler) {

        final LoadingDialog dialog = new LoadingDialog(context);
        requestQueue.add(what, request, new OnResponseListener() {
            @Override
            public void onStart(int i) {

                dialog.show();
            }

            @Override
            public void onSucceed(int i, Response response) {
                switch (i) {
                    case 1:
                        result = (String) response.get();
                        Log.i("response", result);
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = result;
                        handler.sendMessage(msg);
                        break;
                    case 2:
                        result = (String) response.get();
                        Log.i("response", result);
                        Message msg1 = Message.obtain();
                        msg1.what = 2;
                        msg1.obj = result;
                        handler.sendMessage(msg1);
                        break;

                }


            }

            @Override
            public void onFailed(int i, String s, Object o, Exception e, int i1, long l) {
                result = s;
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFinish(int i) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });


    }



}
