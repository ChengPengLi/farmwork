package com.ky3h.farmwork.netrequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;


import com.ky3h.farmwork.R;
import com.ky3h.farmwork.base.BaseManager;
import com.ky3h.farmwork.bean.Version;
import com.ky3h.farmwork.utils.AsyncHttpClientUtil;
import com.ky3h.farmwork.utils.Constant;
import com.ky3h.farmwork.utils.JsonUtil;
import com.ky3h.farmwork.widget.LoadingDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;


/**
 *检测更新
 */

public class UpdateManager extends BaseManager
{
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;
	
	public static final int HAVE_NEW_VERSION = 101;
    public static final int NO_NEW_VERSION = 100;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	String downurl;

	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Handler handler , Context mContext)
	{
		super(handler);
		this.mContext = mContext;
	}


	/**
	 * 检查软件是否有更新版本
	 * @param mContext 
	 * 
	 * @return
	 */
	public void isUpdate(Context mContext)
	{
		// 获取当前软件版本
		int version = 0;
		try {
		PackageManager manager = mContext.getPackageManager();
		PackageInfo info;
			info = manager.getPackageInfo(mContext.getPackageName(), 0);
			version = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		String url = "/versions_update/updateVersion.jhtml";
		Map<String , String> params = new HashMap<String, String>();
		RequestParams params2 = new RequestParams(params);
		getVersion(Constant.BASE_URL+url,params2,mContext);
	}
	private LoadingDialog mWaitDialog;
	private void getVersion(String url, RequestParams params2,
			Context mContext2) {
		if (mWaitDialog == null) {
			mWaitDialog = new LoadingDialog(mContext);
		}
		if (!mWaitDialog.isShowing()) {
			mWaitDialog.show();
		}
		AsyncHttpClient asyncHttpClient = AsyncHttpClientUtil.getClient();
		asyncHttpClient.get(url, params2, new TextHttpResponseHandler(){
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				if (mWaitDialog != null) {
					mWaitDialog.dismiss();
				}	
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, String response) {
				if (mWaitDialog != null) {
					mWaitDialog.dismiss();
				}
				Version v = JsonUtil.versionResultParser(response, "data");
				if (v != null) {
					downurl = v.getDownurl();
					if (v.getIsUpdate()) {
						// 提示版本升级
//						if (downurl != null && !TextUtils.isEmpty(downurl)) {
//							Uri uri = Uri.parse(downurl);
//							showNoticeDialog(downurl);
//						}
						Message message = Message.obtain();
						message.what = HAVE_NEW_VERSION;
						message.obj = v;
						mHandler.sendMessage(message);
//						mHandler.obtainMessage(HAVE_NEW_VERSION, v).sendToTarget();
					}else{
//						Toast.makeText(mContext, "已是最新版本，无需更新。", Toast.LENGTH_SHORT).show();
//						mHandler.obtainMessage(NO_NEW_VERSION).sendToTarget();
						mHandler.sendEmptyMessage(NO_NEW_VERSION);
					}
				}	
			}
		});
	}


	/**
	 * 显示软件更新对话框
	 * @param url 
	 */
	private void showNoticeDialog(final String url)
	{
		// 构造对话框
		Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// 更新
		builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				// 显示下载对话框
				showDownloadDialog(url);
			}
		});
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 * @param url 
	 */
	private void showDownloadDialog(String url)
	{
		// 构造软件下载对话框
		Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				// 设置取消状态
				cancelUpdate = true;
			}
		});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 下载文件
		downloadApk(url);
	}

	/**
	 * 下载apk文件
	 * @param url 
	 */
	private void downloadApk(String url)
	{
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 *@date 2012-4-26
	 *@blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory() + "/";
					mSavePath = sdpath + Constant.PATH+"/"+ "download";
					URL url = new URL(downurl);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists())
					{
						file.mkdir();
					}
					File apkFile = new File(mSavePath, "WYBS");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do
					{
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						handler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							// 下载完成
							handler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk()
	{
		File apkfile = new File(mSavePath, "WYBS");
		if (!apkfile.exists())
		{
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
