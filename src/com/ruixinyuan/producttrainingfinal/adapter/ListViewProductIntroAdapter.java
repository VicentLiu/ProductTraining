package com.ruixinyuan.producttrainingfinal.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixinyuan.producttrainingfinal.R;
import com.ruixinyuan.producttrainingfinal.bean.CommentBean;
import com.ruixinyuan.producttrainingfinal.bean.ProductInfoBean;
import com.ruixinyuan.producttrainingfinal.utils.BitmapUtils;
import com.ruixinyuan.producttrainingfinal.utils.EncryptionAndDecryption;
import com.ruixinyuan.producttrainingfinal.utils.net.JsonUtils;
import com.ruixinyuan.producttrainingfinal.utils.net.NetUtils;

/*
 *@user vicentliu
 *@time 2013-6-14下午12:34:18
 *@package com.ruixinyuan.producttrainingfinal.adapter
 */
public class ListViewProductIntroAdapter extends BaseAdapter {

    LayoutInflater mLayoutInflater;
    List<ProductInfoBean> mListProductionInfo;
    Context mContext;
    int listProductionCount = 0;

    public ListViewProductIntroAdapter(Context mContext , List<ProductInfoBean> mListProductionInfo) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mListProductionInfo = mListProductionInfo;
        if (mListProductionInfo != null)
            listProductionCount = this.mListProductionInfo.size();
    }

    @Override
    public int getCount() {
        return listProductionCount;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    List<Bitmap> listBitmap;
    GalleryImgAdapter galleryImgAdapter;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listview_productioninfo_item, null);
            viewHolder.mImgViewTitle = (ImageView)convertView.findViewById(R.id.item_imageview_title);
            viewHolder.mImgViewCommentIcon = (ImageView)convertView.findViewById(R.id.item_imgview_production_comment_icon);
            viewHolder.mTextViewProductionName = (TextView)convertView.findViewById(R.id.item_tv_production_name);
            viewHolder.mTextViewPublishDate = (TextView)convertView.findViewById(R.id.item_tv_production_publish_date);
            viewHolder.mTextViewProdutionIntro = (TextView)convertView.findViewById(R.id.item_tv_production_intro);
            viewHolder.mTextViewCommentCount = (TextView)convertView.findViewById(R.id.item_tv_production_comment_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (listProductionCount > 0 && position < listProductionCount) {
            if (mListProductionInfo.get(position).getmListProductionPicsLocalAddr().size() > 0) {
                String imageTitlePath = mListProductionInfo.get(position).getmListProductionPicsLocalAddr().get(0);
                if (!imageTitlePath.equals("")) {
                    int subStringStartIndex = imageTitlePath.lastIndexOf("/");
                    String fileName = imageTitlePath.substring(subStringStartIndex + 1);
                    viewHolder.mImgViewTitle
                    .setImageBitmap(BitmapUtils.getBitmapFromSDCard(fileName));
                }
            }
            String strUrl = mContext.getString(R.string.api_url)
                + "getComment"
                + "&type=" + mContext.getString(R.string.industry_type)
                + "&pid=" + mListProductionInfo.get(position).getProductID()
                + "&num=" + Integer.MAX_VALUE //评论总数
                + "&auth="
                + EncryptionAndDecryption
                  .MD5(mContext.getString(R.string.industry_type) + mContext.getString(R.string.app_key));
            String strResult = NetUtils.connServerForResult(strUrl);
            List<CommentBean> listCommentBean = new ArrayList<CommentBean>();
            listCommentBean = JsonUtils.parseCommentJsonList(mContext, strResult);
            int count = listCommentBean.size();
            viewHolder.mTextViewProductionName
              .setText(mListProductionInfo.get(position).getProductName());
            viewHolder.mTextViewProdutionIntro
              .setText((String)mListProductionInfo.get(position).getProductFunction());
            viewHolder.mTextViewPublishDate
              .setText(mListProductionInfo.get(position).getPublishTime());
            viewHolder.mTextViewCommentCount
              .setText(count + "");
        }
        return convertView;
    }

    final class ViewHolder {
        TextView mTextViewProductionName;
        TextView mTextViewProdutionIntro;
        TextView mTextViewPublishDate;
        TextView mTextViewCommentCount;
        ImageView mImgViewTitle;
        ImageView mImgViewCommentIcon;
    }
}
