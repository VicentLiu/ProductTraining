package com.ruixinyuan.producttrainingfinal.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.ruixinyuan.producttrainingfinal.bean.ProductInfoBean;
import com.ruixinyuan.producttrainingfinal.utils.BitmapUtils;
import com.ruixinyuan.producttrainingfinal.utils.StringUtils;

/*
 *@user vicentliu
 *@time 2013-6-4下午3:21:09
 *@package com.ruixinyuan.producttraining.db
 */
public class SQLiteProductHelper extends SQLiteOpenHelper {

    private static SQLiteProductHelper mDBHelper = null;
    private static int DB_VERSION = 1;
    private static final String SQL_TABLE_MAIN_CREATE = "create table if not exists " + DBConstants.PRODUCTS_TABLE_NAME + "("
                                                 + DBConstants.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                 + DBConstants.PRODUCT_BRAND + " text,"
                                                 + DBConstants.PRODUCT_SERIES + " text,"
                                                 + DBConstants.PRODUCT_NAME + " text,"
                                                 + DBConstants.PRODUCT_SHORT_DESC + " text,"
                                                 + DBConstants.PRODUCT_DESCRIPTION + " text,"
                                                 + DBConstants.PRODUCT_PUBLISH_TIME + " text,"
                                                 + DBConstants.PRODUCT_PICTURE_ONE + " text,"
                                                 + DBConstants.PRODUCT_PICTURE_TWO + " text,"
                                                 + DBConstants.PRODUCT_PICTURE_THREE + " text,"
                                                 + DBConstants.PRODUCT_PICTURE_FOUR + " text,"
                                                 + DBConstants.PRODUCT_PICTURE_FIVE + " text,"
                                                 + DBConstants.PRODUCT_PICTURE_SIX + " text)";

    public SQLiteProductHelper(Context context) {
        super(context, DBConstants.PRODUCT_DB_NAME, null, DB_VERSION);
    }

    public static synchronized SQLiteProductHelper getInstance(Context context) {
        if (mDBHelper == null) {
            mDBHelper = new SQLiteProductHelper(context);
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_MAIN_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PRODUCTS_TABLE_NAME);
        onCreate(db);
    }

    /**
     * 查询产品信息
     * @param productName 查询条件，产品名称
     * @return
     */
    public ProductInfoBean queryProductInfo(String productName) {
        ProductInfoBean piBean = null;
        String[] columns = {DBConstants.PRODUCT_ID,
                            DBConstants.PRODUCT_BRAND,
                            DBConstants.PRODUCT_SERIES,
                            DBConstants.PRODUCT_NAME,
                            DBConstants.PRODUCT_DESCRIPTION,
                            DBConstants.PRODUCT_SHORT_DESC,
                            DBConstants.PRODUCT_PUBLISH_TIME,
                            DBConstants.PRODUCT_PICTURE_ONE,
                            DBConstants.PRODUCT_PICTURE_TWO,
                            DBConstants.PRODUCT_PICTURE_THREE,
                            DBConstants.PRODUCT_PICTURE_FOUR,
                            DBConstants.PRODUCT_PICTURE_FIVE,
                            DBConstants.PRODUCT_PICTURE_SIX};
        String[] params = {productName};
        String where = DBConstants.PRODUCT_NAME + "=?";
        Cursor result = null;
        try{
            if (productName != null) {
                result = this.getWritableDatabase().query(DBConstants.PRODUCTS_TABLE_NAME, columns, where, params, null, null, null);
            } else {
                result = this.getWritableDatabase().query(DBConstants.PRODUCTS_TABLE_NAME, columns, null, null, null, null, null);
            }
            if (!result.equals(null)) {
                result.moveToFirst();
                piBean = new ProductInfoBean();
                piBean.setProductID(result.getInt(0));
                piBean.setBrand(result.getString(1));
                piBean.setSeries(result.getString(2));
                piBean.setProductName(result.getString(3));
                piBean.setProductIntro(result.getString(4));
                piBean.setProductShortdest(result.getString(5));
                piBean.setPublishTime(result.getString(6));
                List<String> listPicsAddr = new ArrayList<String>();
                List<Bitmap> listPics = new ArrayList<Bitmap>();
                for (int j = 7; j < 13; j++) {
                    if (!result.getString(j).startsWith("no pic")) {
                        String fileDownloadUrl = result.getString(j);
                        int subStringStartIndex = fileDownloadUrl.lastIndexOf("/");
                        String fileName = fileDownloadUrl.substring(subStringStartIndex + 1);
                        listPicsAddr.add(fileName);
                        Bitmap bitmap = BitmapUtils.getBitmapFromSDCard(fileName);
                        listPics.add(bitmap);
                    }
                }
                piBean.setmListProductionPicsLocalAddr(listPicsAddr);
                piBean.setListProductionPics(listPics);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (!result.equals(null))
                    result.close();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return piBean;
    }

    /**
     * 根据产品id查询
     * @param productId
     * @return
     */
    private ProductInfoBean queryProductInfoById(String productId) {
        ProductInfoBean piBean = null;
        String[] columns = {DBConstants.PRODUCT_ID};
        String[] params = {productId};
        String where = DBConstants.PRODUCT_ID + "=?";
        Cursor result = null;
        try{
            if (productId != null) {
                result = this.getWritableDatabase().query(DBConstants.PRODUCTS_TABLE_NAME, columns, where, params, null, null, null);
            } else {
                result = this.getWritableDatabase().query(DBConstants.PRODUCTS_TABLE_NAME, columns, null, null, null, null, null);
            }
            if (!result.equals(null)) {
                result.moveToFirst();
                piBean = new ProductInfoBean();
                piBean.setProductID(result.getInt(0));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (!result.equals(null))
                    result.close();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return piBean;
    }

    /**
     * 遍历当前数据库中产品
     * @return
     */
    public List<ProductInfoBean> getAllProductionInfo () {
        List<ProductInfoBean> listProdcution = new ArrayList<ProductInfoBean>();
        ProductInfoBean production = null;
        String[] columns = {
                DBConstants.PRODUCT_ID,
                DBConstants.PRODUCT_BRAND,
                DBConstants.PRODUCT_SERIES,
                DBConstants.PRODUCT_NAME,
                DBConstants.PRODUCT_DESCRIPTION,
                DBConstants.PRODUCT_SHORT_DESC,
                DBConstants.PRODUCT_PUBLISH_TIME,
                DBConstants.PRODUCT_PICTURE_ONE,
                DBConstants.PRODUCT_PICTURE_TWO,
                DBConstants.PRODUCT_PICTURE_THREE,
                DBConstants.PRODUCT_PICTURE_FOUR,
                DBConstants.PRODUCT_PICTURE_FIVE,
                DBConstants.PRODUCT_PICTURE_SIX};
        Cursor result = this.getReadableDatabase()
                            .query(DBConstants.PRODUCTS_TABLE_NAME, columns, null, null, null, null, DBConstants.PRODUCT_ID + " DESC");
        try {
            if (result.moveToFirst()) {
                do {
                    production = new ProductInfoBean();
                    production.setProductID(result.getInt(0));
                    production.setBrand(result.getString(1));
                    production.setSeries(result.getString(2));
                    production.setProductName(result.getString(3));
                    production.setProductIntro(result.getString(4));
                    production.setProductShortdest(result.getString(5));
                    production.setPublishTime(result.getString(6));
                    List<String> listPicsAddr = new ArrayList<String>();
                    for (int j = 7; j < 13; j++) {
                        if (!result.getString(j).startsWith("no pic")) {
                            String fileDownloadUrl = result.getString(j);
//                            int subStringStartIndex = fileDownloadUrl.lastIndexOf("/");
//                            String fileName = fileDownloadUrl.substring(subStringStartIndex + 1);
//                            listPicsAddr.add(fileName);
                            listPicsAddr.add(fileDownloadUrl);
                        }
                    }
                    production.setmListProductionPicsLocalAddr(listPicsAddr);
                    listProdcution.add(production);
                } while (result.moveToNext());
            }
        } finally {
            result.close();
        }
        return listProdcution;
    }

    /**
     * 方便存储图片而设计的
     * @param list
     * @param currentIndex
     * @return
     */
    @SuppressWarnings("unused")
    private String returnBitmapLocalAddrOrNull (List<String> list, int currentIndex) {
        int maxIndex = list.size();
        String bitmapLocalAddr = null;
        if (maxIndex == 6) {
            for (int i = 1; i <= maxIndex; i++) {
                    bitmapLocalAddr = list.get(currentIndex);
                    return bitmapLocalAddr;
                }
        } else if (maxIndex < 6) {
            for (int j = 1; j <= 6; j++) {
                if (j < maxIndex && currentIndex < maxIndex)
                    bitmapLocalAddr = list.get(currentIndex);
                else
                    bitmapLocalAddr = "no pic";
                return bitmapLocalAddr;
            }
        }
        return "no pic";
    }
    /**
     * 保存产品信息
     * @param pib
     */
    public int saveProductionInfo (ProductInfoBean pib) {
        List<String> listBitmapLoaclAddr = pib.getmListProductionPicsLocalAddr();
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            if (queryProductInfoById(pib.getProductID()+"") != null 
                && (queryProductInfoById(pib.getProductID()+"").getProductID()
                    != pib.getProductID())) {//产品未入库，则保存
                cv.put(DBConstants.PRODUCT_ID, pib.getProductID());
                cv.put(DBConstants.PRODUCT_BRAND, pib.getBrand());
                cv.put(DBConstants.PRODUCT_SERIES, pib.getSeries());
                cv.put(DBConstants.PRODUCT_NAME, StringUtils.substitudeString(pib.getProductName()));
                cv.put(DBConstants.PRODUCT_SHORT_DESC, pib.getProductShortdest());
                cv.put(DBConstants.PRODUCT_DESCRIPTION, StringUtils.substitudeString(pib.getProductFunction()));
                cv.put(DBConstants.PRODUCT_PUBLISH_TIME, pib.getPublishTime());
                cv.put(DBConstants.PRODUCT_PICTURE_ONE, returnBitmapLocalAddrOrNull(listBitmapLoaclAddr, 0));
                cv.put(DBConstants.PRODUCT_PICTURE_TWO, returnBitmapLocalAddrOrNull(listBitmapLoaclAddr, 1));
                cv.put(DBConstants.PRODUCT_PICTURE_THREE, returnBitmapLocalAddrOrNull(listBitmapLoaclAddr, 2));
                cv.put(DBConstants.PRODUCT_PICTURE_FOUR, returnBitmapLocalAddrOrNull(listBitmapLoaclAddr, 3));
                cv.put(DBConstants.PRODUCT_PICTURE_FIVE, returnBitmapLocalAddrOrNull(listBitmapLoaclAddr, 4));
                cv.put(DBConstants.PRODUCT_PICTURE_SIX, returnBitmapLocalAddrOrNull(listBitmapLoaclAddr, 5));
                db.insert(DBConstants.PRODUCTS_TABLE_NAME, null, cv);
                return 1;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return -1;
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DBConstants.PRODUCT_DB_NAME);
    }
}
