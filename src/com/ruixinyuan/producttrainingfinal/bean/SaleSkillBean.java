package com.ruixinyuan.producttrainingfinal.bean;
/*
 *@user vicentliu
 *@time 2013-6-20下午4:45:25
 *@package com.ruixinyuan.producttrainingfinal.bean
 */
public class SaleSkillBean {
    private int saleSkillId;
    private String saleSkillTitle;
    private String saleSkillContent;

    public SaleSkillBean() {
        super();
    }

    public int getSaleSkillId() {
        return saleSkillId;
    }

    public void setSaleSkillId(int saleSkillId) {
        this.saleSkillId = saleSkillId;
    }

    public String getSaleSkillTitle() {
        return saleSkillTitle;
    }

    public void setSaleSkillTitle(String saleSkillTitle) {
        this.saleSkillTitle = saleSkillTitle;
    }

    public String getSaleSkillContent() {
        return saleSkillContent;
    }

    public void setSaleSkillContent(String saleSkillContent) {
        this.saleSkillContent = saleSkillContent;
    }

}
