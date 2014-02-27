package com.ruixinyuan.producttrainingfinal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruixinyuan.producttrainingfinal.adapter.GalleryImgAdapter;
import com.ruixinyuan.producttrainingfinal.adapter.ListViewCommentAdapter;
import com.ruixinyuan.producttrainingfinal.bean.CommentBean;
import com.ruixinyuan.producttrainingfinal.db.DBConstants;
import com.ruixinyuan.producttrainingfinal.utils.BitmapUtils;
import com.ruixinyuan.producttrainingfinal.utils.EncryptionAndDecryption;
import com.ruixinyuan.producttrainingfinal.utils.RConstrants;
import com.ruixinyuan.producttrainingfinal.utils.net.JsonUtils;
import com.ruixinyuan.producttrainingfinal.utils.net.NetUtils;

/*
 *@user vicentliu
 *@time 2013-6-17下午4:47:57
 *@package com.ruixinyuan.producttrainingfinal
 */
public class ProductionDetailActivity extends Activity {

    TextView mTvProductionName;
    TextView mTvProductionIntro;
    TextView mTvProductionIntroList;
    TextView mTvProductionPublishDate;
    Gallery mGalleryProductionPictures;
    TextView mTvCommentCount;
    ListView mListviewComment;
    EditText mEditTextComment;
    Button mButtonUploadComment;
    ScrollView mScrollViewProductionIntro;

    String nativePhoneNum = "";
    String providerName = "";
    ListViewCommentAdapter listviewCommentadapter;
    List<CommentBean> listCommentBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_detail);
        initData();
        init();
        //真机测试看可以成功不
//        SIMCardInfo simCardInfo = new SIMCardInfo(ProductionDetailActivity.this);
//        nativePhoneNum = simCardInfo.getNativePhoneNumber();
//        providerName = simCardInfo.getProviderName();
    }

    String productionName;
    String productionIntro;
    String productionShortDesc;
    String publishDate;
    int productionId;
    List<Bitmap> listBitmap;
    List<String> listBitmapAddr;
    private void initData() {
        productionId = getIntent().getIntExtra(DBConstants.PRODUCT_ID, 0);
        productionName = getIntent().getStringExtra(RConstrants.PRODUTION_NAME);
        productionIntro = getIntent().getStringExtra(RConstrants.PRODUCTION_INTRO);
        productionShortDesc = getIntent().getStringExtra(RConstrants.PRODUCTION_SHORT_DESC);
        publishDate = getIntent().getStringExtra(RConstrants.PUBLISH_DATE);
        int picCount = getIntent().getIntExtra(RConstrants.PRODUCTION_PICS_COUNT, 0);
        listBitmap = new ArrayList<Bitmap>();
        listBitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.comment_title_bg));
        listBitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
        listBitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon));
        listBitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.comment_title_bg));
        listBitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.comment_title_bg));
        listBitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.comment_title_bg));
        listBitmapAddr = new ArrayList<String>();
        String bitmapFileName;
        for (int i = 0;i < picCount;i++){
            bitmapFileName = getIntent().getStringExtra(RConstrants.PRODUCTION_PICS + i);
            listBitmapAddr.add(bitmapFileName);
            int subStringStartIndex = bitmapFileName.lastIndexOf("/");
            String fileName = bitmapFileName.substring(subStringStartIndex + 1);
            Bitmap tempBitmap = BitmapUtils.getBitmapFromSDCard(fileName);
            listBitmap.add(tempBitmap);
        }
    }
    String strDestUrl;
    String commentContent;
    GalleryImgAdapter adapter;
    CommentBean commentBean;
    private void init() {
        mTvProductionName = (TextView)findViewById(R.id.detail_item_tv_production_name);
        mTvProductionName.setText(productionName);
        mTvProductionPublishDate = (TextView)findViewById(R.id.detail_item_tv_production_publish_date);
        mTvProductionPublishDate.setText(publishDate);
        mScrollViewProductionIntro = (ScrollView)findViewById(R.id.scrollview_detial_item_tv_production_intro);
        mTvProductionIntro = (TextView)findViewById(R.id.detail_item_tv_production_intro);
        if (productionShortDesc != null) {
            mScrollViewProductionIntro.setVisibility(View.VISIBLE);
            mTvProductionIntro.setText(productionShortDesc);
        }
        mTvProductionIntroList = (TextView)findViewById(R.id.detail_item_tv_production_intro_list);
        mTvProductionIntroList.setText(productionIntro);
        mGalleryProductionPictures = (Gallery)findViewById(R.id.detail_item_gallery_prodution_pictures);
        mGalleryProductionPictures.setSelection(listBitmap.size() / 2);
        GalleryImgAdapter adapter = new GalleryImgAdapter(this, listBitmap);
        mGalleryProductionPictures.setAdapter(adapter);
        mGalleryProductionPictures.setSelection(2);
        mGalleryProductionPictures.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                    long arg3) {
                //TODO：打开大图
                int size = listBitmapAddr.size();
                if (size > 0) {
                    String str = listBitmapAddr.get(pos % size).toString();
                    int startIndex = str.lastIndexOf("/");
                    String fileName = str.substring(startIndex);
                    String path = Environment.getExternalStorageDirectory()
                            + "/"
                            + fileName;
                    File file = new File(path);
                    if (file != null && file.isFile()) {
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "image/*");
                        startActivity(intent);
                    } else { //todo:加入点击下载图片代码
                        Toast.makeText(ProductionDetailActivity.this, "没有图片啦~", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProductionDetailActivity.this, "没有图片啦~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mTvCommentCount = (TextView)findViewById(R.id.detail_comment_count);
        mEditTextComment = (EditText)findViewById(R.id.detail_edittext_comment);

        mListviewComment = (ListView)findViewById(R.id.detail_listview_comments_list);
        String strUrl =  getString(R.string.api_url)
                + "getComment"
                + "&type=" + getString(R.string.industry_type)
                + "&pid=" + productionId
                + "&num=" + 20 //评论总数
                + "&auth="
                + EncryptionAndDecryption
                  .MD5(getString(R.string.industry_type) +getString(R.string.app_key));
        String strResult = NetUtils.connServerForResult(strUrl);
        listCommentBean = new ArrayList<CommentBean>();
        listCommentBean = JsonUtils.parseCommentJsonList(ProductionDetailActivity.this, strResult);
        listviewCommentadapter = new ListViewCommentAdapter(ProductionDetailActivity.this, listCommentBean);
        mListviewComment.setAdapter(listviewCommentadapter);

        mButtonUploadComment = (Button)findViewById(R.id.buttonUploadComment);
        mButtonUploadComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                strDestUrl =  getString(R.string.api_url)
                        + "setComment"
                        + "&type=" + getString(R.string.industry_type)
                        + "&auth="
                        + EncryptionAndDecryption
                          .MD5(getString(R.string.industry_type) +getString(R.string.app_key));
                commentContent = mEditTextComment.getText().toString();
                if (commentContent.equals("")) {
                    Toast.makeText(ProductionDetailActivity.this, getString(R.string.none_comment_content), Toast.LENGTH_SHORT).show();
                } else {
                    if (postCommentData(strDestUrl)) {
                        String strUrl = ProductionDetailActivity.this.getString(R.string.api_url)
                                + "getComment"
                                + "&type=" + ProductionDetailActivity.this.getString(R.string.industry_type)
                                + "&pid=" + productionId
                                + "&num=" + Integer.MAX_VALUE //评论总数
                                + "&auth="
                                + EncryptionAndDecryption
                                  .MD5(ProductionDetailActivity.this.getString(R.string.industry_type)
                                       + ProductionDetailActivity.this.getString(R.string.app_key));
                        String strResult = NetUtils.connServerForResult(strUrl);
                        List<CommentBean> listCommentBean = new ArrayList<CommentBean>();
                        listCommentBean = JsonUtils.parseCommentJsonList(ProductionDetailActivity.this, strResult);
                        listviewCommentadapter = new ListViewCommentAdapter(ProductionDetailActivity.this
                                                                            , listCommentBean);
                        mListviewComment.setAdapter(listviewCommentadapter);
                        listviewCommentadapter.notifyDataSetChanged();
                    }
                    mEditTextComment.setText("");
                }
            }});
    }
    /**
     * 提交评论
     * @param strDestUrl
     * @return
     */
    private boolean postCommentData (String strDestUrl) {
        HttpPost httpPost
                  = new HttpPost(strDestUrl);
        List<NameValuePair> paras = new ArrayList<NameValuePair>();
        paras.add(new BasicNameValuePair("pid", productionId + ""));
        paras.add(new BasicNameValuePair("phone", providerName + nativePhoneNum));
        paras.add(new BasicNameValuePair("ctt", commentContent));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paras, HTTP.UTF_8));
          //execute the post and get the response from servers
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                httpResponse.getEntity();
                Toast.makeText(ProductionDetailActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(ProductionDetailActivity.this, "网络异常，请检测网络后提交评论", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    
}
