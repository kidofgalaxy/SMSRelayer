package com.p.SMSRelayer.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import com.p.SMSRelayer.R;
import com.p.SMSRelayer.Service.SmsMonitor;

import java.util.Date;

/**
 * Created by p on 2014/10/17.
 */
public class MainActivity extends Activity {
    Intent aintent = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        aintent = new Intent(this, SmsMonitor.class);
        startService(aintent);
    }
    @Override
    public void onDestroy(){
        stopService(aintent);
    }


    public void sendMessage(View view) {
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put("address","18807963708");
        values.put("body","测试短信");
        values.put("read",0);
        values.put("date",date.getTime());
        values.put("type", 1);


        getContentResolver().insert(Uri.parse("content://sms/inbox"),values);
    }

    public void stopService(View view) {
    }
}