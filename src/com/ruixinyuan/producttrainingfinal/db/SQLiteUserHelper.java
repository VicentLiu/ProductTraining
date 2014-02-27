package com.ruixinyuan.producttrainingfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ruixinyuan.producttrainingfinal.bean.UserBean;

/*
 *@user vicentliu
 *@time 2013-6-13下午3:14:25
 *@package com.ruixinyuan.producttrainingfinal.db
 */
public class SQLiteUserHelper extends SQLiteOpenHelper {

    private static SQLiteUserHelper mDBHelper = null;
    public static String DB_NAME = "login_users.db";
    private static int DB_VERSION = 1;
    private static final String SQL_TABLE_USER_CREATE = "create table if not exists " + DBConstants.USER_TABLE_NAME + "("
            + DBConstants.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConstants.USERNAME + " varchar(20),"
            + DBConstants.PASSWORD + " varchar(20))";
    
    public SQLiteUserHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized SQLiteUserHelper getInstance(Context context) {
        if (mDBHelper == null) {
            mDBHelper = new SQLiteUserHelper(context);
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_USER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PRODUCTS_TABLE_NAME);
        onCreate(db);
    }

    public UserBean queryUserInfo(String userName,String password) {
        UserBean userBean = null;
        String[] columns = {DBConstants.USERNAME,
                            DBConstants.PASSWORD};
        String[] params = {userName,password};
        String where = DBConstants.USERNAME +"=? and " + DBConstants.PASSWORD + "=?";
        Cursor result = null;
        try{
            if (!userName.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
                result = this.getWritableDatabase().query(DBConstants.USER_TABLE_NAME, columns, where, params, null, null, null);
            } else {
                result = this.getWritableDatabase().query(DBConstants.USER_TABLE_NAME, columns, null, null, null, null, null);
            }
            int count = result.getCount();
            if (count != 0) {
                result.moveToFirst();
                userBean = new UserBean(result.getString(0),
                                        result.getString(1));
            } else {
                userBean = new UserBean("", "");
            }
        }finally{
            result.close();
        }
        return userBean;
    }

    public boolean saveUserInfo(String userName,String password) {
        SQLiteDatabase sqldb = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", userName);
        cv.put("password", password);
        sqldb.insert(DBConstants.USER_TABLE_NAME, "name", cv);
        return true;
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DB_NAME);
    }
}
