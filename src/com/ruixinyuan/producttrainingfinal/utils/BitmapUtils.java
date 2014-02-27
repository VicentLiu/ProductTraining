package com.ruixinyuan.producttrainingfinal.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

/*
 *@user vicentliu
 *@time 2013-6-5下午5:04:56
 *@package com.ruixinyuan.producttraining.utils
 */
public class BitmapUtils {

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
           return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
           return null;
        }
    }

    public static byte[] Bitmap2Bytes(Bitmap bm){  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();  
       }  
    /**
     * 图像缩放
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    public static Bitmap getBitmapFromSDCard (String path) {
        Bitmap resultBitmap = null;
        path = Environment.getExternalStorageDirectory()
                + "/"
                + path;
        try {
            File file = new File(path);
            if (file.exists())
                resultBitmap = BitmapFactory.decodeFile(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultBitmap;
    }
}
