package com.ruixinyuan.producttrainingfinal;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruixinyuan.producttrainingfinal.bean.ProductInfoBean;
import com.ruixinyuan.producttrainingfinal.db.SQLiteProductHelper;
import com.ruixinyuan.producttrainingfinal.utils.EncryptionAndDecryption;
import com.ruixinyuan.producttrainingfinal.utils.net.JsonUtils;
import com.ruixinyuan.producttrainingfinal.utils.net.NetUtils;

/*
 *@user vicentliu
 *@time 2013-6-19下午2:07:11
 *@package com.ruixinyuan.producttrainingfinal
 */
public class DownloadActivity extends Activity {
    EditText mDownloadPathText;
    TextView mResultView;
    ProgressBar mProgressBar;
    Button mButton;
    /**
     * 当Handler被创建会关联到创建它的当前线程的消息队列，该类用于往消息队列发送消息
     * 消息队列中的消息由当前线程内部进行处理
     */
//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//            case 1:
//                mProgressBar.setProgress(msg.getData().getInt("size"));
//                float num = (float)mProgressBar.getProgress()/(float)mProgressBar.getMax();
//                int result = (int)(num * 100);
//                mResultView.setText(result + "%");
//
//                if (mProgressBar.getProgress() == mProgressBar.getMax()) {
//                    Toast.makeText(DownloadActivity.this, "success", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case -1:
//                Toast.makeText(DownloadActivity.this, "error", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mDownloadPathText = (EditText)findViewById(R.id.path);
        mProgressBar = (ProgressBar)findViewById(R.id.downloadbar);
        mResultView = (TextView)findViewById(R.id.resultView);
        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String path = mDownloadPathText.getText().toString();
                String strUrl =  "http://api.rxynet.com/index.php?r=api/"
                               + "getData"
                               + "&type=2"
                               + "&num=2"
                               + "&auth="
                               + EncryptionAndDecryption
                                 .MD5("2" +"8aURejZBbV659OkjgTXwFhMFRYEtlOGrGfpoZEpTz68LCd8kgUtU2jegLf1RwN");
                String strResult = NetUtils.connServerForResult(strUrl);
                List<ProductInfoBean> list = new ArrayList<ProductInfoBean>();
                try {
//                    ProductInfoBean pib = new ProductInfoBean();
//                    pib = JsonUtils.parseProductionJson(DownloadActivity.this,strResult);
//                    pib.setProductName("2");
//                    pib.setBrand("2");
                    list = JsonUtils.parseProductionJsonMulti(DownloadActivity.this, strResult);
                } finally {
                    SQLiteProductHelper pHelper = SQLiteProductHelper.getInstance(DownloadActivity.this);
                    for(int i = 0; i < list.size();i++) {
                        pHelper.saveProductionInfo(list.get(i));
                    }
                }
//                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    download(path, Environment.getExternalStorageDirectory());
//                } else {
//                    Toast.makeText(DownloadActivity.this, "sdcard error", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
    /**
     * 主线程(UI线程)
     * 对于显示控件的界面更新只是由UI线程负责，如果是在非UI线程更新控件的属性值，更新后的显示界面不会反映到屏幕上 
     * @param path
     * @param saveDir
     */
//    private void download(final String path,final File saveDir) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FileDownloader downloader = new FileDownloader(DownloadActivity.this, path, saveDir, 3);
//                mProgressBar.setMax(downloader.getFileSize());//设置进度条的最大刻度为文件的长度
//                try {
//                    downloader.startDownload(new DownloadProgressListener() {
//                        @Override
//                        public void onDownloadSize(int size) {
//                            Message msg = new Message();
//                            msg.what = 1;
//                            msg.getData().putInt("size", size);
//                            mHandler.sendMessage(msg);
//                        }
//                    });
//                } catch (Exception ex) {
//                    mHandler.obtainMessage(-1).sendToTarget();
//                }
//            }
//        }).start();
//    }
}
