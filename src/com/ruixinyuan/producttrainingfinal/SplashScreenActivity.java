package com.ruixinyuan.producttrainingfinal;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ruixinyuan.producttrainingfinal.db.DBConstants;
import com.ruixinyuan.producttrainingfinal.db.DBUtils;
import com.ruixinyuan.producttrainingfinal.services.MessageService;
import com.ruixinyuan.producttrainingfinal.utils.net.NetUtils;

/*
 *@user vicentliu
 *@time 2013-6-13下午1:55:00
 *@package com.ruixinyuan.producttrainingfinal
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        String str = getResources().getString(R.string.current_version);
        str = String.format(str, 100);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.activity_splash);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
            .detectDiskReads().detectDiskWrites().detectNetwork()
            .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
            .build());
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pinfo = pm.getPackageInfo("com.ruixinyuan.producttrainingfinal", 0);
            TextView tvVersion = (TextView)findViewById(R.id.textViewVersion);
            tvVersion.setText("Version " + pinfo.versionName);
            
        } catch (NameNotFoundException ex) {
            ex.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, GroupActivity.class);
                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, 1000);
    }

    @Override
    protected void onStart() {
        NetUtils.productionDataExist(SplashScreenActivity.this);
        Intent intentService = new Intent(SplashScreenActivity.this, MessageService.class);
        SplashScreenActivity.this.startService(intentService);
        if (!DBUtils.isDatabaseExists(DBConstants.SALE_SKILL_DB_NAME)) {
            try {
                DBUtils.copyLocalSaleSkillDatabase(SplashScreenActivity.this, DBConstants.SALE_SKILL_DB_NAME);
            } catch (Exception ex) {
                throw new Error("Error copying database");
            }
        }
        if (!DBUtils.isDatabaseExists(DBConstants.PRODUCT_DB_NAME)) {
            try {
                DBUtils.copyLocalProductionDatabase(SplashScreenActivity.this, DBConstants.PRODUCT_DB_NAME);
            } catch (Exception ex) {
                throw new Error("Error copying database");
            }
        }
        super.onStart();
    }


}
