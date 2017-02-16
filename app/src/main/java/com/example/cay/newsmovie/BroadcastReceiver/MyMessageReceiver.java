package com.example.cay.newsmovie.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.cay.newsmovie.activity.MovieDetailActivity;
import com.example.cay.newsmovie.app.MyApplication;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.Map;

/**
 * Created by Cay on 2017/2/13.
 */

public class MyMessageReceiver extends PushMessageReceiver {
    private static final String TAG = "MyMessageReceiver";
    // onReceivePassThroughMessage用来接收服务器发送的透传消息，

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        String mMessage = miPushMessage.getContent();
        Log.i(TAG,"onReceivePassThroughMessage"+ mMessage);
        String mMessage1 = miPushMessage.getTopic();
        Log.i(TAG,"onReceivePassThroughMessage1"+ mMessage1);

    }
    //onNotificationMessageClicked用来接收服务器发来的通知栏消息（用户点击通知栏时触发）
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        String mMessage = miPushMessage.getContent();
        String mMessage1 = miPushMessage.getTopic();
        String mMessage2 = miPushMessage.getMessageId();
        String mMessage3 = miPushMessage.getAlias();
        String mMessage4 = miPushMessage.getCategory();
        String mMessage5 = miPushMessage.getDescription();
        String mMessage6 = miPushMessage.getTitle();
        String mMessage7 = miPushMessage.getUserAccount();
        int mMessage8 = miPushMessage.getMessageType();
        Map<String,String> mMessage9 = miPushMessage.getExtra();
        int mMessage10 = miPushMessage.getNotifyId();
        int mMessage11 = miPushMessage.getNotifyType();
        int mMessage12 = miPushMessage.getPassThrough();
        Log.i(TAG, "getContent: "+mMessage);
        Log.i(TAG, "getTopic: "+mMessage1);
        Log.i(TAG, "getMessageId: "+mMessage2);
        Log.i(TAG, "getAlias: "+mMessage3);
        Log.i(TAG, "getCategory: "+mMessage4);
        Log.i(TAG, "getDescription: "+mMessage5);
        Log.i(TAG, "getTitle: "+mMessage6);
        Log.i(TAG, "getUserAccount: "+mMessage7);
        Log.i(TAG, "getMessageType: "+mMessage8);
        Log.i(TAG, "getExtra: "+mMessage9.toString());
        Log.i(TAG, "getNotifyId: "+mMessage10);
        Log.i(TAG, "getNotifyType: "+mMessage11);
        Log.i(TAG,"getPassThrough"+ mMessage12);
        Log.i(TAG, "电影id: "+mMessage9.get("id"));
        Log.i(TAG, "图片地址: "+mMessage9.get("img_url"));
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", mMessage9.get("id"));
        intent.putExtra("img_url",mMessage9.get("img_url"));
        context.startActivity(intent);
      //  MovieDetailActivity.startE((Activity)context,mMessage9.get("id"),mMessage9.get("img_url"),null);

    }
    //    onNotificationMessageArrived用来接收服务器发来的通知栏消息（消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息）
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        String mMessage = miPushMessage.getContent();
        Log.i(TAG, "onNotificationMessageArrived: "+mMessage);
    }

    //onReceiveRegisterResult用来接受客户端向服务器发送注册命令消息后返回的响应。
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        String mMessage = miPushCommandMessage.getCommand();
        Log.i(TAG, "onReceiveRegisterResult: "+mMessage);    }
    //    onCommandResult用来接收客户端向服务器发送命令消息后返回的响应，
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        String mMessage = miPushCommandMessage.getCommand();
        Log.i(TAG, "onCommandResult: "+mMessage);    }
}
