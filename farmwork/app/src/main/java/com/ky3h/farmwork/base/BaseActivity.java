package com.ky3h.farmwork.base;

import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.ky3h.farmwork.R;
import com.ky3h.farmwork.Regist;
import com.ky3h.farmwork.bean.DeviceInfo;
import com.ky3h.farmwork.utils.SystemBarTintManager;
import com.ky3h.farmwork.widget.LoadingDialog;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by lipengcheng on 2016/6/1.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private LoadingDialog loadingDialog;
    public static DeviceInfo deviceInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        int pid = android.os.Process.myPid();
//        String processAppName = getAppName(pid);
//// 如果APP启用了远程的service，此application:onCreate会被调用2次
//// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
//// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
//
//        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {
//
//
//            // 则此application::onCreate 是被service 调用的，直接返回
//            return;
//        }
//        EMOptions options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//
////初始化
//        EMClient.getInstance().init(this, options);
////在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
        setContentView();


        dealLogicBeforeInitView();
        initView();
        afterInitView();
//        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

        if (deviceInfo == null) {
            deviceInfo = new DeviceInfo();

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            Locale locale = getResources().getConfiguration().locale;
            deviceInfo.setUserAgentName("VoiceDiagnosis");
            deviceInfo.setSoftver(getString(R.string.version));
            deviceInfo.setWidthPixels(dm.widthPixels);
            deviceInfo.setHeightPixels(dm.heightPixels);
            deviceInfo.setLanguage(locale.getLanguage());
            deviceInfo.setCountry(locale.getCountry());
        }
    }

    @Override
    public void onClick(View v) {
        try {
            touchListener(v);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public abstract void touchListener(View v) throws HyphenateException;

    public void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    protected void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    protected void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
    /**
     * 设置布局文件
     */
    public abstract void setContentView();



    /**
     * 实例化之前的操作可以进行逻辑处理
     */
    public abstract void dealLogicBeforeInitView();

    /**
     * 实例化布局
     */
    public abstract void initView();

    /**
     * 实例化布局之后的逻辑处理
     */
    public abstract void afterInitView();
}
