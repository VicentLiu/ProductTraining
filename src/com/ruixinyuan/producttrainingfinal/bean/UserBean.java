package com.ruixinyuan.producttrainingfinal.bean;
/*
 *@user vicentliu
 *@time 2013-6-13下午3:29:29
 *@package com.ruixinyuan.producttrainingfinal.bean
 */
public class UserBean {

    String username;
    String password;
    
    public UserBean(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
