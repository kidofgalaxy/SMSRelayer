package com.p.SMSRelayer.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.p.SMSRelayer.R;
import com.p.SMSRelayer.Service.SmsMonitor;

import java.util.Date;

/**
 * Created by p on 2014/10/17.
 *
 */
public class MainActivity extends Activity {
    EditText phone_num = null;
    int tnum = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        phone_num = (EditText) findViewById(R.id.phone_num_text);
    }


    public void sendMessage(View view) {
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put("address","18607963708");
        values.put("body",String.valueOf(tnum));
        values.put("read",0);
        values.put("date",date.getTime());
        values.put("type", 1);
        ++tnum;

        Uri auri = getContentResolver().insert(Uri.parse("content://sms/inbox"),values);
        if(auri == null){
            Log.d(getClass().getName(),"发送测试短信失败！");
        }else Log.d(getClass().getName(),"发送短信成功！");
    }

    public void stopService(View view) {
        Log.d(getClass().getName(),"stop service!");
        Intent aintent = new Intent(this,SmsMonitor.class);
        aintent.putExtra("STOP_SERVICE","YES");
        startService(aintent);
    }
    public void startService(View view) {
        Log.d(getClass().getName(),"startservice!");
        Intent aintent = new Intent(this, SmsMonitor.class);
        startService(aintent);
    }
    public void changePhone(View view){
        Log.d(getClass().getName(),"change phone!");
        String pnum = phone_num.getText().toString();
        Intent aintent = new Intent(this,SmsMonitor.class);
        aintent.putExtra("TARGET_PHONE",pnum);
        startService(aintent);
    }
}