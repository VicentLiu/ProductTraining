package com.ruixinyuan.producttrainingfinal.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ruixinyuan.producttrainingfinal.bean.SaleSkillBean;

/*
 *@user vicentliu
 *@time 2013-6-28下午3:20:54
 *@package com.ruixinyuan.producttrainingfinal.db
 */
public class SQLiteSalesSkillOpenHelper extends SQLiteOpenHelper{

    private static SQLiteSalesSkillOpenHelper mDBHelper = null;
    private static int DB_VERSION = 1;
    private static final String SQL_TABLE_SALES_SKILL_CREATE = "create table if not exists " + DBConstants.SALE_SKILL_TABLE_NAME + "("
                                                              + DBConstants.SALE_SKILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                              + DBConstants.SALE_SKILL_TITLE + " text,"
                                                              + DBConstants.SALE_SKILL_CONTENT + " text)";
    public SQLiteSalesSkillOpenHelper(Context context) {
        super(context,DBConstants.SALE_SKILL_DB_NAME,null,DB_VERSION);
    }

    public static synchronized SQLiteSalesSkillOpenHelper getInstance(Context context) {
        if (mDBHelper == null) {
            mDBHelper = new SQLiteSalesSkillOpenHelper(context);
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_SALES_SKILL_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DBConstants.SALE_SKILL_DB_NAME);
        onCreate(db);
    }

    /**
     * 查询销售技巧信息
     * @param saleSkillTitle 查询条件，销售技巧名称
     * @return
     */
    public SaleSkillBean querySaleSkillInfo(String saleSkillTitle) {
        SaleSkillBean saleSkillBean = null;
        String[] columns = {DBConstants.SALE_SKILL_ID,
                            DBConstants.SALE_SKILL_TITLE,
                            DBConstants.SALE_SKILL_CONTENT};
        String[] params = {saleSkillTitle};
        String where = DBConstants.SALE_SKILL_TITLE + "=?";
        Cursor result = null;
        try{
            if (saleSkillTitle != null) {
                result = this.getWritableDatabase().query(DBConstants.SALE_SKILL_TABLE_NAME, columns, where, params, null, null, null);
            } else {
                result = this.getWritableDatabase().query(DBConstants.SALE_SKILL_TABLE_NAME, columns, null, null, null, null, null);
            }
            if (!result.equals(null)) {
                result.moveToFirst();
                saleSkillBean = new SaleSkillBean();
                saleSkillBean.setSaleSkillId(result.getInt(0));
                saleSkillBean.setSaleSkillTitle(result.getString(1));
                saleSkillBean.setSaleSkillContent(result.getString(2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            result.close();
        }
        return saleSkillBean;
    }

    /**
     * 遍历当前数据库中销售技巧
     * @return
     */
    public List<SaleSkillBean> getAllSaleSkill () {
        List<SaleSkillBean> listSaleSkill = new ArrayList<SaleSkillBean>();
        SaleSkillBean saleSkill = null;
        String[] columns = {
                DBConstants.SALE_SKILL_ID,
                DBConstants.SALE_SKILL_TITLE,
                DBConstants.SALE_SKILL_CONTENT};
        Cursor result = this.getReadableDatabase()
                            .query(DBConstants.SALE_SKILL_TABLE_NAME, columns, null, null, null, null, null);
        try {
            if (result.moveToFirst()) {
                do {
                    saleSkill = new SaleSkillBean();
                    saleSkill.setSaleSkillId(result.getInt(0));
                    saleSkill.setSaleSkillTitle(result.getString(1));
                    saleSkill.setSaleSkillContent(result.getString(2));
                    listSaleSkill.add(saleSkill);
                } while (result.moveToNext());
            }
        } finally {
            result.close();
        }
        return listSaleSkill;
    }
}
