package com.p.SMSRelayer.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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

    }
}
