package com.ky3h.farmwork;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.hyphenate.exceptions.HyphenateException;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.netrequest.UserManager;
import com.ky3h.farmwork.utils.NoHttpUtil;
import com.ky3h.farmwork.utils.SharedUtils;
import com.ky3h.farmwork.utils.StringUtil;
import com.yolanda.nohttp.able.Startable;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by lipengcheng on 2016/9/1.
 * you are sb
 */
public class SplashActivity extends BaseActivity {
    private String username;
    private String password;
    private UserManager manager;
    private RequestQueue requestQueue;
    private Intent intent;
    private Button chongxin;

    @Override
    public void touchListener(View v) throws HyphenateException {
        switch (v.getId()) {
            case R.id.chongxin:
                if (!StringUtil.isNullOrEmpty(username) && !StringUtil.isNullOrEmpty(password)) {
                    showLoadingDialog();
                    chongxin.setVisibility(View.GONE);
                    manager.LoginFromNohttp(username, password, requestQueue);
                } else {
                    intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    finish();

                }
                break;


        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.splash_activity);
    }

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {
        manager = new UserManager(handler);
        username = SharedUtils.getString("username", "", SplashActivity.this);
        password = SharedUtils.getString("userpass", "", SplashActivity.this);
        requestQueue = NoHttpUtil.getRequestQueue();
        chongxin = (Button) findViewById(R.id.chongxin);
        chongxin.setOnClickListener(this);
    }

    @Override
    public void afterInitView() {
        if (!StringUtil.isNullOrEmpty(username) && !StringUtil.isNullOrEmpty(password)) {
            showLoadingDialog();
            manager.LoginFromNohttp(username, password, requestQueue);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            intent = new Intent(SplashActivity.this, Login.class);
            startActivity(intent);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UserManager.LOGIN_FAIL:
                    showShortToast("抱歉,登录失败,请重新登录");
                    dismissLoadingDialog();
                    intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                    break;
                case UserManager.LOGIN_NETWORK_ERROR:
                    showShortToast("抱歉,您的网络连接有问题,请重新尝试");
                    dismissLoadingDialog();
                    chongxin.setVisibility(View.VISIBLE);
                    startActivity(intent);
                    finish();
                    break;
                case UserManager.LOGIN_SUCCESS:
                    showShortToast("登录成功，欢迎！！");
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;


            }
        }
    };
}
