package com.p.SMSRelayer.Utils;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by p on 2014/10/17.
 *
 */
public class SmsObserver extends ContentObserver{
    Context context = null;
    String target_phone = "18807966755";
    WorkerThread smstask_thread = null;
    public SmsObserver(Handler handler ,Context ctx) {
        super(handler);
        context = ctx;
        smstask_thread = new WorkerThread(target_phone,context,handler);
        smstask_thread.start();
    }
    public void stop(){
        smstask_thread.finish();
    }
    public void setTargetPhone(String phone){
        target_phone = phone;
        Log.d(getClass().getName(),"change phone");
        if(smstask_thread.thread_handler != null){
            Message msg = new Message();
            msg.what = SmsTaskHandler.CHANGE_TARGET_PHONE;
            msg.obj = target_phone;
            smstask_thread.thread_handler.sendMessage(msg);
        }
    }
    public void onChange(boolean selfChange){
        Log.d(getClass().getName(),"onchange");
        if(smstask_thread.thread_handler != null){
            Message msg = new Message();
            msg.what = SmsTaskHandler.NEW_SMS_ARRIVE;
            smstask_thread.thread_handler.sendMessage(msg);
        }
    }
}
