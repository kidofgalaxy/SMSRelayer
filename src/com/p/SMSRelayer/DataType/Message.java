package com.p.SMSRelayer.DataType;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by p on 2014/10/18.
 * 消息类，用于储存一条收到的消息，并发送。
 */
public class Message {
    public String from,content,date,person,id;

    public Message(String id,String person,String address,String body,long date){
        this.id = id;
        from = address;
        this.person = person;
        content = body;
        SimpleDateFormat sdf= new SimpleDateFormat("yy/MM/dd HH:mm");
        java.util.Date dt = new Date(date );
        this.date = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00

    }
    public void send(String target,Context context){
        SmsManager smsManager = SmsManager.getDefault();


        /*// create the sentIntent parameter
        Intent sentIntent = new Intent("SENT_SMS_ACTION");

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent,
                0);

        // create the deilverIntent parameter
        Intent deliverIntent = new Intent("DELIVERED_SMS_ACTION");

        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
                deliverIntent, 0);*/
        String transfer_str = content+"\n来自:";
        if(person != null)
            transfer_str += person;
        transfer_str +=" "+from+"\n";
        transfer_str += "时间:"+date;

        Log.d("Message",transfer_str);
        List<String> divideContents = smsManager.divideMessage(transfer_str);
        for (String text : divideContents) {
            smsManager.sendTextMessage( target , null , text , null , null);
        }

    }
    public void show(){
        Log.d("Message","id="+id+" "+content+"来自:"+from+"接受时间:"+date);
    }
}
