package com.p.SMSRelayer.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by p on 2014/10/17.
 *
 */
public class WorkerThread extends Thread {
    String target_phone = null;
    Context context = null;
    Looper mylooper = null;
    public Handler thread_handler = null;
    public Handler main_handler = null;
    public WorkerThread(Context ctx,Handler mh){
        context = ctx;
        main_handler = mh;
    }
    public void finish(){
        mylooper.quit();
    }
    public WorkerThread(String phone, Context ctx,Handler mh){
        target_phone = phone;
        context = ctx;
        main_handler = mh;
    }

    @Override
    public void run(){
        Looper.prepare();
        mylooper = Looper.myLooper();
        thread_handler = new SmsTaskHandler(context,target_phone);
        Looper.loop();
    }

}
