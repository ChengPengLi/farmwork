package com.ky3h.farmwork.netrequest;

import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ky3h.farmwork.base.BaseManager;
import com.ky3h.farmwork.utils.AsyncHttpClientUtil;
import com.ky3h.farmwork.utils.Constant;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 提供闻音辨识基本功能 Created by Zhenyu on 2015/8/17.
 */
public class BianShiManager extends BaseManager {
    private static final String TAG = "BianShiActivity";
    public static final int START_RECORD = 0;
    public static final int INIT_RECORDER_FAIL = 1;
    public static final int CHANGE_ZI_ANIM = 2;
    public static final int FILE_UPLOAD_FAIL = 3;
    public static final int FILE_UPLOAD_SUCCESS = 4;
    public static final int GET_RESULT_FAIL = 5;
    public static final int GET_RESULT_SUCCESS = 6;
    public static final int NO_VOICE = 7;
    public static final int SEND_CHANGE_ZI = 8;

    private boolean isUseVoiceAnalysis = false;

    private static final String FILE_UPLOAD_URL = Constant.BASE_URL
            + "/member/fileUpload/upload.jhtml?fileType=media&convert=convert&memberChildId="
            + UserManager.getLoginedUser().getMemberChildId();

    private MediaRecorder recorder;
    private boolean isRecording = false;
    private String voiceFilePath;
    private String remoteVoiceFilePath;
    private Thread changeZiImgWorker;
    private Runnable rChangeZiImgWorker;

    private int hasPowerCount = 0; // 采集到声音的次数
    private double dbAll = 0;//每个字音频采集到的总分贝
    private double db = 0;// 分贝

    public BianShiManager(Handler handler) {
        super(handler);
    }

    public synchronized void startVoiceRecording() {
        if (recorder == null) {

            setInitRecorderData(); //录音的基本设置
        } else {
            // 停止录音
            stopVoiceRecord();
            setInitRecorderData(); //录音的基本设置
        }
    }

    //录音的基本设置
    public synchronized void setInitRecorderData() {
        dbAll = 0;
        db = 0;
        hasPowerCount = 0;
        Count = 0;
        final String path = Environment.getExternalStorageDirectory()
                + "/"
                + Constant.UPLOAD_PATH
                + "/"
                + UserManager.getLoginedUser().getUserName()
                + "/"
                + "YH"
                + DateFormat.format("yyyyMMdd_hhmmss",
                System.currentTimeMillis());

        if (!createPath(path)) {
            mHandler.obtainMessage(INIT_RECORDER_FAIL).sendToTarget();
            return;
        }

        recorder = new MediaRecorder();// new出MediaRecorder对象

        // 设置MediaRecorder的音频源为麦克风
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setAudioChannels(1);

        if (Build.VERSION.SDK_INT >= 10) {
            // 设置MediaRecorder录制的音频格式
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            // 设置MediaRecorder录制音频的编码为AAC。
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            // 设置MediaRecorder的音频参数
            recorder.setAudioSamplingRate(44100);
            recorder.setAudioEncodingBitRate(9600);

            voiceFilePath = path + ".m4a";
        } else {
            // older version of Android, use crappy sounding voice codec
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            voiceFilePath = path + ".amr";
        }

        recorder.setOutputFile(voiceFilePath);

        try {
            recorder.prepare();// 准备录制
        } catch (IOException e) {
            mHandler.obtainMessage(INIT_RECORDER_FAIL).sendToTarget();

            if (Constant.DEBUG)
                e.printStackTrace();

            stopVoiceRecord();
            return;
        }
        recorder.start();// 开始录制
        isRecording = true;
        mHandler.obtainMessage(START_RECORD).sendToTarget();

        rChangeZiImgWorker = new changeZiImgWorker();
        changeZiImgWorker = new Thread(rChangeZiImgWorker);
        changeZiImgWorker.start();
    }

    // 停止录音
    public synchronized void stopVoiceRecord() {
        Log.i(TAG, "删除录音1");
        isRecording = false;
        if (recorder != null) {

            Log.i(TAG, "删除录音2");

            // recorder.stop();
            // recorder.release();
            //recorder = null;
            try {
                recorder.stop();
            } catch (IllegalStateException e) {
                // TODO 如果当前java状态和jni里面的状态不一致，
                //e.printStackTrace();
                recorder = null;
                recorder = new MediaRecorder();
            }
            recorder.release();
            recorder = null;
        }
//		isRecording = false;
//		if (recorder != null) {
//			recorder.stop();
//			recorder.release();
//			recorder = null;
//		}

    }

    public boolean startUploadVoiceFile(String filePath) {
        this.voiceFilePath = filePath;
        return startUploadVoiceFile();
    }

    public boolean startUploadVoiceFile() {
        if (!TextUtils.isEmpty(voiceFilePath)) {
            if (Constant.DEBUG)
                Log.d(tag, "filePath = " + voiceFilePath);

            final File voiceFile = new File(voiceFilePath);

            if (voiceFile.exists()) {
                RequestParams params = new RequestParams(
                        new HashMap<String, String>());
                params.put("token", UserManager.getLoginedUser().getToken());
                if (Constant.DEBUG) {
                    Log.i("BianShiManager", "memberChildId = "
                            + UserManager.getLoginedUser().getMemberChildId());
                    Log.i("BianShiManager", "token = "
                            + UserManager.getLoginedUser().getToken());
                }
                try {
                    params.put("file", voiceFile);
                } catch (FileNotFoundException e) {
                    if (Constant.DEBUG)
                        Log.e(tag, e.getMessage());
                }

                AsyncHttpClientUtil.getClient().post(FILE_UPLOAD_URL, params,
                        new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode,
                                                  Header[] headers, String response) {
                                int status = 0;

                                com.alibaba.fastjson.JSONObject jo = JSON.parseObject(response);
                                Log.d(tag, "jo = " + jo);
                                status = jo.getInteger("status");
                                remoteVoiceFilePath = jo
                                        .getString("data");

                                if (Constant.DEBUG)
                                    Log.d(tag,
                                            "Get the responst of FILE_UPLOAD_URL: "
                                                    + remoteVoiceFilePath);

                                if (status == 100) {
                                    mHandler.obtainMessage(FILE_UPLOAD_SUCCESS)
                                            .sendToTarget();
                                } else {
                                    Message message = Message.obtain();
                                    message.what = FILE_UPLOAD_FAIL;
                                    message.arg1 = status;
                                    message.obj = remoteVoiceFilePath;
                                    mHandler.sendMessage(message);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode,
                                                  Header[] headers, String responses,
                                                  Throwable e) {
                                mHandler.obtainMessage(FILE_UPLOAD_FAIL)
                                        .sendToTarget();

                                if (Constant.DEBUG)
                                    e.printStackTrace();
                            }
                        });
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void clearVoiceFile() {
        if (!TextUtils.isEmpty(voiceFilePath)) {
            final File voiceFile = new File(voiceFilePath);

            if (voiceFile.exists()) {
                if (!voiceFile.delete()) {
                    if (Constant.DEBUG)
                        Log.e(tag, "Voice File can not be delete. The Path is "
                                + voiceFilePath);
                }
            }
        }
    }

    public boolean isUseVoiceAnalysis() {
        return isUseVoiceAnalysis;
    }

    public void setUseVoiceAnalysis(boolean isUseVoiceAnalysis) {
        this.isUseVoiceAnalysis = isUseVoiceAnalysis;
    }

    public boolean isRecordSuccess() {
        return hasPowerCount >= 25 || !isUseVoiceAnalysis();
    }

    //	private class changeZiImgWorker implements Runnable {
//		private static final int RISING_DETECT_TIMES = 5;
//		private static final int FALLING_DETECT_TIMES = 4;
//		private static final int OUTSIDE_POWER_PRE_TEST = 16;
//
//		private static final int MAX_RISING_DETECT_TIMES = 20;
//
//		@Override
//		public void run() {
//			int power;
//			int powerCount = 0;
//
//			double powerMap;
//			double powerMapPre = 0;
//
//			int outsidePower = 0;
//			int risingCount = 0;
//			int fallingCount = 0;
//			int buildBase = 0;
//
//			int repeatCount = 0;
//
//			while (isRecording && isUseVoiceAnalysis) {
//
//					power = 30 * recorder.getMaxAmplitude() / 32768;
//
//					buildBase++;
//					powerCount = powerCount + power - outsidePower;
//
//					if (buildBase <= OUTSIDE_POWER_PRE_TEST ) {
//						outsidePower = powerCount / buildBase;
//					} else {
//						powerMap = Math.log10(powerCount / (double) buildBase);
//						if (Constant.DEBUG)
////							Log.e(tag, power + " / " + powerMap);
//
//						if (powerMap > powerMapPre) {
//							risingCount++;
//							if (fallingCount > 0
//									&& fallingCount < FALLING_DETECT_TIMES) {
//								fallingCount = 0;
//							}
//						} else if (powerMap < powerMapPre) {
//							if (risingCount > 0
//									&& risingCount < RISING_DETECT_TIMES) {
//								risingCount = 0;
//							} else if (risingCount >= MAX_RISING_DETECT_TIMES) {
//								risingCount = 0;
//							} else if (risingCount >= RISING_DETECT_TIMES
//									&& risingCount < MAX_RISING_DETECT_TIMES) {
//								fallingCount++;
//							}
//							
//							Log.e(tag, risingCount + " / " + fallingCount);
//
//							if (risingCount >= RISING_DETECT_TIMES
//									&& fallingCount >= FALLING_DETECT_TIMES) {
//
//								hasPowerCount++;
//								Log.e("Log.d", "hasPowerCount = "
//										+ hasPowerCount);
//								Log.e("Log.d", "buildBase = " + buildBase);
//								Log.e("Log.d", "outsidePower = " + outsidePower);
//								risingCount = 0;
//								fallingCount = 0;
//								powerCount = 0;
//								buildBase = 0;
//
//								repeatCount = 0;
//							}
//						}
//						powerMapPre = powerMap;
//					}
//
//				 try {
//				 Thread.sleep(50);
//				 } catch (InterruptedException e) {
//					 e.printStackTrace();
//				 }
//			}
//		}
//	}
    private class changeZiImgWorker implements Runnable {

        @Override
        public void run() {
            updateMicStatus();
        }
    }

    /**
     * 更新话筒状态
     */
    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间  
    private int Count = 0;//计算采集次数
    //每个音采集的分贝集合
    List<Double> list = new ArrayList<Double>();

    private void updateMicStatus() {
        if (recorder != null) {
            while (isRecording && isUseVoiceAnalysis) {
                double ratio = (double) recorder.getMaxAmplitude() / BASE;
                if (ratio > 1)
                    db = 20 * Math.log10(ratio);
                list.add(db);
                Log.d(TAG, "分贝值：" + db);
                dbAll = dbAll + db;
                Log.d(TAG, "分贝总值：" + dbAll);
                Count++;
                if (Count == 20 || Count == 40 || Count == 60 || Count == 80 || Count == 100) {
                    double meandb = dbAll / Count;
                    for (int i = 0; i < list.size(); i++) {
                        double oncedb = list.get(i);
                        if (oncedb - meandb > 8) {
                            hasPowerCount++;
                        }
                    }
                    list.clear();
                }
                try {
                    Thread.sleep(SPACE);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}
