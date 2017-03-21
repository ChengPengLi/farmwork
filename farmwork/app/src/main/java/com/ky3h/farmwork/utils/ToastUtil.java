package com.ky3h.farmwork.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unused")
public class ToastUtil {
    /**
     * After a time show a <code>Toast</code> again.
     *
     * @param toast <code>Toast</code>
     * @param cnt   Sequence
     */

    private static void execToast(final Toast toast, final int cnt) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                initToast(toast, cnt + 1);
            }

        }, 3000);
    }

    /**
     * Show the <code>Toast</code> and {#execToast}
     *
     * @param toast <code>Toast</code>
     * @param cnt   Sequence
     */
    private static void initToast(Toast toast, int cnt) {
        if (cnt <= 2) {
            toast.show();
            execToast(toast, cnt);
        }
    }

    /**
     * Show a <code>Toast</code> much longer than normal.
     *
     * @param info <code>String</code> that wants to show.
     */
    public static void invokeLongTimeToast(final Context context, final String info) {
        if (context != null && !TextUtils.isEmpty(info)) {
            Toast toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
            initToast(toast, 0);
        }
    }

    public static void invokeShortTimeToast(Context context, String info) {
        if (context != null && !TextUtils.isEmpty(info)) {
            Toast toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void invokeShortTimeToastBottom(Context context, String info) {
        if (context != null && !TextUtils.isEmpty(info)) {
            Toast toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
