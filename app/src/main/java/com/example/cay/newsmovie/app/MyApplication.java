package com.example.cay.newsmovie.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.os.Process;

import com.example.cay.newsmovie.http.HttpUtils;
import com.tencent.bugly.crashreport.CrashReport;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import io.vov.vitamio.Vitamio;
import okhttp3.Call;


/**
 * Created by jingbin on 2016/11/22.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    public static final String APP_ID = "2882303761517547390";
    public static final String APP_KEY = "5111754767390";
    public static final String TAG = "com.example.cay.newsmovie";

    public static MyApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        HttpUtils.getInstance().setContext(getApplicationContext());
        CrashReport.initCrashReport(getApplicationContext());
/**********************小米推送开始***************************/
       //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

        /**********************小米推送结束***************************/

        Vitamio.isInitialized(getApplicationContext());

    }

   private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
