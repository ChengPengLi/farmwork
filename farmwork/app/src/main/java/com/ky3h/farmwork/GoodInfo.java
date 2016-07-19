package com.ky3h.farmwork;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hyphenate.exceptions.HyphenateException;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.bean.Good;

/**
 * Created by lipengcheng on 2016/6/29.
 * you are sb
 */
public class GoodInfo extends BaseActivity {
    private Good good;

    @Override
    public void touchListener(View v) throws HyphenateException {

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.goodinfo);
    }

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        good = (Good) intent.getSerializableExtra("GoodInfo");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(good.getGoodName());
        CollapsingToolbarLayout coordinatorLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout.setTitle(good.getGoodName());
        loadBackdrop();
    }

    @Override
    public void afterInitView() {

    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(good.getGoodResId()).centerCrop().into(imageView);
    }
}
