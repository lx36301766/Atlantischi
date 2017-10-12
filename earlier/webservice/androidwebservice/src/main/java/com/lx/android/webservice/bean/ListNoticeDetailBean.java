package com.lx.android.webservice.bean;

import java.util.ArrayList;
import java.util.List;

public class ListNoticeDetailBean {

    private String Notice_OID;
    private String Category_OID;
    private String NoticeContent;
    private String Publisher;
    private String PublisherOID;
    private String ReleaseDate;
    private String isTop;
    private String DelFlag;
    private String AttachmentsOID;
    private String AttachmentsName;
    private String Attachments;

    private List<ListAttachBean> listAttachBeans = new ArrayList<ListAttachBean>();

    public List<ListAttachBean> getListAttachBeans() {
        return listAttachBeans;
    }

    public void setListAttachBeans(List<ListAttachBean> listAttachBeans) {
        this.listAttachBeans.clear();
        this.listAttachBeans.addAll(listAttachBeans);
    }

    public String getNotice_OID() {
        return Notice_OID;
    }

    public void setNotice_OID(String notice_OID) {
        Notice_OID = notice_OID;
    }

    public String getCategory_OID() {
        return Category_OID;
    }

    public void setCategory_OID(String category_OID) {
        Category_OID = category_OID;
    }

    public String getNoticeContent() {
        return NoticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        NoticeContent = noticeContent;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getPublisherOID() {
        return PublisherOID;
    }

    public void setPublisherOID(String publisherOID) {
        PublisherOID = publisherOID;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getDelFlag() {
        return DelFlag;
    }

    public void setDelFlag(String delFlag) {
        DelFlag = delFlag;
    }

    public String getAttachmentsOID() {
        return AttachmentsOID;
    }

    public void setAttachmentsOID(String attachmentsOID) {
        AttachmentsOID = attachmentsOID;
    }

    public String getAttachmentsName() {
        return AttachmentsName;
    }

    public void setAttachmentsName(String attachmentsName) {
        AttachmentsName = attachmentsName;
    }

    public String getAttachments() {
        return Attachments;
    }

    public void setAttachments(String attachments) {
        Attachments = attachments;
    }

    @Override
    public String toString() {
        return "ListNoticeDetailBean {" +
                "Notice_OID='" + Notice_OID + '\'' +
                ", Category_OID='" + Category_OID + '\'' +
                ", NoticeContent='" + NoticeContent + '\'' +
                ", Publisher='" + Publisher + '\'' +
                ", PublisherOID='" + PublisherOID + '\'' +
                ", ReleaseDate='" + ReleaseDate + '\'' +
                ", isTop='" + isTop + '\'' +
                ", DelFlag='" + DelFlag + '\'' +
                ", AttachmentsOID='" + AttachmentsOID + '\'' +
                ", AttachmentsName='" + AttachmentsName + '\'' +
                ", Attachments='" + Attachments + '\'' +
                ", \nlistAttachBeans =" + buildListString(listAttachBeans) +
                '}';
    }

    private String buildListString(List<?> list) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n[");
        for (Object bean : list) {
            builder.append(bean);
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }

}
