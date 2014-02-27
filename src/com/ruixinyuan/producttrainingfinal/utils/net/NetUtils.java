package com.ruixinyuan.producttrainingfinal.utils.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.ruixinyuan.producttrainingfinal.R;
import com.ruixinyuan.producttrainingfinal.bean.ProductInfoBean;
import com.ruixinyuan.producttrainingfinal.db.SQLiteProductHelper;
import com.ruixinyuan.producttrainingfinal.utils.EncryptionAndDecryption;

/*
 *@user vicentliu
 *@time 2013-6-21上午9:46:08
 *@package com.ruixinyuan.producttrainingfinal.utils.net
 */
public class NetUtils {

    public static String connServerForResult (String strUrl) {
        HttpGet httpRequest = new HttpGet(strUrl);
        String strResult = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return strResult;
    }

    public static List<ProductInfoBean> productionDataExist (Context context) {
        List<ProductInfoBean> resultList = new ArrayList<ProductInfoBean>();
        String strUrl = context.getString(R.string.api_url)
                + "getData"
                + "&type=" + context.getString(R.string.industry_type)
                + "&num=5"
                + "&auth="
                + EncryptionAndDecryption
                  .MD5(context.getString(R.string.industry_type) + context.getString(R.string.app_key));
         String strResult = NetUtils.connServerForResult(strUrl);
         List<ProductInfoBean> templist = new ArrayList<ProductInfoBean>();
         try {
             templist = JsonUtils.parseProductionJsonMulti(context, strResult);
         } catch (Exception ex) {
             ex.printStackTrace();
             Toast.makeText(context, "暂无更新", Toast.LENGTH_LONG).show();
         }finally {
             int listSize = templist.size();
             if (listSize > 0) {
                 SQLiteProductHelper pHelper = SQLiteProductHelper.getInstance(context);
                 int count = templist.size();
                 for(int i = 0; i < count;i++) {
                     if (pHelper.saveProductionInfo(templist.get(i)) == 1) {
                         resultList.add(templist.get(i));
                     }
                 }
                 return resultList;
             }
         }
         return null;
    }

    /**
     * 获取当前app网络设置状态
     * @param context
     */
    public static int getCurrentStateOfNetFromPreference (Context context) {
        if (context != null) {
            SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(context);
            String str = pre.getString(context.getString(R.string.download_picture_way), "1"); //默认为wifi
            int i = Integer.valueOf(str);
            return i;
        }
        return -1;
    }

    /**
     * 判断是否有网络链接
     * @param context
     * @return
     */
    public static boolean isNetConnected (Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager)
                                     context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否有wifi网络状态
     * @param context
     * @return
     */
    public static boolean isWifiConnected (Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager)
                                     context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断手机是否联网2G、3G
     * @param context
     * @return
     */
    public static boolean isMobileConnected (Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager)
                                     context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型
     * @param context
     * @return
     */
    public static int getConnectedType (Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager)
                                     context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return networkInfo.getType();
            }
        }
        return -1;
    }

    public static void openMobileNetWork (Context context) {
        Intent intent = new Intent("/");
        ComponentName comp = new ComponentName("com.android.settings",
                                               "com.android.settings.WirelessSettings");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
