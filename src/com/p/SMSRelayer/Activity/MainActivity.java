package com.p.SMSRelayer.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.p.SMSRelayer.R;
import com.p.SMSRelayer.Service.SmsMonitor;

import java.util.Date;

/**
 * Created by p on 2014/10/17.
 */
public class MainActivity extends Activity {
    Intent aintent = null;
    EditText phone_num = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        phone_num = (EditText) findViewById(R.id.phone_num_text);

        aintent = new Intent(this, SmsMonitor.class);
        startService(aintent);
    }


    public void sendMessage(View view) {
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put("address","18607963708");
        values.put("body","测试短信");
        values.put("read",0);
        values.put("date",date.getTime());
        values.put("type", 1);


        Uri auri = getContentResolver().insert(Uri.parse("content://sms/inbox"),values);
        if(auri == null){
            Log.d("MainActivity","发送测试短信失败！");
        }
    }

    public void stopService(View view) {
        stopService(aintent);
    }
    public void changePhone(View view){
        String pnum = phone_num.getText().toString();
        aintent.putExtra("TARGET_PHONE",pnum);
        startService(aintent);
    }
}