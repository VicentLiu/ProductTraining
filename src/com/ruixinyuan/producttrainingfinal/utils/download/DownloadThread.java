package com.ruixinyuan.producttrainingfinal.utils.download;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 *@user vicentliu
 *@time 2013-6-19上午10:31:43
 *@package com.ruixinyuan.producttrainingfinal.utils.download
 */
public class DownloadThread extends Thread{
//    private static final String TAG = "DownloadThread";
    private File saveFile;
    private URL downUrl;
    private int block;
    /*
     * 下载开始位置
     */
    private int threadId = -1;
    private int downLength;
    private boolean isFinish = false;
    private FileDownloader downloader;

    public DownloadThread(FileDownloader downloader, URL downUrl, File saveFile, int block, int downLength, int threadId) {
        this.downUrl = downUrl;
        this.saveFile = saveFile;
        this.block = block;
        this.downloader = downloader;
        this.threadId = threadId;
        this.downLength = downLength;
    }

    @Override
    public void run() {
        if (downLength < block) { //未完成下载
            try {
                HttpURLConnection conn = (HttpURLConnection)downUrl.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept",
                        "image/gif, image/jpeg, image/pjpeg,image/pjpeg," +
                        " application/x-shockwave-flash, application/xaml+xml," +
                        " application/vnd.ms-xpsdocument, application/x-ms-xbap," +
                        " application/x-ms-application, application/vnd.ms-excel," +
                        " application/vnd.ms-powerpoint, application/msword newValue");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Referer", downUrl.toString());
                conn.setRequestProperty("Charset", "UTF-8");
                int startPos = block * (threadId - 1) + downLength; //开始位置
                int endPos = block * threadId - 1; //结束位置
                conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0;" +
                                                        " .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30;" +
                                                        " .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                conn.setRequestProperty("Connection", "Keep-Alive");

                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int offset = 0;
//                print("Thread" + threadId + "start dowload pos" + startPos);
                RandomAccessFile threadFile = new RandomAccessFile(saveFile, "rwd");
                threadFile.seek(startPos);

                while ((offset = is.read(buffer, 0, 1024)) != -1) {
                    threadFile.write(buffer, 0, offset);
                    downLength += offset;
                    downloader.update(threadId, downLength);
                    downloader.append(offset);
                }

                threadFile.close();
                is.close();
//                print("Thread" + threadId + "download finished");
                isFinish = true;
            } catch (Exception ex) {
                downLength = -1;
//                print("Thread" + threadId + ":" + ex);
            }
        }
    }


    /**
     * 
     * @return
     */
    public boolean isFinish() {
        return isFinish;
    }

    /**
     * 已经下载的内容大小
     * @return =-1,代表下载失败
     */
    public long getDownLength() {
        return downLength;
    }

//    private static void print(String msg) {
//        Log.i(TAG, msg);
//    }
}
