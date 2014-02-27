package com.ruixinyuan.producttrainingfinal.adapter;

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
import com.ruixinyuan.producttrainingfinal.bean.SaleSkillBean;

/*
 *@user vicentliu
 *@time 2013-6-20下午4:44:29
 *@package com.ruixinyuan.producttrainingfinal.adapter
 */
public class ListViewSaleSkillAdapter extends BaseAdapter {


    LayoutInflater mLayoutInflater;
    List<SaleSkillBean> mListSaleSkill;
    Context mContext;
    int listSaleSkillCount = 0;

    public ListViewSaleSkillAdapter(Context mContext , List<SaleSkillBean> mListSaleSkill) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mListSaleSkill = mListSaleSkill;
        if (mListSaleSkill != null)
            listSaleSkillCount = this.mListSaleSkill.size();
    }

    @Override
    public int getCount() {
        return listSaleSkillCount;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO
        return 0;
    }

    List<Bitmap> listBitmap;
    GalleryImgAdapter galleryImgAdapter;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listview_sale_skill_item, null);
            viewHolder.mImgViewTitleSaleSkill = (ImageView)convertView.findViewById(R.id.item_imageview_title_sale_skill);
            viewHolder.mTextViewProductionNameSaleSkill = (TextView)convertView.findViewById(R.id.item_tv_production_name_sale_skill);
            viewHolder.mTextViewProdutionIntroSaleSkill = (TextView)convertView.findViewById(R.id.item_tv_production_intro_sale_skill);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (listSaleSkillCount > 0 && position < listSaleSkillCount) {
            viewHolder.mTextViewProductionNameSaleSkill
              .setText(mListSaleSkill.get(position).getSaleSkillTitle());
            viewHolder.mTextViewProdutionIntroSaleSkill
              .setText(mListSaleSkill.get(position).getSaleSkillContent());
        }
        return convertView;
    }

    final class ViewHolder {
        TextView mTextViewProductionNameSaleSkill;
        TextView mTextViewProdutionIntroSaleSkill;
        ImageView mImgViewTitleSaleSkill;
    }
}
