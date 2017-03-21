package com.ky3h.farmwork.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ky3h.farmwork.R;


/**
 * 用于确认用户的意图
 * Created by Zhenyu on 2015/8/16.
 */
public class ConfirmDialog extends Dialog {
    private View.OnClickListener cancelListener;
    private View.OnClickListener okListener;
    private int textResourceId;
    private String textPrice;
    private boolean isCancleDisabled =false;

    public ConfirmDialog(Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tijian_again_dialog);

        final TextView text = (TextView) findViewById(R.id.text);
        text.setText(textResourceId);

        final View closeView = findViewById(R.id.close);
        closeView.setVisibility(View.GONE);
        
        final TextView price = (TextView) findViewById(R.id.price);
        price.setText(textPrice);

        final ImageView cancel = (ImageView) findViewById(R.id.duihuan);
        if(!isCancleDisabled) {
            cancel.setVisibility(View.VISIBLE);
            cancel.setImageResource(R.mipmap.an_fanhui1);
            cancel.setOnClickListener(cancelListener);
        }else {
            cancel.setVisibility(View.GONE);
        }

        final ImageView ok = (ImageView) findViewById(R.id.goumai);
        ok.setImageResource(R.mipmap.an_queding1);
        ok.setOnClickListener(okListener);
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setOkListener(View.OnClickListener okListener) {
        this.okListener = okListener;
    }

    public void setTextResourceId(int textResourceId) {
        this.textResourceId = textResourceId;
    }
    public void setTextPrice(String textPrice) {
        this.textPrice = textPrice;
    }

    public boolean isCancleDisabled() {
        return isCancleDisabled;
    }

    public void setCancleDisabled(boolean isDisableCancle) {
        this.isCancleDisabled = isDisableCancle;
    }
}
