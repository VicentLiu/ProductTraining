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

import com.ruixinyuan.producttrainingfinal.adapter.ListViewProductIntroAdapter;
import com.ruixinyuan.producttrainingfinal.bean.ProductInfoBean;
import com.ruixinyuan.producttrainingfinal.db.DBConstants;
import com.ruixinyuan.producttrainingfinal.db.SQLiteProductHelper;
import com.ruixinyuan.producttrainingfinal.utils.RConstrants;

/*
 *@user vicentliu
 *@time 2013-6-8下午2:51:26
 *@package com.ruixinyuan.producttrainingfinal
 */
public class TabActivityOne extends Activity {

    List<ProductInfoBean> mListProductionInfo = null;
    ListView mListViewProductItems;
    AutoCompleteTextView mAutoCompleteTextView;
    Button mButtonSearch;
    SQLiteProductHelper mProductHelper;
    ListViewProductIntroAdapter mListViewAdapter;
//    private static final String[] COUNTRIES = new String[] {
//        "Belgium", "France", "Italy", "Germany", "Spain"
//    }; //可以从数据库中获取
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tabone);
        mAutoCompleteTextView =
                (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewTabProductionSearch);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
//                                                                android.R.layout.simple_dropdown_item_1line,
//                                                                COUNTRIES);
//        mAutoCompleteTextView.setAdapter(arrayAdapter);
        mListViewProductItems = (ListView)findViewById(R.id.listViewProductItems);
        try {
            mProductHelper = SQLiteProductHelper.getInstance(TabActivityOne.this);
            mListProductionInfo = mProductHelper.getAllProductionInfo();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            mProductHelper.close();
        }
        mButtonSearch =(Button)findViewById(R.id.buttonSearchTabOne);
        mButtonSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String strQuery = mAutoCompleteTextView.getText().toString().trim();
                SQLiteProductHelper mProductHelper = SQLiteProductHelper.getInstance(TabActivityOne.this);
                ProductInfoBean pib = new ProductInfoBean();
                pib = mProductHelper.queryProductInfo(strQuery);
                if (pib.getProductID() != 0) {
                    Intent intent = new Intent(TabActivityOne.this, ProductionDetailActivity.class);
                    intent.putExtra(DBConstants.PRODUCT_ID, pib.getProductID());
                    intent.putExtra(RConstrants.PRODUTION_NAME, pib.getProductName());
                    intent.putExtra(RConstrants.PUBLISH_DATE, pib.getPublishTime());
                    intent.putExtra(RConstrants.PRODUCTION_SHORT_DESC, pib.getProductShortdest());
                    intent.putExtra(RConstrants.PRODUCTION_INTRO, pib.getProductFunction());
                    List<String> tempListBitmapAddr = pib.getmListProductionPicsLocalAddr();
                    int picCount = tempListBitmapAddr.size();
                    for (int i = 0; i < picCount; i++) {
                        intent.putExtra(RConstrants.PRODUCTION_PICS + i, tempListBitmapAddr.get(i));
                    }
                    intent.putExtra(RConstrants.PRODUCTION_PICS_COUNT, picCount);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(TabActivityOne.this, "无此产品，请重新输入", Toast.LENGTH_LONG).show();
                }
                mAutoCompleteTextView.setText("");
            }
        });

        mListViewAdapter = new ListViewProductIntroAdapter(TabActivityOne.this, mListProductionInfo);
        mListViewProductItems.setAdapter(mListViewAdapter);
        mListViewProductItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int currentIndex,
                    long arg3) {
                Intent intent = new Intent(TabActivityOne.this, ProductionDetailActivity.class);
                ProductInfoBean curProductionInfoBean = mListProductionInfo.get(currentIndex);
                intent.putExtra(DBConstants.PRODUCT_ID, curProductionInfoBean.getProductID());
                intent.putExtra(RConstrants.PRODUTION_NAME, curProductionInfoBean.getProductName());
                intent.putExtra(RConstrants.PUBLISH_DATE, curProductionInfoBean.getPublishTime());
                intent.putExtra(RConstrants.PRODUCTION_SHORT_DESC, curProductionInfoBean.getProductShortdest());
                intent.putExtra(RConstrants.PRODUCTION_INTRO, curProductionInfoBean.getProductFunction());
                List<String> tempListBitmapAddr = curProductionInfoBean.getmListProductionPicsLocalAddr();
                int picCount = tempListBitmapAddr.size();
                for (int i = 0; i < picCount; i++) {
                    intent.putExtra(RConstrants.PRODUCTION_PICS + i, tempListBitmapAddr.get(i));
                }
                intent.putExtra(RConstrants.PRODUCTION_PICS_COUNT, picCount);
//                startActivity(intent);
                startActivityForResult(intent, RConstrants.PRODUCTION_DETAIL_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RConstrants.PRODUCTION_DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            //TODO:获取评论数目
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    
}
