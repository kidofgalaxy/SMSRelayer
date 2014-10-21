package com.p.SMSRelayer.DataType;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by p on 2014/10/18.
 * 消息类，用于储存一条收到的消息，并发送。
 */
public class Message {
    String from,content,date;

    public Message(String person,String body,long date){
        from = person;
        content = body;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date dt = new Date(date );
        this.date = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00

    }
    public void send(String target,Context context){
        SmsManager smsMagager = SmsManager.getDefault();


        // create the sentIntent parameter
        Intent sentIntent = new Intent("SENT_SMS_ACTION");

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent,
                0);

        // create the deilverIntent parameter
        Intent deliverIntent = new Intent("DELIVERED_SMS_ACTION");

        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
                deliverIntent, 0);



        smsMagager.sendTextMessage( target , null , content , sentPI , deliverPI );
    }
    public void show(){
        Log.d("a message","来自:"+from+"接受时间:"+date+""+content);
    }
}
