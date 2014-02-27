package com.ruixinyuan.producttrainingfinal;

import java.util.ArrayList;
import java.util.List;

import com.ruixinyuan.producttrainingfinal.adapter.ListViewQuestionAdapter;
import com.ruixinyuan.producttrainingfinal.bean.QuestionBean;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/*
 *@user vicentliu
 *@time 2013-6-9下午4:00:49
 *@package com.ruixinyuan.producttrainingfinal
 */
public class TabActivityThree extends Activity {

    ListView mListViewQA;
    List<QuestionBean> listQuestionBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tabthree);
        mListViewQA = (ListView)findViewById(R.id.listViewQA);
        listQuestionBean = getData();
        ListViewQuestionAdapter adapter = new ListViewQuestionAdapter(TabActivityThree.this, listQuestionBean);
        mListViewQA.setAdapter(adapter);
        mListViewQA.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int curPos,
                    long arg3) {
                QuestionBean qb = listQuestionBean.get(curPos);
                new AlertDialog.Builder(TabActivityThree.this)
                               .setTitle(getString(R.string.answer_dialog_title))
                               .setMessage(qb.getQuestionAnswer())
                               .setPositiveButton(getString(R.string.answer_dialog_got_it), null)
                               .show();
            }
        });
    }

    private List<QuestionBean> getData() {
        List<QuestionBean> templistQuestionBean = new ArrayList<QuestionBean>();
        QuestionBean qb = new QuestionBean();
        qb.setQuestionTitle("海信");
        qb.setQuestionContent("海信是哪国品牌，产自哪里？");
        qb.setQuestionAnswer("中国山东，青岛");
        templistQuestionBean.add(qb);
        qb = new QuestionBean();
        qb.setQuestionTitle("长虹");
        qb.setQuestionContent("长虹是哪国品牌，产自哪里？");
        qb.setQuestionAnswer("中国四川，绵阳");
        templistQuestionBean.add(qb);
        return templistQuestionBean;
    }
}
