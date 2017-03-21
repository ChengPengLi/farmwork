package com.ky3h.farmwork;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ky3h.farmwork.bean.User;
import com.ky3h.farmwork.netrequest.UserManager;
import com.ky3h.farmwork.utils.FileUtil;
import com.ky3h.farmwork.utils.HtmlUtils;
import com.ky3h.farmwork.utils.NoHttpUtil;
import com.ky3h.farmwork.utils.StringUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

import java.io.File;
import java.net.HttpCookie;

/**
 * Created by lipengcheng on 2016/6/2.
 */
public class Fragment_type extends Fragment implements View.OnClickListener {
    private Button jingluo;
    private Button tizhi;
    private Button zangfu;
    private Button wenjian;
    private RequestQueue requestQueue;
    private String str = "A094AD974B08E388087EB38CA2E65849";
    String json;
    String tizhiJson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_type, container, false);
        jingluo = (Button) view.findViewById(R.id.jingluo);
        tizhi = (Button) view.findViewById(R.id.tizhi);
        zangfu = (Button) view.findViewById(R.id.zangfu);
        wenjian = (Button) view.findViewById(R.id.wnjian);
        requestQueue = NoHttpUtil.getRequestQueue();
        jingluo.setOnClickListener(this);
        tizhi.setOnClickListener(this);
        zangfu.setOnClickListener(this);
        wenjian.setOnClickListener(this);
        return view;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String tizhiresult = (String) msg.obj;
                    Toast.makeText(getActivity(), tizhiresult, Toast.LENGTH_SHORT).show();
                    tizhiJson = HtmlUtils.getTextFromHtml(tizhiresult);
                    Toast.makeText(getActivity(), tizhiJson, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String result = (String) msg.obj;
                    if (!StringUtil.isNullOrEmpty(result)) {
                        json = HtmlUtils.getTextFromHtml(result);
                    }
                    Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };

    @Override
    public void onClick(View v) {
        Intent[] intent = {null};
        switch (v.getId()) {
            case R.id.jingluo:
                intent[0] = new Intent(getActivity(), OneSayActivity.class);
                startActivity(intent[0]);
                break;
            case R.id.tizhi:
                Request<String> tiZhirequest = NoHttp.createStringRequest("http://119.254.24.4:7006/result/zytzbsKL.jhtml?ywID=kunlun001&qdbs=kunlunapp&sign=A094AD974B08E388087EB38CA2E65849&str=1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0,1.0,2.0,3.0,2.0,5.0", RequestMethod.GET);

                NoHttpUtil.addLisenter(1, tiZhirequest, getActivity(), handler);

                break;
            case R.id.zangfu:
                break;
            case R.id.wnjian:

                Request<String> request = NoHttp.createStringRequest("http://119.254.24.4:7006/resultFileUpload/wenyinKL.jhtml?convert=convert&memberChildId=24", RequestMethod.POST);
//                Request<String> request = NoHttp.createStringRequest("http://10.1.71.45:8888/resultFileUpload/wenyinKL.jhtml?convert=convert&memberChildId=24", RequestMethod.POST);
                request.add("file", new File(FileUtil.getRootPath() + "/a.m4a"));
                Toast.makeText(getActivity(), FileUtil.getRootPath() + "/a.m4a", Toast.LENGTH_SHORT).show();
                User user = UserManager.getLoginedUser();
                request.add("qdbs", "kunlunapp");
                request.add("ywID", "kunlun001");
                request.add("sign", str);
                request.addHeader(new HttpCookie("token", user.getToken()));

                NoHttpUtil.addLisenter(2, request, getActivity(), handler);

                break;
            default:
                break;


        }
    }
}
