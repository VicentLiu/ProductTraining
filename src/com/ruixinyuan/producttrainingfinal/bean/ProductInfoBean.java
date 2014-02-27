package com.ruixinyuan.producttrainingfinal.bean;

import java.util.List;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/*
 *@user vicentliu
 *@time 2013-6-5下午2:58:57
 *@package com.ruixinyuan.producttraining.bean
 */
public class ProductInfoBean implements Parcelable {

    int productID;
    String brand;
    String series;
    String productName;
    String productFunction;
    String productShortdest;

    String publishTime;
    List<String> mListProductionPicsLocalAddr;
    List<Bitmap> listProductionPics;

    public ProductInfoBean() {
        super();
    }

    public ProductInfoBean(String brand, String series, String productName,
            String productFunction) {
        super();
        this.brand = brand;
        this.series = series;
        this.productName = productName;
        this.productFunction = productFunction;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductFunction() {
        return productFunction;
    }

    public void setProductIntro(String productFunction) {
        this.productFunction = productFunction;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    
    public List<String> getmListProductionPicsLocalAddr() {
        return mListProductionPicsLocalAddr;
    }

    public void setmListProductionPicsLocalAddr(
            List<String> mListProductionPicsLocalAddr) {
        this.mListProductionPicsLocalAddr = mListProductionPicsLocalAddr;
    }

    
    public List<Bitmap> getListProductionPics() {
        return listProductionPics;
    }

    public void setListProductionPics(List<Bitmap> listProductionPics) {
        this.listProductionPics = listProductionPics;
    }

    public String getProductShortdest() {
        return productShortdest;
    }
    
    public void setProductShortdest(String productShortdest) {
        this.productShortdest = productShortdest;
    }

    public static final Parcelable.Creator<ProductInfoBean> CREATEOR = new Creator<ProductInfoBean>() {

        @SuppressWarnings("unchecked")
        @Override
        public ProductInfoBean createFromParcel(Parcel source) {
            ProductInfoBean pib = new ProductInfoBean();
            pib.brand = source.readString();
            pib.productName = source.readString();
            pib.productFunction = source.readString();
            pib.publishTime = source.readString();
            pib.productShortdest = source.readString();
            pib.mListProductionPicsLocalAddr = source.readArrayList(String.class.getClassLoader());
            pib.listProductionPics = source.readArrayList(Bitmap.class.getClassLoader());
            return pib;
        }

        @Override
        public ProductInfoBean[] newArray(int size) {
            return new ProductInfoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(series);
        dest.writeString(productName);
        dest.writeString(productFunction);
        dest.writeString(publishTime);
    }

}
