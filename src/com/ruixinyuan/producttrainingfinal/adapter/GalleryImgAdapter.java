package com.ruixinyuan.producttrainingfinal.adapter;

import java.util.List;

import com.ruixinyuan.producttrainingfinal.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/*
 *@user vicentliu
 *@time 2013-6-5下午5:14:39
 *@package com.ruixinyuan.producttraining.adapter
 */
public class GalleryImgAdapter extends BaseAdapter {

    private Context mContext;
//    private Bitmap[] bitmaps;
    private List<Bitmap> listBitmap;
    private int listBitmapSize;
    private int selectedItemPosition;
    public GalleryImgAdapter(Context mContext,List<Bitmap> listBitmap) {
        super();
        this.mContext = mContext;
        this.listBitmap = listBitmap;
        if (this.listBitmap != null)
            listBitmapSize = this.listBitmap.size();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void setSelectedItem(int selectedItemPosition) {
        if (this.selectedItemPosition != selectedItemPosition) {
            this.selectedItemPosition = selectedItemPosition;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View converView, ViewGroup arg2) {
        ImageView imgView = new ImageView(mContext);
        imgView.setBackgroundResource(R.drawable.product_default);
        if (listBitmapSize != 0) {
            imgView.setImageBitmap(listBitmap.get(position % listBitmapSize));
        }
        imgView.setAdjustViewBounds(true);
        if (selectedItemPosition == position) { //选中item
            //可以添加动画效果
//            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.gallery_product_pictures_show);
            imgView.setLayoutParams(new Gallery.LayoutParams(300,300));
//            imgView.startAnimation(animation);
        } else {
            imgView.setLayoutParams(new Gallery.LayoutParams(300,300));
        }
        return imgView;
    }
}
