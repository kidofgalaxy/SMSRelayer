package com.p.SMSRelayer.Utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by p on 2014/10/25.
 * 该类用于在新线程中对信收到的消息进行处理，
 * 首先扫描得到的短信，找出未读的短信存入sqlite数据库，
 * 并发送给target_phone（仅当有target_phone参数时），然后将该短信标记为已读取。
 * read字段0未读，1已读。
 *
 */
public class SmsTaskHandler extends Handler {
    public static final int NEW_SMS_ARRIVE = 0;
    public static final int CHANGE_TARGET_PHONE = 1;
    Context context = null;
    String target_phone = null;
    ContentValues values = new ContentValues();
    HashSet<String> sended_msgid = null;
    public SmsTaskHandler(Context ctx,String tgp){
        super();
        context = ctx;
        target_phone = tgp;
        sended_msgid = new HashSet<String>();
        values.put("read","1");
    }
    private void newMessageArrive(){
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://sms/inbox"), null, "read=0", null, null);
        int cnum;
        if(cursor != null){
            while(cursor.moveToNext()){
                com.p.SMSRelayer.DataType.Message amsg = new com.p.SMSRelayer.DataType.Message(
                        cursor.getString(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("person")),
                        cursor.getString(cursor.getColumnIndex("address")),
                        cursor.getString(cursor.getColumnIndex("body")),
                        cursor.getLong(cursor.getColumnIndex("date"))
                );

                cnum = context.getContentResolver().update(Uri.parse("content://sms/inbox/"),
                        values,"_id="+amsg.id,null);
                if(cnum > 0)
                    Log.d("WorkerThread", "修改短信状态成功");
                else Log.d("WorkerThread","修改短信状态失败");
                if(target_phone != null){
                    if(!sended_msgid.contains(amsg.id)){
                        sended_msgid.add(amsg.id);
                        amsg.send(target_phone,context);
                        amsg.show();
                        MyLoger.Log(getClass().getName(),"ameesage send");
                    }

                }
            }
        }
    }
    @Override
    public void handleMessage(Message msg){
        switch (msg.what){
            case NEW_SMS_ARRIVE:
                    Log.d("SmsTaskHanler","receive a new msg  message");
                    newMessageArrive();
                break;
            case CHANGE_TARGET_PHONE:
                Log.d("SmsTaskHanler","receive a change phone message");
                target_phone = (String)msg.obj;
                break;

        }
    }
}
