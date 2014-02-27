package com.ruixinyuan.producttrainingfinal.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixinyuan.producttrainingfinal.R;
import com.ruixinyuan.producttrainingfinal.bean.CommentBean;

/*
 *@user vicentliu
 *@time 2013-6-25下午4:32:05
 *@package com.ruixinyuan.producttrainingfinal.adapter
 */
public class ListViewCommentAdapter extends BaseAdapter {

    LayoutInflater mLayoutInflater;
    public List<CommentBean> listComments;
    Context mContext;
    int listCommentCount = 0;

    
    public ListViewCommentAdapter(Context mContext, List<CommentBean> listComments) {
        super();

        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(this.mContext);
        this.listComments = listComments;
        if (this.listComments != null)
            this.listCommentCount = listComments.size();
    }

    @Override
    public int getCount() {
        return listCommentCount;
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO
        return arg0;
    }

    @Override
    public View getView(int pos, View converView, ViewGroup arg2) {
        ViewHolder viewHolder = new ViewHolder();
        if (converView == null) {
            converView = mLayoutInflater.inflate(R.layout.listview_comment_item, null);
            viewHolder.mImageViewAvatar = (ImageView)converView.findViewById(R.id.item_comment_user_avatar);
            viewHolder.mTextViewUserName = (TextView)converView.findViewById(R.id.item_textview_user_name);
            viewHolder.mTextViewCommentTime = (TextView)converView.findViewById(R.id.item_textview_comment_time);
            viewHolder.mTextViewCommentContent = (TextView)converView.findViewById(R.id.item_textview_comment_content);
//            viewHolder.mTextViewUserName
//                        .setText(listComments.get(pos).getPhone().toString());  //0627 待真机加入测试
            viewHolder.mTextViewUserName.setText(mContext.getString(R.string.user_name));
            viewHolder.mTextViewCommentTime
                        .setText(listComments.get(pos).getTime().toString());
            viewHolder.mTextViewCommentContent
                        .setText(listComments.get(pos).getContent().toString());
            converView.setTag(viewHolder);
        } else {
            converView.getTag();
        }
        return converView;
    }

    final class ViewHolder {
        ImageView mImageViewAvatar;
        TextView mTextViewUserName;
        TextView mTextViewCommentTime;
        TextView mTextViewCommentContent;
    }
}
