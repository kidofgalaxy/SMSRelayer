package com.p.SMSRelayer.DataType;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.p.SMSRelayer.R;
import com.p.SMSRelayer.Utils.DataUtil;

import java.util.HashSet;

/**
 * Created by p on 2014/10/25.
 *
 */
public class PublicData extends Application {


    private static PublicData publicDataSelf = null;

    private DataUtil du = null;
    @Override
    public void onCreate(){
        publicDataSelf = this;
        du = new DataUtil(this, this.getString(R.string.database_name), null, 1);

    }
    public static PublicData getInstance(){
        return publicDataSelf;
    }
    public SQLiteDatabase getDataBaseInstanse(){
        return du.getReadableDatabase();
    }
}
