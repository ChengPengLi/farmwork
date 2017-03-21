package com.ky3h.farmwork.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.ky3h.farmwork.R;
import com.ky3h.farmwork.application.Application;


public class DialogUtils {
    public static WaitingDialog displayWaitDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.progress_wait_dialog, null);
        final WaitingDialog dialog = new WaitingDialog(context, R.style.dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        return dialog;
    }

    static class WaitingDialog extends Dialog {

        public WaitingDialog(Context context, int theme) {
            super(context, theme);
        }


        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
        }
    }

    // 设置dialog为全屏
    public static void setDialogFull(Dialog dialog) {
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = Application.screenHeight; // 高度设置为屏幕的高度
        p.width = Application.screenWidth; // 宽度设置为全屏
        dialog.getWindow().setAttributes(p); // 设置生效
    }
}
