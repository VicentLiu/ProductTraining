package com.ruixinyuan.producttrainingfinal.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
/*
 *@user vicentliu
 *@time 2013-6-25下午1:26:32
 *@package com.ruixinyuan.producttrainingfinal.utils
 */
public class SIMCardInfo {
   /**
     * TelephonyManager提供设备上获取通讯服务信息的入口。 应用程序可以使用这个类方法确定的电信服务商和国家 以及某些类型的用户访问信息。
     * 应用程序也可以注册一个监听器到电话收状态的变化。不需要直接实例化这个类
     * 使用Context.getSystemService(Context.TELEPHONY_SERVICE)来获取这个类的实例。
     */
    TelephonyManager mTelephonyManager;
    /**
     * 国际移动用户识别码
     */
    private String IMSI;

    public SIMCardInfo(Context context) {
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * @return 当前设置的电话号码
     */
    public String getNativePhoneNumber() {
        String telephoneNum = null;
        telephoneNum = mTelephonyManager.getLine1Number();
        return telephoneNum;
    }

    /**
     * 获取手机服务商信息</BR>
     * 需要加入权限<uses-permissionandroid:name="android.permission.READ_PHONE_STATE"/></BR>
     * @return
     */
    public String getProviderName() {
        String providerName = null;
        // 返回唯一的用户ID
        IMSI = mTelephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            providerName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            providerName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            providerName = "中国电信";
        }
        return providerName;
    }
}
