package com.ky3h.farmwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * Created by l on 2016/12/21.
 * 辨识广播，用于更新在我的 未发出的文件上传成功之后，更新历史记录的图片，更换为有红点的图片
 */
public class BianShiBroadcast extends BroadcastReceiver{
    public static final int IS_BIANSHI=1100;//更新历史记录的图片，更换为有红点的图片

    private Handler handler;
    public BianShiBroadcast(Handler handler)
    {
        this.handler=handler;
    }

    //设置action
    public static final String ACTION="com.hxlm.health.voice.android.intent.action.BianShiBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {

        Message message=Message.obtain();
        message.what=IS_BIANSHI;
        handler.sendMessage(message);
    }
}
