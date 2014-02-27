package com.ruixinyuan.producttrainingfinal.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.ruixinyuan.producttrainingfinal.GroupActivity;
import com.ruixinyuan.producttrainingfinal.R;
import com.ruixinyuan.producttrainingfinal.bean.ProductInfoBean;
import com.ruixinyuan.producttrainingfinal.db.SQLiteProductHelper;
import com.ruixinyuan.producttrainingfinal.utils.download.DownloadProgressListener;
import com.ruixinyuan.producttrainingfinal.utils.download.FileDownloader;
import com.ruixinyuan.producttrainingfinal.utils.net.NetUtils;

/*
 *@user vicentliu
 *@time 2013-6-4下午4:10:07
 *@package com.ruixinyuan.producttraining.services
 */
public class MessageService extends Service {

    //获取消息线程
    private MessageThread msgThread = null;
    //点击查看
    private Intent msgIntent = null;
    private PendingIntent msgPendingIntent = null;
    //通知栏消息
    private int msgNotificationId = 0;
    private Notification msgNotification = null;
    private NotificationManager msgNotificationManager = null;
    private BroadcastReceiver connectionReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo mobileNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (!mobileNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                    Toast.makeText(MessageService.this, getString(R.string.net_connect_off), Toast.LENGTH_LONG).show();
                } else {
                    if ((NetUtils.getCurrentStateOfNetFromPreference(MessageService.this)  == 0)) {
                        if (NetUtils.isMobileConnected(MessageService.this)
                            || NetUtils.isWifiConnected(MessageService.this)) {
//                            Toast.makeText(MessageService.this, getString(R.string.mode_net), Toast.LENGTH_LONG).show();
                            downloadPics();
                        } else {//未联网
                            NetUtils.openMobileNetWork(MessageService.this);
                        }
                    } else if ((NetUtils.getCurrentStateOfNetFromPreference(MessageService.this) == 1)) {
                        if (NetUtils.isWifiConnected(MessageService.this)) {
                            Toast.makeText(MessageService.this, getString(R.string.mode_wifi), Toast.LENGTH_LONG).show();
                            downloadPics();
                        } else {
                            Toast.makeText(MessageService.this, getString(R.string.no_wifi_prompt), Toast.LENGTH_LONG).show();
                        }
                    } else if (NetUtils.getCurrentStateOfNetFromPreference(MessageService.this) == 2){
                        Toast.makeText(MessageService.this, getString(R.string.mode_no_pic), Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.connectivity_action));
        registerReceiver(connectionReceiver, intentFilter);

        msgNotification = new Notification();
        msgNotification.tickerText = getString(R.string.notification_tickertext);
        msgNotification.icon = R.drawable.ic_launcher;
        msgNotification.defaults = Notification.DEFAULT_SOUND;

        msgNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        msgIntent = new Intent(this,GroupActivity.class);//mainActivity为点击后跳转的activity，用来处理详细信息
        msgPendingIntent = PendingIntent.getActivity(this, 0, msgIntent, 0);
         //开启线程
        msgThread = new MessageThread();
        msgThread.isRunning = true;
        msgThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    
    @Override
    public void onDestroy() {
        if (connectionReceiver != null)
            unregisterReceiver(connectionReceiver);
        System.exit(0);
        super.onDestroy();
    }

    /*
     * 从服务器获取数据
     */
    class MessageThread extends Thread {
        boolean isRunning = true;
        List<ProductInfoBean> listProduction = new ArrayList<ProductInfoBean>();
        @Override
        public void run() {
            while (isRunning) {
                try {
                    Thread.sleep(900000); //每隔十五分钟检测一次
                    listProduction = NetUtils.productionDataExist(MessageService.this);
                    int size = listProduction.size();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            String tempStr = getServerMessage() + "  " + listProduction.get(i).getProductName();
                            if (tempStr != null && !"".equalsIgnoreCase(tempStr)) {
                                msgNotification.setLatestEventInfo(MessageService.this,
                                        getString(R.string.notification_tickertext),
                                        tempStr, //TODO：添加获取字串
                                        msgPendingIntent);
                                msgNotificationManager.notify(msgNotificationId, msgNotification);
                                //每次通知完,id递增一下,避免消息覆盖
                                msgNotificationId++;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    ExecutorService threadPool;
    /**
     * 从本地数据库中读取产品的图片链接并下载
     */
    private void downloadPics () {
          SQLiteProductHelper mProductHelper = SQLiteProductHelper.getInstance(getBaseContext());
          List<ProductInfoBean> listProduction = mProductHelper.getAllProductionInfo();
          int listProductionSize = listProduction.size();
          for (int j = 0; j < listProductionSize; j++) {
              ProductInfoBean production = listProduction.get(j);
              List<String> list = new ArrayList<String>();
              list = production.getmListProductionPicsLocalAddr();
              int listSize = list.size();
              for(int i = 0; i < listSize; i++) {
                  final String fileDownloadUrl = list.get(i).toString();
                  int subStringStartIndex = fileDownloadUrl.lastIndexOf("/");
                  final String fileName = fileDownloadUrl.substring(subStringStartIndex + 1);
                  threadPool = Executors.newFixedThreadPool(3);
                  File file = new File(Environment.getExternalStorageDirectory()
                                       + "/"
                                       + fileName);
                  if (!file.exists()) {//文件在本地不存在则下载
                      if (Environment
                          .getExternalStorageState()
                          .equals(Environment.MEDIA_MOUNTED)) {
                          Thread thread = new Thread() {
                              public void run() {
                                  try {
                                      FileDownloader downloader =
                                              new FileDownloader(MessageService.this,
                                                      fileDownloadUrl,
                                                      Environment.getExternalStorageDirectory(),
                                                      3);
                                      downloader.startDownload(new DownloadProgressListener() {
                                          @Override
                                          public void onDownloadSize(int size) {
                                          }
                                      });
                                  } catch (Exception ex) {
                                      ex.printStackTrace();
                                  }
                              }
                           };
                           threadPool.submit(thread); //加入线程
                          }
                      }
                  }
          }
    }
    /*
     * 利用服务器方法获取提示字串
     */
    public String getServerMessage () {
        return "有新产品啦~";
    }
}
