package com.shaohong.thesethree.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.shaohong.thesethree.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by shaohong on 2017/5/10.
 */

public class UpdateManager {
    //获取服务器数据成功
    private static final int CHECKED=0;
    //下载中...
    private static final int DOWNLOAD = 1;
    //下载完成
    private static final int DOWNLOAD_FINISH = 2;
    //保存服务器提供的版本信息
    private HashMap<String,String> hashMap;
    //下载保存路径
    private String savePath;
    //记录进度条数量
    private int progress;
    //是否取消更新
    private boolean cancelUpdate = false;
    //上下文对象
    private Context context;
    //进度条
    private ProgressBar progressBar;
    //更新进度条的对话框
    private Dialog downloadDialog;

    public UpdateManager(Context context) {
        this.context = context;
    }

    //更新检查
    private void checkUpdate(){
        if(isUpdate()){
            showNoticeDialog();
        }
    }

    //检查是否需要更新
    private boolean isUpdate(){
        int versionCode = getVerCode();
        if(null != hashMap) {
            int serviceCode = Integer.valueOf(hashMap.get(ConstantUtils.APK_VERSION));
            //版本判断
            if(serviceCode > versionCode) {
                return true;
            }
        }
        return false;
    }

    public void update(){
        new Thread(runnable).start();
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ConstantUtils.REQUEST_URL+"getVersion")
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (!result.isEmpty()) {
                        JSONObject obj=new JSONObject(result);
                        hashMap=new HashMap<>();
                        hashMap.put(ConstantUtils.APK_VERSION,obj.getString(ConstantUtils.APK_VERSION));
                        hashMap.put(ConstantUtils.APK_NAME,"thesethree");
                        hashMap.put(ConstantUtils.APK_URL,"http://www.js00000000.com/Content/File/thesethree.apk");
                        handler.sendEmptyMessage(CHECKED);
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //更新提示框
    private void showNoticeDialog() {
        //构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(R.string.soft_update_info);
        //更新
        builder.setPositiveButton(R.string.soft_update_button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.progress_bar, null);
        progressBar = (ProgressBar) view.findViewById(R.id.update_progressBar);
        builder.setView(view);
        builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
        //下载文件
        downloadApk();
    }

    private void downloadApk() {
        // 启动新线程下载软件
        new DownloadApkThread().start();
    }

    //获取版本号
    private int getVerCode() {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    ConstantUtils.PACKAGE_NAME, 0).versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){
                case CHECKED:
                    checkUpdate();
                    break;
                //下载中。。。
                case DOWNLOAD:
                    //更新进度条
                    progressBar.setProgress(progress);
                    break;
                //下载完成
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;

            }
        }
    };

    //创建新进程下载APK 文件
    private class DownloadApkThread extends Thread {
        @Override
        public void run() {
            try
            {
                //判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    // 获取SDCard的路径
                    String sdPath = Environment.getExternalStorageDirectory() + "/";
                    savePath = sdPath + "ehospital";
                    URL url = new URL(hashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File(savePath);
                    // 如果文件不存在，新建目录
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    File apkFile = new File(savePath, hashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do
                    {
                        int numRead = is.read(buf);
                        count += numRead;
                        // 计算进度条的位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        handler.sendEmptyMessage(DOWNLOAD);
                        if (numRead <= 0)
                        {
                            // 下载完成
                            handler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numRead);
                    } while (!cancelUpdate);//点击取消就停止下载
                    fos.close();
                    is.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            downloadDialog.dismiss();
        }
    }

    //安装新版本 apk
    private void installApk() {
        File apkFile = new File(savePath, hashMap.get("name"));
        if (!apkFile.exists())
        {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}