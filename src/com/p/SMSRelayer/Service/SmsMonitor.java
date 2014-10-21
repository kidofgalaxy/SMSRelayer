package com.p.SMSRelayer.Service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import com.p.SMSRelayer.Utils.SmsObserver;

/**
 * Created by p on 2014/10/17.
 *
 */
public class SmsMonitor extends Service {
    Thread mTread = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        ContentResolver resolver=getContentResolver();
        SmsObserver observer = new SmsObserver(new Handler(),this);
        //注册观察者类时得到回调数据确定一个给定的内容URI变化。
        resolver.registerContentObserver(Uri.parse("content://sms"), true, observer);
    }
}
