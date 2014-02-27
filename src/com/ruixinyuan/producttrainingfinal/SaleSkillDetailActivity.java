package com.ruixinyuan.producttrainingfinal;

import com.ruixinyuan.producttrainingfinal.db.DBConstants;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/*
 *@user vicentliu
 *@time 2013-7-1下午1:54:02
 *@package com.ruixinyuan.producttrainingfinal
 */
public class SaleSkillDetailActivity extends Activity {

    TextView mTextViewDetailTitle;
    TextView mTextViewDetailContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_skill_details);
        initData();
        init();
    }

    int saleSkillId;
    String saleSkillTitle;
    String saleSkillContent;
    /**
     * 初始化数据
     */
    private void initData() {
        saleSkillId = getIntent().getIntExtra(DBConstants.SALE_SKILL_ID, 0);
        saleSkillTitle = getIntent().getStringExtra(DBConstants.SALE_SKILL_TITLE);
        saleSkillContent = getIntent().getStringExtra(DBConstants.SALE_SKILL_CONTENT);
    }

    private void init() {
        mTextViewDetailTitle = (TextView)findViewById(R.id.textViewSaleSkillDetailTitle);
        mTextViewDetailTitle.setText(saleSkillTitle);
        mTextViewDetailContent = (TextView)findViewById(R.id.textViewSaleSkillDetailContent);
        mTextViewDetailContent.setText(saleSkillContent);
    }
}
