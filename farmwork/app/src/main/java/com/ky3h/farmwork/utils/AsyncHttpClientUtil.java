package com.ky3h.farmwork.utils;

import java.util.concurrent.Executors;

import android.content.Context;

import android.content.pm.PackageManager.NameNotFoundException;


import com.ky3h.farmwork.application.Application;
import com.ky3h.farmwork.base.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;

public class AsyncHttpClientUtil {
	private static AsyncHttpClient client ;
	
	private static int getVersion() {
		Context context = Application.getContext();
		int versionCode = 0;
		String packageName = context.getPackageName();
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
    }
	
	public static AsyncHttpClient getClient(){
		
		if(client == null){
			client = new AsyncHttpClient();
			client.setThreadPool(Executors.newFixedThreadPool(3));
			client.setMaxConnections(3);
			client.setTimeout(20000);
			client.setUserAgent(BaseActivity.deviceInfo.getUserAgent());
//			client.addHeader("version", "android_wybs_yyb-yh-"+getVersion());	//应用宝参数
//			client.addHeader("version", "android_wybs-yh-"+getVersion());    	//测试参数
			if (Constant.BASE_URL.equals(Constant.TEST_SYSTEM_URL)) {
				client.addHeader("version", Constant.TEST_SYSTEM_HEADER + getVersion());
			} else if (Constant.BASE_URL.equals(Constant.PRODUCTION_SYSTEM_URL)) {
				client.addHeader("version", Constant.PRODUCTION_SYSTEM_HEADER + getVersion());
			}
		}
        return client;
	}
}
