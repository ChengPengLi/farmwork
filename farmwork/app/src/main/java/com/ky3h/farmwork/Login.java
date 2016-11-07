package com.ky3h.farmwork;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ky3h.farmwork.application.Application;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.netrequest.UserManager;
import com.ky3h.farmwork.utils.NoHttpUtil;
import com.ky3h.farmwork.utils.SharedUtils;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by lipengcheng on 2016/6/1.
 * you are sb
 */
public class Login extends BaseActivity {
    private Button login, regist;
    private EditText account, password;
    private TextView forgetPass;
    private String accountNumber, passWordNumber;
    private RequestQueue requestQueue;

    private UserManager manager;

    @Override
    public void touchListener(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (account.getText().toString().length() < 11) {
                    Toast.makeText(Login.this, "手机号格式错误，请正确输入您的手机号码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length() < 6) {
                    Toast.makeText(Login.this, "请输入6-20位字符，可使用字母，数字或字符组合！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                accountNumber = account.getText().toString();
                passWordNumber = password.getText().toString();
                requestQueue = NoHttpUtil.getRequestQueue();
                manager.LoginFromNohttp(accountNumber, passWordNumber, requestQueue);
                showLoadingDialog();
                break;
            case R.id.btn_reg:
                Intent intent1 = new Intent(Login.this, Regist.class);
                startActivity(intent1);
                break;
            case R.id.foget_pass:
                break;
        }


    }

    @Override
    public void setContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
    }

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {
        login = (Button) findViewById(R.id.btn_login);
        regist = (Button) findViewById(R.id.btn_reg);
        account = (EditText) findViewById(R.id.edit_account);
        password = (EditText) findViewById(R.id.edit_pass);
        forgetPass = (TextView) findViewById(R.id.foget_pass);
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
        manager = new UserManager(handler);

    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UserManager.LOGIN_NETWORK_ERROR:
                    dismissLoadingDialog();
                    showShortToast("您的网络出现问题，请重新尝试。。");
                    break;
                case UserManager.LOGIN_FAIL:
                    dismissLoadingDialog();
                    showShortToast("抱歉，登录失败，请重试");

                    break;
                case UserManager.LOGIN_SUCCESS:
                    if (msg.arg1 == 100) {
                        dismissLoadingDialog();
                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        SharedUtils.putString("username", accountNumber, Login.this);
                        SharedUtils.putString("userpass", passWordNumber, Login.this);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, msg.obj.toString(),
                                Toast.LENGTH_SHORT).show();
                    }

                    break;

            }
        }
    };

    @Override
    public void afterInitView() {

    }
}
