package com.ruixinyuan.producttrainingfinal.utils.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.widget.Toast;

/*
 *@user vicentliu
 *@time 2013-6-19上午10:28:28
 *@package com.ruixinyuan.producttrainingfinal.utils.download
 */
public class FileDownloader {
    private Context mContext;
    private FileService fileService;
    //已下载文件长度
    private int downloadSize = 0;
    //原始文件长度
    private int fileSize = 0;
    //线程数
    private DownloadThread[] threads;
    //本地保存文件
    private File saveFile;
    //缓存各线程下载的长度
    private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
    //每条线程下载的长度
    private int block;
    //下载路径
    private String downloadUrl;

    /**
     * 获取线程数
     * @return
     */
    public int getThreadSize() {
        return threads.length;
    }

    /**
     * 获取文件大小
     * @return
     */
    public int getFileSize() {
        return fileSize;
    }
    /**
     * 累计已下载大小
     * @param size
     */
    protected synchronized void append(int size) {
        downloadSize += size;
    }
    /**
     * 更新指定线程最后下载的位置
     * @param threadId
     * @param pos
     */
    protected synchronized void update(int threadId, int pos) {
        this.data.put(threadId, pos);
        this.fileService.update(this.downloadUrl, this.data);
    }
    /**
     * 构建文件下载器
     * @param context
     * @param downloadUrl
     * @param fileSaveDir
     * @param threadNum
     */
    public FileDownloader(Context context,String downloadUrl,File fileSaveDir,int threadNum){
        try {
            this.mContext = context;
            this.downloadUrl = downloadUrl;
            this.fileService = new FileService(mContext);
            URL url = new URL(this.downloadUrl);
            if (!fileSaveDir.exists())
                fileSaveDir.mkdirs();
            this.threads = new DownloadThread[threadNum];

            try {
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept",
                        "image/gif, image/jpeg, image/pjpeg,image/pjpeg," +
                        " application/x-shockwave-flash, application/xaml+xml," +
                        " application/vnd.ms-xpsdocument, application/x-ms-xbap," +
                        " application/x-ms-application, application/vnd.ms-excel," +
                        " application/vnd.ms-powerpoint, application/msword newValue");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Referer", downloadUrl);
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0;" +
                                                        " .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30;" +
                                                        " .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.connect();
//                printResponseHeader(conn);

                if (conn.getResponseCode() == 200) {
                    this.fileSize = conn.getContentLength();//根据响应获取文件大小
                    if(this.fileSize <= 0)
                        throw new RuntimeException("Unknown file size");
                    String fileName = getFileName(conn);
                    this.saveFile = new File(fileSaveDir, fileName);
                    //获取下载记录
                    Map<Integer, Integer> logData = fileService.getData(downloadUrl);

                    if (logData.size() > 0) { //若存在下载记录
                        for (Map.Entry<Integer, Integer> entry : logData.entrySet())
                            data.put(entry.getKey(), entry.getValue()); //把各条线程已经下载的数据长度放入data中
                    }

                    if (data.size() == threads.length) { //计算所有线程已经下载的的文件长度
                        for (int i = 0; i < threads.length; i++) {
                            downloadSize += data.get(i+1);
                        }
//                        print("downloaded length" + downloadSize);
                    }
                    //计算每条线程下载的数据长度
                    this.block = (this.fileSize % this.threads.length) == 0?
                                  this.fileSize / this.threads.length : this.fileSize / this.threads.length + 1;
                } else {
                    throw new RuntimeException("server no response");
                }
            } catch (IOException e) {
//                print(e.toString());
                Toast.makeText(mContext, "图片链接错误", Toast.LENGTH_LONG).show();
                throw new RuntimeException("don't connection this url");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取文件名
     * @param conn
     * @return
     */
    private String getFileName(HttpURLConnection conn) {
        String fileName = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);
        if (fileName == null || "".equals(fileName.trim())) { //若获取不到文件名称
            for(int i = 0;;i++){
                String mine = conn.getHeaderField(i);
                if (mine == null)
                    break;
                if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())){
                    Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                    if(m.find()) return m.group(1);
                }
            }
            fileName = UUID.randomUUID() + ".tmp"; //默认取一个文件名
        }
        return fileName;
    }
    /**
     * 开始下载文件
     * @param listener 监听下载数量的变化，如果不需要了解实时下载的数量,可以设置为null
     * @return 已下载文件大小
     * @throws Exception
     */
    public int startDownload(DownloadProgressListener listener) throws Exception {
        try {
            RandomAccessFile randOut = new RandomAccessFile(saveFile, "rw");
            if (this.fileSize > 0)
                randOut.setLength(fileSize);
            randOut.close();
            URL url = new URL(downloadUrl);
            if (data.size() != threads.length) {
                data.clear();
                for (int i = 0;i < threads.length; i++) {
                    data.put(i+1, 0); //初始化每条线程已经下载的数据长度为0
                }
            }

            for (int i = 0;i < threads.length; i++) { //开启线程进行下载
                int downLength = data.get(i+1);
                if (downLength < block && downloadSize < fileSize) { //判断线程是否已经完成下载，否则继续下载
                    threads[i] = new DownloadThread(this, url, saveFile, block, data.get(i+1), i+1);
                    threads[i].setPriority(7);
                    threads[i].start();
                } else {
                    threads[i] = null;
                }
            }

            fileService.save(downloadUrl, data);
            boolean notFinish = true; //下载未完成
            while (notFinish) { //循环判断所有线程是否完成下载
                Thread.sleep(900);
                notFinish = false; //假设全部线程完成下载
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i] != null && !threads[i].isFinish()) { //若发现线程未完成下载
                        notFinish = true;
                        if (threads[i].getDownLength() == -1) { //如果下载失败,则重新下载
                            threads[i] = new DownloadThread(this, url, saveFile, block, data.get(i+1), i+1);
                            threads[i].setPriority(7);
                            threads[i].start();
                        }
                    }
                }
                if (listener != null)
                    listener.onDownloadSize(downloadSize); //通知目前已经下载完成的数据长度
            }
            fileService.delete(downloadUrl);
        } catch (Exception e) {
//            print(e.toString());
            throw new Exception("file download failed");
        }
        return downloadSize;
    }
    
    /**
     * 打印http头字段
     * @param http
     */
//    public static void printResponseHeader(HttpURLConnection http) {
//        Map<String, String> header = getHttpResponseHeader(http);
//        for(Map.Entry<String, String> entry : header.entrySet()) {
//            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
////            print(key + entry.getValue());
//        }
//    }
    /**
     * 获取http响应头字段
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(
            HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        for (int i = 0;;i++) {
            String mine = http.getHeaderField(i);
            if (mine == null)
                break;
            header.put(http.getHeaderFieldKey(i), mine);
        }
        return header;
    }
//    private static final String TAG = "FileDownloader";
//    private static void print(String string) {
//        Log.i(TAG, string);
//    }

}
