package com.ruixinyuan.producttrainingfinal.bean;
/*
 *@user vicentliu
 *@time 2013-6-20下午3:35:23
 *@package com.ruixinyuan.producttrainingfinal.bean
 */
public class QuestionBean {

    private String questionTitle;
    private String questionContent;
    private String questionAnswer;
    public QuestionBean() {
        super();
    }
    public QuestionBean(String questionTitle, String questionContent,String questionAnswer) {
        super();
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.questionAnswer = questionAnswer;
    }
    public String getQuestionTitle() {
        return questionTitle;
    }
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }
    public String getQuestionContent() {
        return questionContent;
    }
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
    public String getQuestionAnswer() {
        return questionAnswer;
    }
    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
