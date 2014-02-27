package com.ruixinyuan.producttrainingfinal.bean;

import android.content.Intent;
/*
 *@user vicentliu
 *@time 2013-6-8下午2:39:51
 *@package com.ruixinyuan.producttrainingfinal.bean
 */
public class TabItemBean {
    private String title;
    private int icon;
    private int bg;
    private Intent intent;

    public TabItemBean(String title, int icon, int bg, Intent intent) {
        super();
        this.title = title;
        this.icon = icon;
        this.bg = bg;
        this.intent = intent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

}
