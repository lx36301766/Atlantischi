package com.lx.webservice.bean;

public class ListNoticeTitleBean {

    private String Notice_OID;
    private String Category_OID;
    private String title;
    private String Publisher;
    private String PublisherOID;
    private String ReleaseDate;
    private String isTop;
    private String DelFlag;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "ListNoticeTitleBean {" +
                "Notice_OID='" + Notice_OID + '\'' +
                ", Category_OID='" + Category_OID + '\'' +
                ", title='" + title + '\'' +
                ", Publisher='" + Publisher + '\'' +
                ", PublisherOID='" + PublisherOID + '\'' +
                ", ReleaseDate='" + ReleaseDate + '\'' +
                ", isTop='" + isTop + '\'' +
                ", DelFlag='" + DelFlag + '\'' +
                '}';
    }
}
