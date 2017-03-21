package com.ky3h.farmwork;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.hyphenate.exceptions.HyphenateException;
import com.ky3h.farmwork.base.BaseActivity;
import com.ky3h.farmwork.bean.Version;
import com.ky3h.farmwork.netrequest.BianShiManager;
import com.ky3h.farmwork.netrequest.UpdateManager;
import com.ky3h.farmwork.utils.Constant;
import com.ky3h.farmwork.widget.ConfirmDialog;

import static com.ky3h.farmwork.BianShiBroadcast.ACTION;


/**
 * 辨识
 */
public class BianShiActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "BianShiActivity";
    private static final int ZI_CHANGE_INTERVAL = 2000;//每隔2秒更新一次
    private Runnable runnable;

    private ImageView bianshiMainImg;
    private ImageView bianshiPinyinImg;
    private ImageView bianshiDemo;//历史记录
    private ImageView mStartButton;// 开始辨识
    private boolean isStarting = false;//是否已经开始

    private AnimationDrawable mainAnim;

    private ImageView[] ziImg;
    private int[] mainAnimRidArray;
    private int[] pinyinImgRidArray;
    private int[] ziImgDefaultRidArray;
    private int[] ziImgFocusRidArray;

    private int ziAnimState = 0;

    private BianShiManager bianShiManager;


    private BianShiBroadcast bianShiBroadcast = null;//辨识广播

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //开始录音
                case BianShiManager.START_RECORD: {
                    msg.arg1 = 0;

                    changeZiAnim();
                    break;
                }

                //初始化录音文件失败
                case BianShiManager.INIT_RECORDER_FAIL: {
                    Toast.makeText(BianShiActivity.this,
                            getString(R.string.init_recorder_fail),
                            Toast.LENGTH_LONG).show();
                    resetUI();
                    break;
                }

                case BianShiManager.FILE_UPLOAD_FAIL:
                    //bianshiDemo.setClickable(true);
                    dismissLoadingDialog();
                    Toast.makeText(BianShiActivity.this,
                            getString(R.string.file_upload_network_error),
                            Toast.LENGTH_SHORT).show();
                    break;

                case BianShiManager.FILE_UPLOAD_SUCCESS:
                    //bianshiDemo.setClickable(true);
                    dismissLoadingDialog();
                    Toast.makeText(BianShiActivity.this,
                            getString(R.string.file_upload_success),
                            Toast.LENGTH_LONG).show();

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bianshiDemo.setImageResource(R.mipmap.wenyin_history_record_dian);
                        }
                    }, 5 * 1000); //5秒之后历史记录显示有消息

                    //mBaogao.setImageResource(R.drawable.tab_baogao1_1);
                    bianShiManager.clearVoiceFile();
                    break;

                case BianShiManager.NO_VOICE:
                    //bianshiDemo.setClickable(true);
                    Toast.makeText(BianShiActivity.this,
                            getString(R.string.no_voice), Toast.LENGTH_LONG).show();
                    bianShiManager.stopVoiceRecord();
                    resetUI();
                    bianShiManager.clearVoiceFile();
                    break;

                case UpdateManager.HAVE_NEW_VERSION:
                    Version version = (Version) msg.obj;
                    initDownloadNewVersionDialog(Uri.parse(version.getDownurl()));
                    break;

                //辨识广播，用于更新在我的 未发出的文件上传成功之后，更新历史记录的图片，更换为有红点的图片
                case BianShiBroadcast.IS_BIANSHI:
                    bianshiDemo.setImageResource(R.mipmap.wenyin_history_record_dian);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void afterInitView() {

        mainAnimRidArray = new int[5];
        mainAnimRidArray[0] = R.drawable.bianshi_main_duo_anim;
        mainAnimRidArray[1] = R.drawable.bianshi_main_ruai_anim;
        mainAnimRidArray[2] = R.drawable.bianshi_main_mi_anim;
        mainAnimRidArray[3] = R.drawable.bianshi_main_sao_anim;
        mainAnimRidArray[4] = R.drawable.bianshi_main_la_anim;

        pinyinImgRidArray = new int[5];
        pinyinImgRidArray[0] = R.mipmap.duo_03;
        pinyinImgRidArray[1] = R.mipmap.ruai_03;
        pinyinImgRidArray[2] = R.mipmap.mi_03;
        pinyinImgRidArray[3] = R.mipmap.sao_03;
        pinyinImgRidArray[4] = R.mipmap.la_03;

        ziImgDefaultRidArray = new int[5];
        ziImgDefaultRidArray[0] = R.mipmap.sa_03;
        ziImgDefaultRidArray[1] = R.mipmap.sa_05;
        ziImgDefaultRidArray[2] = R.mipmap.sa_07;
        ziImgDefaultRidArray[3] = R.mipmap.sa_09;
        ziImgDefaultRidArray[4] = R.mipmap.sa_11;

        ziImgFocusRidArray = new int[5];
        ziImgFocusRidArray[0] = R.mipmap.s_03;
        ziImgFocusRidArray[1] = R.mipmap.s_05;
        ziImgFocusRidArray[2] = R.mipmap.s_07;
        ziImgFocusRidArray[3] = R.mipmap.s_09;
        ziImgFocusRidArray[4] = R.mipmap.s_11;

        bianShiManager = new BianShiManager(mHandler);
//        UpdateManager updateManager = new UpdateManager(mHandler,
//                BianShiActivity.this);
//        updateManager.isUpdate(BianShiActivity.this);

        // 动态注册广播 防止重复注册多个广播 跳转首页
        if (bianShiBroadcast == null) {
            bianShiBroadcast = new BianShiBroadcast(mHandler);
            registerReceiver(bianShiBroadcast, new IntentFilter(
                    ACTION));
        }
    }



    @Override
    public void touchListener(View v) throws HyphenateException {
        switch (v.getId()) {
            case R.id.record_start:// 开始辨识
                //档录音开始运行
                if (isStarting == true) {
                    //按钮可以点击
                    if (mStartButton.isEnabled()) {
                        bianShiManager.setUseVoiceAnalysis(false);

                        bianShiManager.stopVoiceRecord();
                        bianShiManager.clearVoiceFile();

                        //bianshiDemo.setClickable(true);
                        resetUI();//跟新UI
                        isStarting = false;
                    } else {
                        Log.i(TAG, "不可点击");
                    }

                } else {
                    Log.i(TAG, "开始录音");
                    bianShiManager.setUseVoiceAnalysis(true);

                    mStartButton.setEnabled(false);//不可点击
                    // 录音
                    bianShiManager.startVoiceRecording();
                }
                break;

            //历史记录
            case R.id.bianshi_demo:
                bianshiDemo.setImageResource(R.mipmap.wenyin_history_record);
                Intent intent = new Intent(this, BaoGaoResultListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bianshi);
    }

    @Override
    public void dealLogicBeforeInitView() {

    }

    @Override
    public void initView() {
        bianshiDemo = (ImageView) findViewById(R.id.bianshi_demo);

        ziImg = new ImageView[5];
        ziImg[0] = (ImageView) findViewById(R.id.zi_img1);
        ziImg[1] = (ImageView) findViewById(R.id.zi_img2);
        ziImg[2] = (ImageView) findViewById(R.id.zi_img3);
        ziImg[3] = (ImageView) findViewById(R.id.zi_img4);
        ziImg[4] = (ImageView) findViewById(R.id.zi_img5);

        bianshiMainImg = (ImageView) findViewById(R.id.bianshi_main_img);
        bianshiPinyinImg = (ImageView) findViewById(R.id.bianshi_pinyin_img);
        mStartButton = (ImageView) findViewById(R.id.record_start);

        bianshiDemo.setOnClickListener(this);

        mStartButton.setOnClickListener(this);
    }

    private void changeZiAnim() {
        if (mainAnim != null) {
            mainAnim.stop();
        }
        if (ziAnimState >= ziImg.length) {
            //bianshiDemo.setClickable(true);
            bianShiManager.stopVoiceRecord();
            resetUI();//更新UI
            isStarting = false;//未开始录音


            if (bianShiManager.isRecordSuccess()) {

                if (bianShiManager.startUploadVoiceFile()) {
                    showLoadingDialog();
                    Toast.makeText(BianShiActivity.this,
                            getString(R.string.file_uploading),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BianShiActivity.this,
                            getString(R.string.file_no_found),
                            Toast.LENGTH_LONG).show();
                }
            } else {
                bianShiManager.clearVoiceFile();
                Toast.makeText(BianShiActivity.this,
                        "未采集到有效声音，请录制有效声音",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            Log.d("Lo.i", "ziAnimState = " + ziAnimState);
            ziImg[ziAnimState]
                    .setImageResource(ziImgFocusRidArray[ziAnimState]);

            if (ziAnimState > 0) {
                ziImg[ziAnimState - 1]
                        .setImageResource(ziImgDefaultRidArray[ziAnimState - 1]);
            }

            bianshiMainImg.setImageResource(mainAnimRidArray[ziAnimState]);
            mainAnim = (AnimationDrawable) bianshiMainImg.getDrawable();
            mainAnim.start();

            bianshiPinyinImg.setImageResource(pinyinImgRidArray[ziAnimState]);

            ziAnimState++;

            if (ziAnimState == 2) {
                isStarting = true;//开始运行
                mStartButton.setEnabled(true);//可以点击

                //bianshiDemo.setClickable(false);//演示不可点击
                mStartButton.setImageResource(R.mipmap.wenyin_stopbianshi);//显示停止
            }

            final int[] num = {0};

            runnable = new Runnable() {
                @Override
                public void run() {
                    num[0]++;
                    Log.i(TAG, "num-->" + num[0]);
                    //此处添加每个一段时间要执行的语句
                    BianShiActivity.this.changeZiAnim();
                }
            };

            //启动计时器
            mHandler.postDelayed(runnable, ZI_CHANGE_INTERVAL);
        }
    }

    private void resetUI() {
        Log.i(TAG, "更新的UI页面");

        if (runnable != null) {
            Log.i(TAG, "停止动画的播放runnable");
            mHandler.removeCallbacks(runnable);
            runnable = null;
        }

        if (mainAnim != null) {
            Log.i(TAG, "停止动画的播放mainAnim");
            mainAnim.stop();
        }

        for (int i = 0; i < ziImg.length; i++) {
            ziImg[i].setImageResource(ziImgDefaultRidArray[i]);
        }
        bianshiMainImg.setImageResource(R.mipmap.wenyin_dou1);

        ziAnimState = 0;

        bianshiPinyinImg.setImageResource(pinyinImgRidArray[0]);
        mStartButton.setImageResource(R.mipmap.wenyin_kaishibianshi);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bianShiManager.stopVoiceRecord();
        // 销毁辨识广播
        if (bianShiBroadcast != null) {
            unregisterReceiver(bianShiBroadcast);
            bianShiBroadcast = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        bianShiManager.stopVoiceRecord();
        resetUI();
        super.onPause();
    }

    private void initDownloadNewVersionDialog(final Uri uri) {
        // 初始化确认下载的Dialog
        final ConfirmDialog dialog = new ConfirmDialog(this);

        dialog.setTextResourceId(R.string.new_version);

        dialog.setCancelListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确认
        dialog.setOkListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.DEBUG)
                    Log.d(TAG, "Begin to download the new version app.");

                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}
