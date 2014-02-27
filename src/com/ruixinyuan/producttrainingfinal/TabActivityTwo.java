package com.ruixinyuan.producttrainingfinal;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ruixinyuan.producttrainingfinal.adapter.ListViewSaleSkillAdapter;
import com.ruixinyuan.producttrainingfinal.bean.SaleSkillBean;
import com.ruixinyuan.producttrainingfinal.db.DBConstants;
import com.ruixinyuan.producttrainingfinal.db.SQLiteSalesSkillOpenHelper;

/*
 *@user vicentliu
 *@time 2013-6-8下午3:11:21
 *@package com.ruixinyuan.producttrainingfinal
 */
public class TabActivityTwo extends Activity {

    List<SaleSkillBean> listSkillBean;
    ListView mListViewSkill;
    AutoCompleteTextView mAutoCompleteTextView;
    Button mButtonSearch;
//    private static final String[] COUNTRIES = new String[] {
//        "Belgium", "France", "Italy", "Germany", "Spain"
//    }; //可以从数据库中获取
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tabtwo);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
//                android.R.layout.simple_dropdown_item_1line,
//                COUNTRIES);
        mAutoCompleteTextView =
                (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewTabSaleSkillSearch);
//        mAutoCompleteTextView.setAdapter(arrayAdapter);
        mButtonSearch = (Button)findViewById(R.id.buttonSearchTabTwo);
        mButtonSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryStr = mAutoCompleteTextView.getText().toString().trim();
                SQLiteSalesSkillOpenHelper dbHelper = SQLiteSalesSkillOpenHelper.getInstance(TabActivityTwo.this);
                SaleSkillBean ssb = dbHelper.querySaleSkillInfo(queryStr);
                if (ssb.getSaleSkillId() != 0) {
                    Intent intent = new Intent(TabActivityTwo.this, SaleSkillDetailActivity.class);
                    intent.putExtra(DBConstants.SALE_SKILL_TITLE, ssb.getSaleSkillTitle());
                    intent.putExtra(DBConstants.SALE_SKILL_CONTENT, ssb.getSaleSkillContent());
                    startActivity(intent);
                } else {
                    Toast.makeText(TabActivityTwo.this, "无此销售技巧，请重新输入", Toast.LENGTH_LONG).show();
                }
                mAutoCompleteTextView.setText("");
            }
        });
        mListViewSkill = (ListView)findViewById(R.id.listViewSaleSkill);
        SQLiteSalesSkillOpenHelper mdbHelper = SQLiteSalesSkillOpenHelper.getInstance(TabActivityTwo.this);
        listSkillBean = mdbHelper.getAllSaleSkill();
        ListViewSaleSkillAdapter adapter = 
                new ListViewSaleSkillAdapter(TabActivityTwo.this, listSkillBean);
        mListViewSkill.setAdapter(adapter);
        mListViewSkill.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int curIndex,
                    long arg3) {
                Intent intent = new Intent(TabActivityTwo.this, SaleSkillDetailActivity.class);
                SaleSkillBean curSaleSkillBean = listSkillBean.get(curIndex);
                intent.putExtra(DBConstants.SALE_SKILL_ID, curSaleSkillBean.getSaleSkillId());
                intent.putExtra(DBConstants.SALE_SKILL_TITLE, curSaleSkillBean.getSaleSkillTitle());
                intent.putExtra(DBConstants.SALE_SKILL_CONTENT, curSaleSkillBean.getSaleSkillContent());
                startActivity(intent);
            }
        });
    }
}
