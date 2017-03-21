package com.ky3h.farmwork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.exceptions.HyphenateException;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.utils.ConstansBaoGao;



@SuppressLint("CutPasteId")
public class BaoGaoActivity extends BaseActivity implements OnClickListener {

    public static final int ZHAOSHI_GONG = 6;
    public static final int ZHAOSHI_JIAO = 5;
    public static final int ZHAOSHI_SHANG = 2;
    public static final int ZHAOSHI_ZHI = 4;
    public static final int ZHAOSHI_YU = 3;
    private TextView mBianshibaogaoButton;
    private TextView mTiaolijianyiButton;
    private ImageView mYueYaoTiaojingluo;
    private LinearLayout mBianshibaogaoLayout;//辨识报告 view
    private LinearLayout mTiaolijianyiLayout;//调理建议 view
    private TextView mBianShiBaoGaoTextView;
    private TextView mBianshibaogao_jiexi_text;
    private TextView mGongFaTiaoLiTextView;
    private TextView mLeTaoTiaoLiTextView;
    private TextView mShanShiTiaoLiTextView;
    private TextView mCiliaoTextView;
    private String subTypeIntent;
    private String mainTypeIntent;
    private int gongfa_index = -1;


    //------------------添加耳穴
    private TextView erxue_tiaoli_text1;
    private TextView erxue_tiaoli_text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void touchListener(View v) throws HyphenateException {
        switch (v.getId()) {
            case R.id.bianshibaogao://显示 辨识报告 view
                if (mBianshibaogaoLayout.getVisibility() == View.VISIBLE) {
                    return;
                }
                mBianshibaogaoLayout.setVisibility(View.VISIBLE);
                mTiaolijianyiLayout.setVisibility(View.GONE);

                mBianshibaogaoButton.setBackgroundResource(R.mipmap.baogao_right);
                mBianshibaogaoButton.setTextColor(getResources().getColor(R.color.blue));
                mTiaolijianyiButton.setBackgroundResource(R.mipmap.baogao_left);
                mTiaolijianyiButton.setTextColor(getResources().getColor(R.color.black));
                break;

            case R.id.tiaolijianyi://显示 调理建议 view
                if (mTiaolijianyiLayout.getVisibility() == View.VISIBLE) {
                    return;
                }
                mBianshibaogaoLayout.setVisibility(View.GONE);
                mTiaolijianyiLayout.setVisibility(View.VISIBLE);

                mBianshibaogaoButton.setBackgroundResource(R.mipmap.baogao_left);
                mBianshibaogaoButton.setTextColor(getResources().getColor(R.color.black));
                mTiaolijianyiButton.setBackgroundResource(R.mipmap.baogao_right);
                mTiaolijianyiButton.setTextColor(getResources().getColor(R.color.blue));
                break;
//            case R.id.gongfa_tiaoli_icon://功法调理
//                if (gongfa_index != -1) {
//                    Intent i = new Intent(BaoGaoActivity.this, GongFaActivity.class);
//                    i.putExtra("gongfa_index", gongfa_index);
//                    startActivity(i);
//                }
//                break;
//            case R.id.gongfatiaoli_text:
//                if (gongfa_index != -1) {
//                    Intent i = new Intent(BaoGaoActivity.this, GongFaActivity.class);
//                    i.putExtra("gongfa_index", gongfa_index);
//                    startActivity(i);
//                }
//                break;
//            case R.id.yueyao_tiaoli_icon://乐药调理
//                Intent intent = new Intent(BaoGaoActivity.this, YueYaoActivity.class);
//
//                intent.putExtra("baogaoType", subTypeIntent);
//                intent.putExtra("baogaoMainType", mainTypeIntent);
//                startActivity(intent);
//                break;
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_baogao);
    }

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {

        mBianshibaogaoButton = (TextView) findViewById(R.id.bianshibaogao);
        mTiaolijianyiButton = (TextView) findViewById(R.id.tiaolijianyi);

        mBianshibaogaoLayout = (LinearLayout) findViewById(R.id.bianshibaogao_layout);
        mTiaolijianyiLayout = (LinearLayout) findViewById(R.id.tiaolijianyi_layout);

        mBianShiBaoGaoTextView = (TextView) findViewById(R.id.bianshibaogao_text);
        mBianshibaogao_jiexi_text = (TextView) findViewById(R.id.bianshibaogao_jiexi_text);
        TextView mJingLuoTiaoLiTextView = (TextView) findViewById(R.id.jingluotiaoli_text);
        mJingLuoTiaoLiTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        mGongFaTiaoLiTextView = (TextView) findViewById(R.id.gongfatiaoli_text);
        mLeTaoTiaoLiTextView = (TextView) findViewById(R.id.leyaotiaoli_text);

        mShanShiTiaoLiTextView = (TextView) findViewById(R.id.shanshitiaoli_text);

        mCiliaoTextView = (TextView) findViewById(R.id.ciliaotiaoli_text);
        //ImageView mGongFaTiaoLiIcon = (ImageView) findViewById(R.id.gongfa_tiaoli_icon);
        // ImageView mYueYaoTiaoLiIcon = (ImageView) findViewById(R.id.yueyao_tiaoli_icon);
        mYueYaoTiaojingluo = (ImageView) findViewById(R.id.ciliao_jingluo);
    }

    @Override
    public void afterInitView() {
        //mGongFaTiaoLiIcon.setOnClickListener(this);
        // mYueYaoTiaoLiIcon.setOnClickListener(this);
        //mGongFaTiaoLiTextView.setOnClickListener(this);

        mBianshibaogaoButton.setOnClickListener(this);
        mTiaolijianyiButton.setOnClickListener(this);
        handleIntentAndView();

        //显示耳穴使用事项
        erxue_tiaoli_text1=(TextView)findViewById(R.id.erxue_tiaoli_text1);
        erxue_tiaoli_text2=(TextView)findViewById(R.id.erxue_tiaoli_text2);

        erxue_tiaoli_text1.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ErXue1);
        erxue_tiaoli_text2.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ErXue2);
    }

    private void handleIntentAndView() {
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("baogaoType") != null &&
                intent.getStringExtra("baogaoMainType") != null &&
                !((intent.getStringExtra("baogaoType")).equals("null")) &&
                !((intent.getStringExtra("baogaoMainType")).equals("null"))) {

            String baogaoType = intent.getStringExtra("baogaoType");
            String baogaoMainType = intent.getStringExtra("baogaoMainType");
            subTypeIntent = baogaoType;
            mainTypeIntent = baogaoMainType;


            if ("大宫".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_GONG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_DaGong);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_DaGong);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_DaGong);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_DaGong);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_DaGong);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_DaGong);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.dagong);
            } else if ("加宫".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_GONG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_JiaGong);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_JiaGong);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_JiaGong);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_JiaGong);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_JiaGong);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_JiaGong);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.jiagong);
            } else if ("上宫".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_GONG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShangGong);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShangGong);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShangGong);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShangGong);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShangGong);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShangGong);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.dagong);
            } else if ("少宫".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_GONG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShaoGong);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShaoGong);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShaoGong);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShaoGong);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShaoGong);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShaoGong);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shaogong);
            } else if ("左角宫".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_GONG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ZuoJiaoGong);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ZuoJiaoGong);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ZuoJiaoGong);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ZuoJiaoGong);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ZuoJiaoGong);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ZuoJiaoGong);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.zuojiaogong);
            } else if ("上商".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_SHANG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShangShang);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShangShang);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShangShang);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShangShang);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShangShang);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShangShang);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shangshang);
            } else if ("少商".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_SHANG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShaoShang);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShaoShang);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShaoShang);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShaoShang);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShaoShang);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShaoShang);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shaoshang);
            } else if ("钛商".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_SHANG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_TaiShang);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_TaiShang);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_TaiShang);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_TaiShang);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_TaiShang);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_TaiShang);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.taishang);
            } else if ("右商".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_SHANG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_YouShang);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_YouShang);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_YouShang);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_YouShang);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_YouShang);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_YouShang);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shangshang);
            } else if ("左商".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_SHANG;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ZuoShang);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ZuoShang);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ZuoShang);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ZuoShang);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ZuoShang);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ZuoShang);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.zuoshang);
            } else if ("大角".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_JIAO;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_DaJiao);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_DaJiao);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_DaJiao);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_DaJiao);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_DaJiao);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_DaJiao);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.dajiao);
            } else if ("判角".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_JIAO;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_PanJiao);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_PanJiao);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_PanJiao);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_PanJiao);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_PanJiao);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_PanJiao);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.panjiao);
            } else if ("上角".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_JIAO;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShangJiao);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShangJiao);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShangJiao);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShangJiao);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShangJiao);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShangJiao);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.dajiao);
            } else if ("少角".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_JIAO;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShaoJiao);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShaoJiao);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShaoJiao);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShaoJiao);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShaoJiao);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShaoJiao);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shaojiao);
            } else if ("钛角".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_JIAO;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_TaiJiao);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_TaiJiao);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_TaiJiao);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_TaiJiao);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_TaiJiao);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_TaiJiao);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.taijiao);
            } else if ("判徵".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_ZHI;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_PanZhi);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_PanZhi);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_PanZhi);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_PanZhi);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_PanZhi);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_PanZhi);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.panwei);
            } else if ("上徵".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_ZHI;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShangZhi);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShangZhi);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShangZhi);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShangZhi);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShangZhi);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShangZhi);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shangwei);
            } else if ("少徵".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_ZHI;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShaoZhi);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShaoZhi);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShaoZhi);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShaoZhi);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShaoZhi);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShaoZhi);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shaowei);
            } else if ("右徵".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_ZHI;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_YouZhi);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_YouZhi);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_YouZhi);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_YouZhi);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_YouZhi);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_YouZhi);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shangwei);
            } else if ("质徵".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_ZHI;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ZhiZhi);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ZhiZhi);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ZhiZhi);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ZhiZhi);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ZhiZhi);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ZhiZhi);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.zhiwei);
            } else if ("大羽".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_YU;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_DaYu);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_DaYu);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_DaYu);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_DaYu);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_DaYu);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_DaYu);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.dayu);
            } else if ("上羽".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_YU;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShangYu);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShangYu);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShangYu);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShangYu);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShangYu);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShangYu);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.dayu);
            } else if ("少羽".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_YU;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ShaoYu);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ShaoYu);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ShaoYu);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ShaoYu);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ShaoYu);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ShaoYu);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.shaoyu);
            } else if ("桎羽".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_YU;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ZhiYu);
                mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_ZhiYu);
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ZhiYu);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ZhiYu);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ZhiYu);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ZhiYu);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.zhiyu);
            } else if ("众羽".equals(baogaoType)) {
                gongfa_index = ZHAOSHI_YU;
                mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_ZhongYu);
                mBianShiBaoGaoTextView.setText((ConstansBaoGao.mBianShiBaoGaoSpanned_ZhongYu));
                mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_ZhongYu);
                mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_ZhongYu);
                mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_ZhongYu);
                mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_ZhongYu);
                mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.zhongyu);
            }
        } else {
            mShanShiTiaoLiTextView.setText(ConstansBaoGao.mShanShiTiaoLiSpanned_DaGong);
            mBianShiBaoGaoTextView.setText(ConstansBaoGao.mBianShiBaoGaoSpanned_DaGong);
            mBianshibaogao_jiexi_text.setText(ConstansBaoGao.mBianShiBaoGaoJiexi_DaGong);
            mGongFaTiaoLiTextView.setText(ConstansBaoGao.mGongFaTiaoLiSpanned_DaGong);
            mLeTaoTiaoLiTextView.setText(ConstansBaoGao.mLeTaoTiaoLiSpanned_DaGong);
            mCiliaoTextView.setText(ConstansBaoGao.mciliaoTiaoLiSpanned_DaGong);
            mYueYaoTiaojingluo.setBackgroundResource(R.mipmap.dagong);
        }
    }
}
