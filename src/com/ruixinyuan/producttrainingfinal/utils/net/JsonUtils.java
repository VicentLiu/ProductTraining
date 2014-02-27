package com.ruixinyuan.producttrainingfinal.utils.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.widget.Toast;

import com.ruixinyuan.producttrainingfinal.bean.CommentBean;
import com.ruixinyuan.producttrainingfinal.bean.ProductInfoBean;

/*
 *@user vicentliu
 *@time 2013-6-21上午9:55:58
 *@package com.ruixinyuan.producttrainingfinal.utils.net
 */
public class JsonUtils {

    public static List<ProductInfoBean> parseProductionJsonMulti (final Context mContext,String strResult) {
        List<ProductInfoBean> listProductionInfoBean = new ArrayList<ProductInfoBean>();
        ProductInfoBean pib = null;
        try {
            JSONTokener jsonTokener = new JSONTokener(strResult);
            JSONObject obj = (JSONObject) jsonTokener.nextValue();
            String str = obj.getString("error");
            if (str.equals("")) {  //无异常
                JSONArray jsonProductionList = 
                        new JSONObject(strResult).getJSONArray("msg");
              int listLength = jsonProductionList.length();
              for (int i = 0; i < listLength; i++) {
                  JSONObject jsonProduction = (JSONObject) jsonProductionList.get(i);
                  int id = jsonProduction.getInt("productId");
                  String productBrand = jsonProduction.getString("brand");
                  String productSeries = jsonProduction.getString("series");
                  String productName = jsonProduction.getString("name");
                  String shortDescription = jsonProduction.getString("short_desc");
                  String description = jsonProduction.getString("description");
                  String publishTime = jsonProduction.getString("time");
                  JSONObject jsonPicAddrs = jsonProduction.getJSONObject("pictures");
                  int leght = jsonPicAddrs.length();
                  int num = leght;
                  List<String> listPicsLocalAddr = new ArrayList<String>();
                  for (int j = 1; j <= num;j++) {
                      final String fileDownloadUrl = jsonPicAddrs.getString("pic" + j);
                      listPicsLocalAddr.add(fileDownloadUrl);
                  }
                  pib = new ProductInfoBean();
                  pib.setProductID(id);
                  pib.setBrand(productBrand);
                  pib.setSeries(productSeries);
                  pib.setProductName(productName);
                  pib.setProductIntro(description);
                  pib.setProductShortdest(shortDescription);
                  int listPicLocalAddrCount = listPicsLocalAddr.size();
                  if (listPicLocalAddrCount > 0)
                      pib.setmListProductionPicsLocalAddr(listPicsLocalAddr);
                  pib.setPublishTime(publishTime);
                  listProductionInfoBean.add(pib);
              }
            } else if (str.equals("10001")) {
                Toast.makeText(mContext, "type返回错误，请联系软件开发商", Toast.LENGTH_LONG).show();
            } else if (str.equals("10002")) {
                Toast.makeText(mContext, "校验错误，请联系软件开发商", Toast.LENGTH_LONG).show();
            } else if (str.equals("10003")) {
//                Toast.makeText(mContext, "暂无新产品", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException ex){
            Toast.makeText(mContext, "网络数据包异常", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        return listProductionInfoBean;
    }

    public static List<CommentBean> parseCommentJsonList (final Context mContext,String strResult) {
        List<CommentBean> listCommentBean = new ArrayList<CommentBean>();
        CommentBean commentBean = null;
        try {
            JSONArray jsonArray = 
                    new JSONObject(strResult).getJSONArray("msg");
            int jsonArrayLength = jsonArray.length();
            for (int i = 0; i < jsonArrayLength; i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                int productId = jsonObj.getInt("productId");
                String content = jsonObj.getString("content");
                String telNum = jsonObj.getString("phone");
                String time = jsonObj.getString("time");
                commentBean = new CommentBean();
                commentBean.setProductId(productId);
                commentBean.setContent(content);
                commentBean.setPhone(telNum);
                commentBean.setTime(time);
                listCommentBean.add(commentBean);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return listCommentBean;
    }
}
