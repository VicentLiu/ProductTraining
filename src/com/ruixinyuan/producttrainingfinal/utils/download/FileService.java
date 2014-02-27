package com.ruixinyuan.producttrainingfinal.utils.download;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ruixinyuan.producttrainingfinal.db.DBConstants;
import com.ruixinyuan.producttrainingfinal.db.DownloadDBOpenHelper;
/*
 *@user vicentliu
 *@time 2013-6-19上午9:45:52
 *@package com.ruixinyuan.producttrainingfinal.utils.download
 */
public class FileService {

    private DownloadDBOpenHelper openHelper;

    public FileService(Context context) {
        openHelper = new DownloadDBOpenHelper(context);
    }

    private static final String sql = "select thread_id,downlength from "
                                      + DBConstants.DOWNLOAD_TABLE_NAME
                                      + " where path=?";
    /**
     * 获取每条线程已经下载的文件长度
     * @param path
     * @return
     */
    public Map<Integer, Integer> getData(String path) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{path});
        Map<Integer, Integer> data = new HashMap<Integer, Integer>();
        try {
            while(cursor.moveToNext()) {
                data.put(cursor.getInt(0), cursor.getInt(1));
            }
        } finally {
            cursor.close();
            db.close();
        }
        
        return data;
    }
    /**
     * 实时更新每条线程已经下载的文件长度
     * @param path
     * @param map
     */
    public void update( String path , Map<Integer, Integer> map) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for(Map.Entry<Integer, Integer>entry : map.entrySet()) {
                db.execSQL("update "
                           + DBConstants.DOWNLOAD_TABLE_NAME
                           + " set downlength=? "
                           + "where path=? and thread_id=?",
                           new Object[]{entry.getValue(),path,entry.getKey()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    /**
     * 保存每条线程已经下载的文件长度
     * @param path
     * @param map
     */
    public void save( String path , Map<Integer, Integer> map) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for(Map.Entry<Integer, Integer>entry : map.entrySet()) {
                db.execSQL("insert into "
                           + DBConstants.DOWNLOAD_TABLE_NAME
                           + " (path,thread_id,downlength)"
                           + " values(?,?,?)",
                           new Object[]{entry.getValue(),path,entry.getKey()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    /**
     * 当文件下载完成后，删除对应的下载记录
     * @param path
     */
    public void delete(String path) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        try {
            db.execSQL("delete from "
                       + DBConstants.DOWNLOAD_TABLE_NAME
                       + " where path=?", new Object[]{path});
        } finally {
            db.close();
        }
    }
}
