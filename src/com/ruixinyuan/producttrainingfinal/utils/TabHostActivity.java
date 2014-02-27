package com.ruixinyuan.producttrainingfinal.utils;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.ruixinyuan.producttrainingfinal.R;

/*
 *@user vicentliu
 *@time 2013-6-8上午10:24:19
 *@package com.ruixinyuan.producttrainingfinal.utils
 *此类可以作为一个工具类，在工程中可以建立tabHost
 */
public abstract class TabHostActivity extends TabActivity {

    private TabHost mTabHost;
    private TabWidget mTabWidget;
    private LayoutInflater mLayoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Tabhost);
        setContentView(R.layout.tab_host);
        mLayoutInflater = getLayoutInflater();
        mTabHost = getTabHost();
        mTabWidget = getTabWidget();
        prepare();
        initTop();
        initTabSpec();
    }

    private void initTop() {
        View child = getTop();
        LinearLayout l = (LinearLayout)findViewById(R.id.tab_top);
        l.addView(child);
    }

    private void initTabSpec() {
        int count = getTabItemCount();
        for (int i = 0; i < count; i++) {
            View tabItem = mLayoutInflater.inflate(R.layout.tab_item, null);
            TextView tabItemTv = (TextView)tabItem.findViewById(R.id.tab_item_textview);
            setTabItemTextView(tabItemTv, i);
            String tabItemId = getTabItemId(i);
            TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
            tabSpec.setIndicator(tabItem);
            tabSpec.setContent(getTabItemIntent(i));
            mTabHost.addTab(tabSpec);
        }
    }

    //页面初始化之前调用
    protected void prepare() {
        //todo:override it
    }
    //自定义头部布局
    protected View getTop() {
        return null;
    }

    protected int getTabCount() {
        return mTabHost.getTabWidget().getTabCount();
    }

    //设置tabItem的图标和标题等
    abstract protected void setTabItemTextView(TextView textView,int position);
    abstract protected String getTabItemId(int position);
    abstract protected Intent getTabItemIntent(int position);
    abstract protected int getTabItemCount();

    protected void setCurrentTab(int index) {
        mTabHost.setCurrentTab(index);
    }

    protected void focusCurrentTab(int index) {
        mTabWidget.focusCurrentTab(index);
    }
}
