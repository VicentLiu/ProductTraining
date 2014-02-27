package com.ruixinyuan.producttrainingfinal.bean;
/*
 *@user vicentliu
 *@time 2013-6-25下午4:22:20
 *@package com.ruixinyuan.producttrainingfinal.bean
 */
public class CommentBean {
    int productId;
    String content;
    String phone;
    String time;
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
