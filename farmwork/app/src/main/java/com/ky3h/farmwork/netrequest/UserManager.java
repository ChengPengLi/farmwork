package com.ky3h.farmwork.netrequest;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.ky3h.farmwork.bean.User;
import com.ky3h.farmwork.utils.Constants;
import com.ky3h.farmwork.utils.NoHttpUtil;
import com.ky3h.farmwork.utils.ResponseStatus;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lipengcheng on 2016/8/16.
 * you are sb
 */
public class UserManager {
    private Handler handler;
    private static final int NOHTTP_WHAT_LOGIN = 0x001;
    public static final int LOGIN_FAIL = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_NETWORK_ERROR = 2;
    private User loginedUser;

    public UserManager(Handler handler) {
        this.handler = handler;
    }

    public void LoginFromNohttp(final String username, String password, RequestQueue requestQueue) {
        Request<String> request = NoHttp.createStringRequest(Constants.LOGIN_URL, RequestMethod.POST);
        request.add("username", username);
        request.add("password", password);
        request.setConnectTimeout(NoHttp.getDefaultConnectTimeout());
        request.setReadTimeout(NoHttp.getDefaultReadTimeout());
        request.addHeader("version", "erzhen_android-yh-" + NoHttpUtil.getVersion());
        // 设置一个tag, 在请求完(失败/成功)时原封不动返回; 多数情况下不需要。
        request.setTag(this);
        // 设置一个tag, 在请求完(失败/成功)时原封不动返回; 多数情况下不需要。
        request.setTag(this);
        requestQueue.add(NOHTTP_WHAT_LOGIN, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (what == NOHTTP_WHAT_LOGIN) {
                    String result = response.get();

                        JSONObject jo = getReturnJSONObject(result);

                        if (jo != null) {
                            loginedUser = new User();
                            try {
                                int status = jo.getInt("status");
                                JSONObject data = jo.getJSONObject("data");
                                String token = data.getString("token");
                                String JSessionId = data
                                        .getString("JSESSIONID");

                                String cookie = "token=" + token
                                        + ";JSESSIONID=" + JSessionId;
                                loginedUser.setToken(token);
                                loginedUser.setJSESSIONID(JSessionId);

                                JSONObject member = data
                                        .getJSONObject("member");

                                loginedUser.setId(member.getInt("id"));
                                loginedUser.setUserName(member
                                        .getString("username"));
                                loginedUser.setName(getValue(member
                                        .getString("name")));
                                loginedUser.setGender(getValue(member
                                        .getString("gender")));
                                loginedUser.setAddress(getValue(member
                                        .getString("address")));
                                loginedUser.setPhone(getValue(member
                                        .getString("phone")));
                                loginedUser.setMemberImage(getValue(member
                                        .getString("memberImage")));
                                loginedUser.setIdentityType(getValue(member
                                        .getString("identityType")));
                                loginedUser.setIdNumber(getValue(member
                                        .getString("idNumber")));
                                loginedUser.setIsMedicare(getValue(member
                                        .getString("isMedicare")));
                                loginedUser.setBirthDate(getValue(member
                                        .getString("birthday")));

                                JSONArray memberChildArray = member
                                        .getJSONArray("mengberchild");

                                for (int i = 0; i < memberChildArray.length(); i++) {
                                    String mobile = memberChildArray
                                            .getJSONObject(i).getString(
                                                    "mobile");
                                    // loginedUser.setUserName(mobile);

                                    if (!TextUtils.isEmpty(mobile)
                                            && mobile.equals(username)) {
                                        loginedUser
                                                .setMemberChildId(memberChildArray
                                                        .getJSONObject(i)
                                                        .getInt("id"));
                                    }
                                }

//								mHandler.obtainMessage(LOGIN_SUCCESS)
//										.sendToTarget();
                                Message message = Message.obtain();
                                message.what = UserManager.LOGIN_SUCCESS;
                                message.arg1 = status;
                                message.obj = data;
                                handler.sendMessage(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                }
            }

            @Override
            public void onFailed(int i, String s, Object o, Exception e, int i1, long l) {
                handler.obtainMessage(LOGIN_NETWORK_ERROR).sendToTarget();
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }
    private String getValue(String string) {
        if (!TextUtils.isEmpty(string) && !"null".equals(string)) {
            return string;
        }
        return null;
    }
    protected final JSONObject getReturnJSONObject(String response) {
        try {
            JSONObject jo = new JSONObject(response);
            int status = jo.getInt("status");

            if (status == ResponseStatus.SUCCESS) {
                return jo;
            } else {

                return null;
            }
        } catch (JSONException e) {

        }
        return null;
    }
}

