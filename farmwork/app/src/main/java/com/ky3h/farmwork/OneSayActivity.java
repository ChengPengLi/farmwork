package com.ky3h.farmwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.hcy.ky3h.R;
import com.hxlm.android.hcy.AbstractBaseActivity;
import com.hxlm.android.hcy.view.TitleBarView;
import com.hxlm.android.hcy.voicediagnosis.BianShiActivity;

/**
 * Created by l on 2016/11/6.
 * 一说
 */
public class OneSayActivity extends AbstractBaseActivity implements View.OnClickListener{

    private ImageView img_say;

    @Override
    protected void setContentView() {
        setContentView(R.layout.layput_one_say);
    }

    @Override
    protected void initViews() {
        TitleBarView titleBar = new TitleBarView();
        titleBar.init(this, "经络状态评估", titleBar, 1);
        img_say=(ImageView)findViewById(R.id.img_say);
        img_say.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
       Intent intent = new Intent(OneSayActivity.this, BianShiActivity.class);
        startActivity(intent);
        this.finish();
    }
}