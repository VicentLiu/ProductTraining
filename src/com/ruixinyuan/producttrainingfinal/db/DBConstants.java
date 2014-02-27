package com.ruixinyuan.producttrainingfinal.db;
/*
 *@user vicentliu
 *@time 2013-6-13下午3:24:05
 *@package com.ruixinyuan.producttrainingfinal.db
 */
public class DBConstants {

    /**
     * <p>产品表的常量
     */
    public static String PRODUCT_DB_NAME = "products.db";
    public static String PRODUCTS_TABLE_NAME = "products";
    public final static String PRODUCT_ID = "_id";
    public final static String PRODUCT_BRAND = "_brand";
    public final static String PRODUCT_SERIES = "_series";
    public final static String PRODUCT_NAME = "_product_name";
    public final static String PRODUCT_SHORT_DESC = "_product_short_desc";
    public final static String PRODUCT_DESCRIPTION = "_product_description";
    public final static String PRODUCT_PUBLISH_TIME = "_publish_time";
    public final static String PRODUCT_PICTURE_ONE = "_product_picture_one";
    public final static String PRODUCT_PICTURE_TWO = "_product_picture_two";
    public final static String PRODUCT_PICTURE_THREE = "_product_picture_three";
    public final static String PRODUCT_PICTURE_FOUR = "_product_picture_four";
    public final static String PRODUCT_PICTURE_FIVE = "_product_picture_five";
    public final static String PRODUCT_PICTURE_SIX = "_product_picture_six";

    /**
     * <p>users表的常量
     */
    public static String USER_TABLE_NAME = "users";
    public final static String USERNAME = "_username";
    public final static String PASSWORD = "_password";
    /**
     * 断点续传数据库
     */
    public static final String DOWNLOAD_DB_NAME = "filedownloadlog.db";
    public static final int DOWNLOAD_DB_VERSION = 1;
    public static String DOWNLOAD_TABLE_NAME = "pic_download";
    /**
     * 销售技巧数据库
     */
    public static final String SALE_SKILL_DB_NAME = "sale_skills.db";
    public static final String SALE_SKILL_TABLE_NAME = "sale_skill";
    public static final String SALE_SKILL_ID = "_id";
    public static final String SALE_SKILL_TITLE = "_title";
    public static final String SALE_SKILL_CONTENT = "_content";
}
