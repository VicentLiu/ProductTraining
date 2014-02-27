package com.ruixinyuan.producttrainingfinal.adapter;

import java.util.List;

import com.ruixinyuan.producttrainingfinal.R;
import com.ruixinyuan.producttrainingfinal.bean.QuestionBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 *@user vicentliu
 *@time 2013-6-20下午3:39:05
 *@package com.ruixinyuan.producttrainingfinal.adapter
 */
public class ListViewQuestionAdapter extends BaseAdapter {

    @SuppressWarnings("unused")
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private List<QuestionBean> listQuestionBean;
    int listQuestionBeanCount = 0;
    
    public ListViewQuestionAdapter(Context mContext,List<QuestionBean> listQuestionBean) {
        super();
        this.mContext = mContext;
        this.listQuestionBean = listQuestionBean;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        if (this.listQuestionBean != null)
            listQuestionBeanCount = this.listQuestionBean.size();
    }

    @Override
    public int getCount() {
        return listQuestionBeanCount;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO
        return 0;
    }

    @Override
    public View getView(int pos, View converView, ViewGroup arg2) {
        ViewHolder viewHolder = new ViewHolder();
        if (converView == null) {
            converView = mLayoutInflater.inflate(R.layout.listview_qa_item, null);
            viewHolder.mTextViewQuestionTitle =
                    (TextView)converView.findViewById(R.id.item_textview_question_title);
            viewHolder.mTextViewQuestionContent =
                    (TextView)converView.findViewById(R.id.item_textview_question_content);
            viewHolder.mTextViewQuestionAnswer =
                    (TextView)converView.findViewById(R.id.item_textView_question_answer);
            converView.setTag(viewHolder);
        } else {
            converView.getTag();
        }

        if (listQuestionBeanCount > 0 && pos < listQuestionBeanCount) {
            viewHolder.mTextViewQuestionTitle
            .setText(listQuestionBean.get(pos).getQuestionTitle());
            viewHolder.mTextViewQuestionContent
            .setText(listQuestionBean.get(pos).getQuestionContent());
            viewHolder.mTextViewQuestionAnswer
            .setText("点击查看答案");
        }
        return converView;
    }

    class ViewHolder {
        TextView mTextViewQuestionTitle;
        TextView mTextViewQuestionContent;
        TextView mTextViewQuestionAnswer;
    }
}
