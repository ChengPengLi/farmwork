package com.ky3h.farmwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.hyphenate.exceptions.HyphenateException;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.bean.DiagnosisResult;
import com.ky3h.farmwork.netrequest.UserManager;
import com.ky3h.farmwork.utils.AsyncHttpClientUtil;
import com.ky3h.farmwork.utils.Constant;
import com.ky3h.farmwork.utils.JsonUtil;
import com.ky3h.farmwork.widget.ConfirmDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class BaoGaoResultListActivity extends BaseActivity implements
        ListView.OnScrollListener {
    public static final String BAOGAONEW = "baogaonew_";
    private static final int MESSAGE_GET_QUESTIONNAIRE = 20;
    private static final int GET_ITEMS_LIST_EMPTY = 10;
    private static final String TAG = "BaoGaoResultListActivity";
    private static final int GET_ITEMS_LIST_FAIL = 0;
    private static final int POST_ITEMS_LIST_ISREAD = 1;
    private static final int GET_ITEMS_LIST_OUTTIME = 2;
    private static boolean mStillHaveMore = false;
    List<DiagnosisResult> diagnosisResults = new ArrayList<DiagnosisResult>();
    DiagnosisResult diagnosisResult;
    boolean hasIcon = true;

    private ListView mListView;
    private ResultAdapter mAdapter;
    private TextView mNoDataTipText;

    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_GET_QUESTIONNAIRE:
                    if (msg.arg1 == Constant.API_SUCCESS_CODE) {
                        diagnosisResults = (List<DiagnosisResult>) msg.obj;
                    }
                    mAdapter = new ResultAdapter(BaoGaoResultListActivity.this);
                    mAdapter.setDatas(diagnosisResults);
                    mListView.setAdapter(mAdapter);
                    break;
                case GET_ITEMS_LIST_FAIL:
                    //查询失败
                    mNoDataTipText.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    mNoDataTipText.setText("报告查询失败，请重试");
                    break;
                case POST_ITEMS_LIST_ISREAD:
                    diagnosisResult = (DiagnosisResult) msg.obj;
                    notifyRead(diagnosisResult);
                    hasIcon = false;
                    break;
                case GET_ITEMS_LIST_EMPTY:
                	mNoDataTipText.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    mNoDataTipText.setText("没有报告内容");
                	break;
                case GET_ITEMS_LIST_OUTTIME:
                	//登录超时
                    mNoDataTipText.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    mNoDataTipText.setText("登录超时，请重新登录");

                	break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_baogao_list);
        mListView = (ListView) findViewById(R.id.result_list);
        mNoDataTipText = (TextView) findViewById(R.id.no_data_tip);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DiagnosisResult diagnosisResult = mAdapter.getItem(position);

                Intent intent;
                if (diagnosisResult != null) {

                    if (isNew(diagnosisResult)) {
                        changeNewState(diagnosisResult);
                    }
                    intent = new Intent(BaoGaoResultListActivity.this,
                            BaoGaoActivity.class);
                    if (diagnosisResult.getSubject_sn() == null
                            || "null".equals(diagnosisResult.getSubject_sn())
                            || diagnosisResult.getName() == null
                            || "null".equals(diagnosisResult
                            .getName())) {

                        final ConfirmDialog dialog = new ConfirmDialog(BaoGaoResultListActivity.this);

                        dialog.setTextResourceId(R.string.no_complete_report);

                        dialog.setCancelListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        // 确认
                        dialog.setOkListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(BaoGaoResultListActivity.this, BianShiActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                        mAdapter.notifyDataSetChanged();
                    } else {
                        intent.putExtra("baogaoType", diagnosisResult.getSubject_sn());
                        intent.putExtra("baogaoMainType", diagnosisResult.getName());
                        startActivity(intent);
                    }
                }

                mAdapter.notifyDataSetChanged();// 改变new的状态
            }

        });

        initAndStartBaoGaoDataRequestByNotify();
        mListView.setOnScrollListener(this);
    }

    @Override
    public void touchListener(View v) throws HyphenateException {

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void afterInitView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mTabBarView == null) {
//            mTabBarView = new TabBarView();
//            mTabBarView.init(this, TabBarView.TYPE_BAO_GAO);
//        } else {
//            mTabBarView.addMessageListener();
//        }

//        mTitleView.setRightButtonOnclickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BaoGaoResultListActivity.this,
//                        ShiYongShuoMingActivity.class);
//                intent.putExtra(ShiYongShuoMingActivity.SHUOMING_KEY,
//                        ShiYongShuoMingActivity.SHUOMING_TYPE_BAOGAO);
//                startActivity(intent);
//            }
//        }, true);
        initAndStartBaoGaoDataRequestByNotify();
    }

    private void notifyRead(DiagnosisResult diagnosisResult2) {
        String token = UserManager.getLoginedUser().getToken();
        int id = diagnosisResult2.getId();
        String url = "/member/myreport/updateIsRead.jhtml";
        Map<String, String> params = new HashMap<String, String>();
        RequestParams params2 = new RequestParams(params);
        params2.put("id", id);
        params2.put("isRead", "true");
        params2.put("token", token);
        updateIsRead(Constant.BASE_URL + url, params2);
    }

    private void updateIsRead(String url, RequestParams params2) {
        AsyncHttpClient mAsyncClient = AsyncHttpClientUtil.getClient();
        mAsyncClient.post(url, params2, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String response) {

            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable e) {
                if (Constant.DEBUG)
                    e.printStackTrace();
            }
        });
    }

    private void initAndStartBaoGaoDataRequestByNotify() {
        int memberChildId = UserManager.getLoginedUser().getMemberChildId();
        String token = UserManager.getLoginedUser().getToken();
        if (Constant.DEBUG)
            Log.d("memberChildId", "memberChildId = " + memberChildId);

        String uri = "/member/myreport/list/" + "JLBS" + "/" + memberChildId
                + ".jhtml";

        RequestParams params = new RequestParams(new HashMap<String, String>());
        params.put("token", token);
        params.put("time", Long.toString(System.currentTimeMillis()));

        getDiagnosisResultListHttpRequestByNotify(Constant.BASE_URL + uri, params);

    }

    private void getDiagnosisResultListHttpRequestByNotify(String url, RequestParams params2) {
        mNoDataTipText.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);

        showLoadingDialog();

        AsyncHttpClient mAsyncClient = AsyncHttpClientUtil.getClient();
        mAsyncClient.get(url, params2, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2,
                                  Throwable error) {
                handler.sendEmptyMessage(GET_ITEMS_LIST_FAIL);
                dismissLoadingDialog();
                if (Constant.DEBUG)
                    error.printStackTrace();

            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, String response) {
                if (Constant.DEBUG)
                    Log.e("response", "response = " + response);
                // 登陆成功,登录成功后跳转
                dismissLoadingDialog();

                if (response != null && !("".equals(response))) {
                    String errorCaseString = "Failed to query diagnosis results";
                    // 此两种情况排除
                    if (response
                            .startsWith("{\"status\":30,\"results\":{\"error\":")
                            && response.contains(errorCaseString)) {
                        return;
                    }
                }
                int status = -1;
                if (JsonUtil.statusParser(response, "status") instanceof Integer) {
                    status = (Integer) JsonUtil
                            .statusParser(response, "status");
                }

                List<DiagnosisResult> diagnosisResult = new ArrayList<DiagnosisResult>();
                if (status == Constant.API_SUCCESS_CODE) {
                    try {
                        JSONObject jo = new JSONObject(response);
                        JSONArray ja_data = jo.getJSONArray("data");
                        if(ja_data.length() == 0){
                        	handler.sendEmptyMessage(GET_ITEMS_LIST_EMPTY);
                        }
                        for (int i = 0; i < ja_data.length(); i++) {
                            JSONObject res = ja_data.getJSONObject(i);
                            JSONObject jo_subject = res.getJSONObject("subject");
//						JSONObject jo_id = res.getJSONObject("id");
                            DiagnosisResult resource = new DiagnosisResult(
                                    res.getInt("id"),
                                    res.getLong("createDate"),
                                    jo_subject.getString("name"),
                                    jo_subject.getString("subject_sn"),
                                    res.getString("isRead"));
                            diagnosisResult.add(resource);
                            if (Constant.DEBUG)
                                Log.i("resource", "resource = " + resource);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        handler.sendEmptyMessage(GET_ITEMS_LIST_FAIL);
                        if (Constant.DEBUG)
                            e.printStackTrace();
                    }
                } else if(status == Constant.API_UNLOGIN) {
                    handler.sendEmptyMessage(GET_ITEMS_LIST_OUTTIME);
                }

                // 访问了接口，则所有历史报告就变成了所有报告，再次，将此list中的分析result存到share中，做红色标志位
                if (diagnosisResult.size() > 0) {
                    for (DiagnosisResult diagnosisResult1 : diagnosisResult) {
                        if (diagnosisResult1 != null) {
                            //if(isNew(diagnosisResult1))
                            {
                                String baogaoNewKey = BAOGAONEW 
                                        + diagnosisResult1.getId();
                                if (Constant.DEBUG)
                                    Log.e("r", "diagnosisResult.size " + baogaoNewKey);
                                saveLocalNewShared(baogaoNewKey);
                            }
                        }
                    }
                }

                Message message = Message.obtain();
                message.what = MESSAGE_GET_QUESTIONNAIRE;
                message.arg1 = status;
                message.obj = diagnosisResult;
                handler.sendMessage(message);
            }
        });
    }

    private void saveLocalNewShared(String newKey) {
        String account = UserManager.getLoginedUser().getUserName();
        final SharedPreferences shared = getSharedPreferences(
                "voice_diagnosis_setting" + account, 0);
        if (!shared.contains(newKey)) {
            shared.edit().putBoolean(newKey, true).apply();
        }
    }

    private boolean isNew(DiagnosisResult diagnosisResult) {

        return diagnosisResult.getIsRead().equals("false") && !diagnosisResult.getIsRead().equals("") && !TextUtils.isEmpty(diagnosisResult.getIsRead());
    }

    private void changeNewState(DiagnosisResult diagnosisResult) {
        Message message = Message.obtain();
        message.obj = diagnosisResult;
        message.what = POST_ITEMS_LIST_ISREAD;
        handler.sendMessage(message);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        int totalHisResultCount = 1;

        if (mAdapter != null && mAdapter.getDatas() != null) {
            mStillHaveMore = mAdapter.getDatas().size() < totalHisResultCount;
        }

        if (mStillHaveMore && firstVisibleItem > 0
                && firstVisibleItem + visibleItemCount == totalItemCount) {
            // initAndStartBaoGaoDataRequestByHistory(mPageNo);
        }
    }

    private static class ViewHolder {
        public TextView tview;
        public ImageView mImageView;
        public TextView mDate;
    }

    private class ResultAdapter extends BaseAdapter {

        private List<DiagnosisResult> mDatas;
        private LayoutInflater mInflater;

        public ResultAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public List<DiagnosisResult> getDatas() {
            return this.mDatas;
        }

        public void setDatas(List<DiagnosisResult> datas) {
            this.mDatas = datas;
        }

        @Override
        public int getCount() {
            if (mDatas == null) {
                return 0;
            } else {
                return mDatas.size();
            }
        }

        @Override
        public DiagnosisResult getItem(int position) {
            if (mDatas == null) {
                return null;
            } else {
                return mDatas.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_baogao_list_item, parent, false);
                holder = new ViewHolder();
                holder.tview = (TextView) convertView.findViewById(R.id.text);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.imageview);
                holder.mDate = (TextView) convertView.findViewById(R.id.date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DiagnosisResult diagnosisResult = getItem(position);
            long resultTime = diagnosisResult.getCreateDate();//报告时间
            if (resultTime == 0) {
                resultTime = System.currentTimeMillis();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = dateFormat.format(resultTime);
            holder.mDate.setText(dateString);
            String baogaoType = diagnosisResult.getSubject_sn();
            if ((baogaoType == null) || ("".equals(baogaoType))
                    || ("null".equals(baogaoType))) {
                baogaoType = "未知";
            }
            holder.mImageView.setImageResource(R.mipmap.report_new);

            if (isNew(diagnosisResult)) {
                holder.mImageView.setVisibility(View.VISIBLE);
                holder.tview.setText(baogaoType);
            } else {
                holder.mImageView.setVisibility(View.INVISIBLE);
                holder.tview.setText(baogaoType);
            }
            return convertView;
        }
    }

}