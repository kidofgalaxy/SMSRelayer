package com.p.SMSRelayer.Service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.p.SMSRelayer.Utils.SmsObserver;

import java.util.regex.Pattern;

/**
 * Created by p on 2014/10/17.
 * 启动服务后，其他组件对服务的操作也是通过startService给服务传递的Intent中带数据
 *
 * TARGET_PHONE : 转发时接受者电话
 *
 *
 */
public class SmsMonitor extends Service {

    SmsObserver observer = null;
    Handler mhandler = null;
    ContentResolver resolver = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SmsMonitor","into onStartCommand");
        if(intent.hasExtra("TARGET_PHONE")){

            String tp = intent.getStringExtra("TARGET_PHONE");
            Log.d("SmsMonitor","TARGET_PHONE = "+tp);
            if( Pattern.matches("\\+?\\d{11,13}", tp)){
                Log.d("SmsMonitor","Petter n Matches!");
                observer.setTargetPhone(tp);
            }else{
                Log.d("SmsMonitor","Pettern Not Matches!");
            }
        }else if(intent.hasExtra("STOP_SERVICE")){
            if(intent.getStringExtra("STOP_SERVICE").contains("YES")){
                observer.stop();
                resolver.unregisterContentObserver(observer);
                stopSelf();
            }

        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate(){
        Log.d("SmsMonitor","service Started");
        resolver=getContentResolver();
        mhandler = new Handler();
        observer = new SmsObserver(mhandler,this);
        //注册观察者类时得到回调数据确定一个给定的内容URI变化。
        resolver.registerContentObserver(Uri.parse("content://sms"), true, observer);

    }

}
