package com.ky3h.farmwork.base;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ky3h.farmwork.Fragment_home;
import com.ky3h.farmwork.utils.ACache;

import com.loopj.android.http.RequestParams;


import java.io.File;


/**
 * 所有业务逻辑管理器的基类，用于提供一些公共的变量和方法 Created by Zhenyu on 2015/8/22.
 */
public class BaseManager {
    protected static final String METHOD_GET = "get";

    protected final String tag;

    protected final Handler mHandler;

    public BaseManager(Handler handler) {
        this.mHandler = handler;
        tag = this.getClass().getSimpleName();
    }

    /**
     * 创建本地文件存储需要的路径。
     *
     * @param filePath 本地文件的存储路径
     * @return boolean 路径创建成功返回true，失败返回false；
     */
    protected final boolean createPath(String filePath) {
        int cutPosition = filePath.lastIndexOf("/");
        String path = filePath.substring(0, cutPosition);
        File localPath = new File(path);

        return localPath.exists() || localPath.mkdirs();
    }




    protected void queryRemoteFailed() {
        Message message = Message.obtain();
        message.what = Fragment_home.MessageType.MESSAGE_QUERY_REMOTE_FAILED;
        mHandler.sendMessage(message);
    }

    //接口回调
    public interface OnSuccessListener {
        void onSuccess(String content);
    }
}
