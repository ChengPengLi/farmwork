package com.ky3h.farmwork.utils;

import android.text.TextUtils;
import android.util.Log;


import com.ky3h.farmwork.bean.DiagnosisResult;
import com.ky3h.farmwork.bean.Version;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtil {

    //Json解析登陆
    public static Object statusParser(String content, String key) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            JSONObject jo = new JSONObject(content);
            return jo.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object resultParser(String content, String key, String resultKey) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            JSONObject jo = new JSONObject(content);
            JSONObject result = jo.getJSONObject(key);
            Object obj = result.get(resultKey);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<DiagnosisResult> diagnosisListResultParser(String content, String key, String diagnosisKey) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        List<DiagnosisResult> diagnosisList = new ArrayList<DiagnosisResult>();
        try {
            JSONObject jo = new JSONObject(content);
            JSONArray result = jo.getJSONArray(key);//通过results得到music json数组

            for (int i = 0; i < result.length(); i++) {
                JSONObject res = result.getJSONObject(i);
                JSONArray jsonArray = res.getJSONArray(diagnosisKey);
                if (jsonArray.length() > 0) {
                    JSONObject object = jsonArray.getJSONObject(0);
                    DiagnosisResult diagnosisResult = new DiagnosisResult(object.getInt("id"), object.getLong("createDate"), object.getString("name"), object.getString("subject_sn"), object.getString("isRead"));
                    diagnosisList.add(diagnosisResult);
                }
            }
            return diagnosisList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Version versionResultParser(String content, String key) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            JSONObject jo = new JSONObject(content);
            JSONObject result = jo.getJSONObject(key);//通过results得到music json数组
            Version v = new Version();
            v.setIsUpdate(result.getBoolean("isUpdate"));
            v.setDownurl(result.getString("downurl"));
            if (Constant.DEBUG)
                Log.e("v", "v = " + v.getIsUpdate());
            return v;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
