package com.p.SMSRelayer.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.p.SMSRelayer.DataType.Message;

/**
 * Created by p on 2014/10/17.
 * 该类用于在新线程中对信收到的消息进行处理，
 * 首先扫描得到的短信，找出未读的短信存入sqlite数据库，
 * 并发送给target_phone（仅当有target_phone参数时），然后将该短信标记为已读取。
 * read字段0未读，1已读。
 */
public class SmsTask extends Thread {
    String target_phone = null;
    Context context = null;
    public SmsTask(Context ctx){
        context = ctx;
    }
    public SmsTask(String pnum,Context ctx){
        target_phone = pnum;
        context = ctx;
    }

    @Override
    public void run(){
        Cursor cursor = context.getContentResolver()
                                    .query(Uri.parse("content://sms/inbox"), null, "read=0", null, null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Message amsg = new Message(
                                cursor.getString(cursor.getColumnIndex("person")),
                                cursor.getString(cursor.getColumnIndex("body")),
                                cursor.getLong(cursor.getColumnIndex("date"))
                                );
                if(target_phone != null){
                    amsg.send(target_phone);
                }
            }
        }

    }
}
