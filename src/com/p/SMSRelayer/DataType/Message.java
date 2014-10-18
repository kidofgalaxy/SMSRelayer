package com.p.SMSRelayer.DataType;

import android.telephony.SmsManager;

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
        java.util.Date dt = new Date(date * 1000);
        this.date = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00

    }
    public void send(String target){
        SmsManager manager = SmsManager.getDefault();
        ArrayList<String> list = manager.divideMessage("来自:"+from+"\n接受时间:"+date+"\n"+content);  //因为一条短信有字数限制，因此要将长短信拆分
        for(String text:list){
            manager.sendTextMessage(target, null, text, null, null);
        }
    }

}
