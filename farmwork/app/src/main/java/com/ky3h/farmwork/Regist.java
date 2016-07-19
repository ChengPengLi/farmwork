package com.ky3h.farmwork;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.utils.SharedUtils;

/**
 * Created by lipengcheng on 2016/6/2.
 * you are sb
 */
public class Regist extends BaseActivity {
    private EditText regist_account, regist_pass, regist_pass2, code;
    private Button getCode, regist;
    private String username, pwd, pwd2;

    @Override
    public void touchListener(View v) throws HyphenateException {
        switch (v.getId()) {
            case R.id.regist_button:
                startActivity(new Intent(Regist.this,MainActivity.class));
//                showShortToast("注册1");
//                new Thread(new Runnable() {
//                    public void run() {
//                        try {
//                            // 调用sdk注册方法
//                            EMClient.getInstance().createAccount(username, pwd);
//
//
//                            // 保存用户名
//                            SharedUtils.putString("userName", username, Regist.this);
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
//                            finish();
//
//
//                        } catch (final HyphenateException e) {
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//
//                                    int errorCode = e.getErrorCode();
//                                    if (errorCode == EMError.NETWORK_ERROR) {
//                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
//                                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {
//                                        Toast.makeText(getApplicationContext(), "用户已存在", Toast.LENGTH_SHORT).show();
//                                    } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
//                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
//                                    } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
//                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
//                                    } else {
//
//                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed) + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }).start();

        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.regist);
    }

    @Override
    public void dealLogicBeforeInitView() {
    }

    @Override
    public void initView() {
        regist_account = (EditText) findViewById(R.id.regist_account);
        regist_pass = (EditText) findViewById(R.id.regist_password);
        regist_pass2 = (EditText) findViewById(R.id.regist2_password);
        getCode = (Button) findViewById(R.id.regist_getcode);
        code = (EditText) findViewById(R.id.regist_code);
        regist = (Button) findViewById(R.id.regist_button);
        username = regist_account.getText().toString();
        pwd = regist_pass.getText().toString();
        pwd2 = regist_pass2.getText().toString();
        regist.setOnClickListener(this);

    }

    @Override
    public void afterInitView() {

    }

    //注册一个监听连接状态的listener

}


