package com.p.SMSRelayer.Utils;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

/**
 * Created by p on 2014/10/17.
 */
public class SmsObserver extends ContentObserver{
    Context context = null;
    String target_phone = "18807966755";
    public SmsObserver(Handler handler ,Context ctx) {
        super(handler);
        context = ctx;
    }
    public void setTargetPhone(String phone){
        target_phone = phone;
    }
    public void onChange(boolean selfChange){
        SmsTask smsTask = new SmsTask(target_phone,context);
        smsTask.start();
    }
}
