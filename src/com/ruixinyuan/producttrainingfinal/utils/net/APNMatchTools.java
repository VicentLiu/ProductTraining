package com.ruixinyuan.producttrainingfinal.utils.net;
/*
 *@user vicentliu
 *@time 2013-7-3下午1:55:30
 *@package com.ruixinyuan.producttrainingfinal.utils.net
 */
public class APNMatchTools {

    public static class APNNet {
        public static String CMWAP = "cmwap";
        public static String CMNET = "cmnet";
        public static String GWAP_3 = "3gwap";
        public static String GNET_3="3gnet";
        public static String UNIWAP="uniwap";
        public static String UNINET="uninet"; 
    }

    public static String matchAPN (String curName) {
        if ("".equals(curName) || null == curName)
            return "";

        curName = curName.toLowerCase();
        if (curName.startsWith(APNNet.CMWAP))
            return APNNet.CMWAP;
        else if (curName.startsWith(APNNet.CMNET))
            return APNNet.CMNET;
        else if (curName.startsWith(APNNet.GNET_3))
            return APNNet.GNET_3;
        else if (curName.startsWith(APNNet.GWAP_3))
            return APNNet.GWAP_3;
        else if (curName.startsWith(APNNet.UNINET))
            return APNNet.UNINET;
        else if (curName.startsWith(APNNet.UNIWAP))
            return APNNet.UNIWAP;
        else if (curName.startsWith("default"))
            return "default";

        return "";
    }
}
