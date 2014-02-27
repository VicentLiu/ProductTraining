package com.ruixinyuan.producttrainingfinal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 *@user vicentliu
 *@time 2013-6-18下午5:17:04
 *@package com.ruixinyuan.producttrainingfinal.db
 */
public class DownloadDBOpenHelper extends SQLiteOpenHelper {

    private static final String SQL_TABLE_DOWNLOADPICS_CREATE = "create table if not exists "
                                                               + DBConstants.DOWNLOAD_TABLE_NAME
                                                               + "(id integer primary key autoincrement,"
                                                               + "path varchar(100),"
                                                               + "thread_id integer,"
                                                               + "downlength integer)";
    private static final String SQL_TABLE_DOWNLOADPICS_DROP = "drop table if exists" + DBConstants.DOWNLOAD_TABLE_NAME;
    public DownloadDBOpenHelper(Context context) {
        super(context, DBConstants.DOWNLOAD_DB_NAME, null, DBConstants.DOWNLOAD_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_DOWNLOADPICS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_TABLE_DOWNLOADPICS_DROP);
        onCreate(db);
    }

}
