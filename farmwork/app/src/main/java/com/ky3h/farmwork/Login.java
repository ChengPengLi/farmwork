package com.ky3h.farmwork;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ky3h.farmwork.base.BaseActivity;

/**
 * Created by lipengcheng on 2016/6/1.
 * you are sb
 */
public class Login extends BaseActivity {
    private Button login, regist;
    private EditText account, password;
    private TextView forgetPass;
    private String accountNumber, passWordNumber;

    @Override
    public void touchListener(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                accountNumber = account.getText().toString();
                passWordNumber = password.getText().toString();
//                if (StringUtil.isNullOrEmpty(accountNumber)) {
//                    showShortToast("您的账号为空，请重新输入");
//                } else if (StringUtil.isNullOrEmpty(passWordNumber)) {
//                    showShortToast("您的密码为空，请重新输入");
//                } else {
//                    EMClient.getInstance().login(accountNumber,passWordNumber,new EMCallBack() {//回调
//                        @Override
//                        public void onSuccess() {
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    EMClient.getInstance().groupManager().loadAllGroups();
//                                    EMClient.getInstance().chatManager().loadAllConversations();
//                                    Log.d("main", "登录聊天服务器成功！");
//                                    Intent intent = new Intent(Login.this, MainActivity.class);
//                                    startActivity(intent);
//                                    SharedUtils.putString("account", accountNumber, Login.this);
//                                    SharedUtils.putString("password", passWordNumber, Login.this);
//                                    String zz = SharedUtils.getString("account", "", Login.this);
//                                    String pp = SharedUtils.getString("account", "", Login.this);
//                                    showShortToast(zz + "---" + pp);
//                                    finish();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onProgress(int progress, String status) {
//
//                        }
//
//                        @Override
//                        public void onError(int code, String message) {
//                            Log.d("main", "登录聊天服务器失败！");
//                        }
////                    });
//
//
//                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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


    }

    @Override
    public void afterInitView() {

    }
}
