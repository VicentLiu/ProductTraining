package com.ruixinyuan.producttrainingfinal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabWidget;
import android.widget.TextView;

import com.ruixinyuan.producttrainingfinal.bean.TabItemBean;
import com.ruixinyuan.producttrainingfinal.utils.TabHostActivity;

/*
 *@user vicentliu
 *@time 2013-6-8下午2:27:59
 *@package com.ruixinyuan.producttrainingfinal
 */
public class GroupActivity extends TabHostActivity {

    List<TabItemBean> mTabItems;
    LayoutInflater mLayoutInflater;
    /**在初始化TabWidget前调用
     * 和TabWidget有关的必须在这里初始化*/
    @Override
    protected void prepare() {
        TabItemBean tabItemOne = new TabItemBean(getString(R.string.tab_one_title),
                                                 R.drawable.tabone_host_item_icon,
                                                 R.drawable.tab_item_bg,
                                                 new Intent(this, TabActivityOne.class));
        TabItemBean tabItemTwo = new TabItemBean(getString(R.string.tab_two_title),
                                                R.drawable.tabtwo_host_item_icon,
                                                R.drawable.tab_item_bg,
                                                new Intent(this, TabActivityTwo.class));
        TabItemBean tabItemThree = new TabItemBean(getString(R.string.tab_three_title),
                                                R.drawable.tabthree_host_item_icon,
                                                R.drawable.tab_item_bg,
                                                new Intent(this, TabActivityThree.class));
        TabItemBean tabItemFour = new TabItemBean(getString(R.string.tab_four_title),
                                                R.drawable.tabfour_host_item_icon,
                                                R.drawable.tab_item_bg,
                                                new Intent(this, TabActivityFour.class));
        mTabItems = new ArrayList<TabItemBean>();
        mTabItems.add(tabItemOne);
        mTabItems.add(tabItemTwo);
        mTabItems.add(tabItemThree);
        mTabItems.add(tabItemFour);

        //设置分割线
        TabWidget tabWidget = getTabWidget();
        tabWidget.setDividerDrawable(R.drawable.tab_divider);

        mLayoutInflater = getLayoutInflater();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setCurrentTab(0);
        Intent intent = new Intent(getString(R.string.connectivity_action));
        sendBroadcast(intent);
    }

    
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**tab的title，icon，边距设定等等*/
    @Override
    protected void setTabItemTextView(TextView textView, int position) {
        textView.setPadding(15, 5, 15, 5);
        textView.setText(mTabItems.get(position).getTitle());
        textView.setBackgroundResource(mTabItems.get(position).getBg());
        textView.setCompoundDrawablesWithIntrinsicBounds(0, mTabItems.get(position).getIcon(), 0, 0);
    }

    /**tab唯一的id*/
    @Override
    protected String getTabItemId(int position) {
        return mTabItems.get(position).getTitle();
    }

    @Override
    protected Intent getTabItemIntent(int position) {
        return mTabItems.get(position).getIntent();
    }

    @Override
    protected int getTabItemCount() {
        return mTabItems.size();
    }

    /**自定义头部文件*/
    @Override
    protected View getTop() {
        return mLayoutInflater.inflate(R.layout.tabhost_topview, null);
    }
}
