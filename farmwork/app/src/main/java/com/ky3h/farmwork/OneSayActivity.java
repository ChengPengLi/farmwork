package com.ky3h.farmwork;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hyphenate.exceptions.HyphenateException;
import com.ky3h.farmwork.base.BaseActivity;

/**
 * Created by l on 2016/11/6.
 * 一说
 */
public class OneSayActivity extends BaseActivity {

    private ImageView img_say;
    private Toolbar toolbar;

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("经络状态评估");
        img_say = (ImageView) findViewById(R.id.img_say);

    }

    @Override
    public void afterInitView() {
        img_say.setOnClickListener(this);
    }

    @Override
    public void touchListener(View v) throws HyphenateException {
        Intent intent = new Intent(OneSayActivity.this, BianShiActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.layput_one_say);
    }


}