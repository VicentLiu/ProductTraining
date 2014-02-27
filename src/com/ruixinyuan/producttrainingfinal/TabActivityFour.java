package com.ruixinyuan.producttrainingfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

/*
 *@user vicentliu
 *@time 2013-6-9下午4:04:41
 *@package com.ruixinyuan.producttrainingfinal
 */
public class TabActivityFour extends PreferenceActivity
             implements OnSharedPreferenceChangeListener {

    ListPreference lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
        Drawable d = getResources().getDrawable(R.drawable.divider);
        getListView().setDividerHeight(1);
        getListView().setDivider(d);
        getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        initPreferenceItem();
        SharedPreferences prefs = PreferenceManager
                                  .getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        lp = (ListPreference)findPreference(getString(R.string.download_picture_way));
    }

    private void initPreferenceItem () {
        findPreference(getString(R.string.app_recommend))
        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(TabActivityFour.this, "精彩app推荐", 200).show();
                return false;
            }
        });
        findPreference(getString(R.string.recommend_to_friend))
        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(TabActivityFour.this, "推荐给朋友", 200).show();
                return false;
            }
        });
        findPreference(getString(R.string.feedback))
        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(TabActivityFour.this, "意见反馈", 200).show();
                return false;
            }
        });
        findPreference(getString(R.string.update))
        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(TabActivityFour.this, "检查更新", 200).show();
                return false;
            }
        });
        
        findPreference(getString(R.string.help))
        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(TabActivityFour.this, "需要帮助嘛？请致电本公司", 200).show();
                return false;
            }
        });
        findPreference(getString(R.string.company_info))
        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(TabActivityFour.this, "瑞欣源网络科技有限公司，拥有一个充满朝气的团队，欢迎加入~", 200).show();
                return false;
            }
        });
        findPreference(getString(R.string.telephone))
        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(TabActivityFour.this, "公司电话", 200).show();
                TextView tvTelephone = (TextView)findViewById(R.id.setting_textView_telephone_num);
                String str = tvTelephone.getText().toString();
                Intent phoneCallIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + str));
                startActivity(phoneCallIntent);
                return false;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        if (key == getString(R.string.download_picture_way)) {
            Intent intent = new Intent(getString(R.string.connectivity_action));
            sendBroadcast(intent);
        }
    }
}
