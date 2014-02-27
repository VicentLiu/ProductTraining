package com.ruixinyuan.producttrainingfinal.utils;
/*
 *@user vicentliu
 *@time 2013-6-27下午4:17:04
 *@package com.ruixinyuan.producttrainingfinal.utils
 */
public class StringUtils {

    public static String substitudeString (String str) {
        if (!str.equals("")) {
            str = str.replace("<span>", "");
            str = str.replace("</span>", "");
            str = str.replace("<br />", "");
            str = str.replace("<p>", "");
            str = str.replace("</p>", "");
        }
        return str;
    }
}
