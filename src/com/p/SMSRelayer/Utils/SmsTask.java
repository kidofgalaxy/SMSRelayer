package com.p.SMSRelayer.Utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.p.SMSRelayer.DataType.Message;

import java.util.concurrent.locks.Lock;

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
    ContentValues values = new ContentValues();


    public SmsTask(Context ctx){
        context = ctx;
    }
    public SmsTask(String phone,Context ctx){
        target_phone = phone;
        context = ctx;
        values.put("read","1");
    }

    @Override
    public void run(){
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://sms/inbox"), null, "read=0", null, null);
        int msg_id = -1,cnum = -1;
        if(cursor != null){
            while(cursor.moveToNext()){
                Message amsg = new Message(
                                cursor.getString(cursor.getColumnIndex("address")),
                                cursor.getString(cursor.getColumnIndex("body")),
                                cursor.getLong(cursor.getColumnIndex("date"))
                                );
                msg_id = cursor.getInt(cursor.getColumnIndex("_id"));

                cnum = context.getContentResolver().update(Uri.parse("content://sms/inbox/"),
                        values,"_id="+msg_id,null);
                if(cnum > 0)
                    Log.d("SmsTask","修改短信状态成功");
                else Log.d("SmsTask","修改短信状态失败");
                if(target_phone != null){
                    amsg.send(target_phone,context);
                    amsg.show();
                }
            }
        }

        //将所有未读短信标记为已发送
    }
}
