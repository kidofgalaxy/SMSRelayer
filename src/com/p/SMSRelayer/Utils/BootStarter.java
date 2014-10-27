package com.p.SMSRelayer.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.p.SMSRelayer.Activity.MainActivity;

/**
 * Created by p on 2014/10/24.
 */
public class BootStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent it) {
        Log.d("BootStarter","收到开机消息！");
        Intent intent = new Intent(context, MainActivity.class);
        context.startService(intent);
    }
}
