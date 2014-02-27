package com.ruixinyuan.producttrainingfinal.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ruixinyuan.producttrainingfinal.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/*
 *@user vicentliu
 *@time 2013-7-1下午2:31:49
 *@package com.ruixinyuan.producttrainingfinal.db
 */
public class DBUtils {

    private static final String DATABASE_PATH = "/data/data/com.ruixinyuan.producttrainingfinal/databases/";
    public static boolean isDatabaseExists (String DBName) {
        SQLiteDatabase db = null;
        try {
            String dBFileName = DATABASE_PATH + DBName;
            db = SQLiteDatabase.openDatabase(dBFileName, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException ex){
            ex.printStackTrace();
        }
        if (db != null) {
            db.close();
        }
        return db != null ? true : false;
    }

    public static void copyLocalSaleSkillDatabase (Context context , String DBName) 
                  throws IOException {
        String dbFileName = DATABASE_PATH + DBName;
        File fileDir = new File(DATABASE_PATH);
        if (!fileDir.exists())
            fileDir.mkdir();
        FileOutputStream fileOs = null;
        try {
            fileOs = new FileOutputStream(dbFileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        InputStream is = context.getResources().openRawResource(R.raw.sale_skills);
        byte[] buffer = new byte[2046];
        int count = 0;
        try {
            while ((count = is.read(buffer)) > 0) {
                fileOs.write(buffer, 0, count);
                fileOs.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
                fileOs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyLocalProductionDatabase (Context context , String DBName) 
            throws IOException {
      String dbFileName = DATABASE_PATH + DBName;
      File fileDir = new File(DATABASE_PATH);
      if (!fileDir.exists())
          fileDir.mkdir();
      FileOutputStream fileOs = null;
      try {
          fileOs = new FileOutputStream(dbFileName);
      } catch (FileNotFoundException ex) {
          ex.printStackTrace();
      }
      InputStream is = context.getResources().openRawResource(R.raw.products);
      byte[] buffer = new byte[2046];
      int count = 0;
      try {
          while ((count = is.read(buffer)) > 0) {
              fileOs.write(buffer, 0, count);
              fileOs.flush();
          }
      } catch (IOException ex) {
          ex.printStackTrace();
      } finally {
          try {
              is.close();
              fileOs.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
    }
}
}
