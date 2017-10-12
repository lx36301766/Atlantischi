package com.lx.webservice.bean;

public class ListNoticeDetailBean {

    private String Notice_OID;
    private String Category_OID;
    private String NoticeContent;
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

    @Override
    public String toString() {
        return "ListNoticeDetailBean [Notice_OID=" + Notice_OID + ", Category_OID=" + Category_OID + ", NoticeContent="
                + NoticeContent + ", Publisher=" + Publisher + ", PublisherOID=" + PublisherOID + ", ReleaseDate="
                + ReleaseDate + ", isTop=" + isTop + ", DelFlag=" + DelFlag + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Category_OID == null) ? 0 : Category_OID.hashCode());
        result = prime * result + ((DelFlag == null) ? 0 : DelFlag.hashCode());
        result = prime * result + ((NoticeContent == null) ? 0 : NoticeContent.hashCode());
        result = prime * result + ((Notice_OID == null) ? 0 : Notice_OID.hashCode());
        result = prime * result + ((Publisher == null) ? 0 : Publisher.hashCode());
        result = prime * result + ((PublisherOID == null) ? 0 : PublisherOID.hashCode());
        result = prime * result + ((ReleaseDate == null) ? 0 : ReleaseDate.hashCode());
        result = prime * result + ((isTop == null) ? 0 : isTop.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListNoticeDetailBean other = (ListNoticeDetailBean) obj;
        if (Category_OID == null) {
            if (other.Category_OID != null)
                return false;
        } else if (!Category_OID.equals(other.Category_OID))
            return false;
        if (DelFlag == null) {
            if (other.DelFlag != null)
                return false;
        } else if (!DelFlag.equals(other.DelFlag))
            return false;
        if (NoticeContent == null) {
            if (other.NoticeContent != null)
                return false;
        } else if (!NoticeContent.equals(other.NoticeContent))
            return false;
        if (Notice_OID == null) {
            if (other.Notice_OID != null)
                return false;
        } else if (!Notice_OID.equals(other.Notice_OID))
            return false;
        if (Publisher == null) {
            if (other.Publisher != null)
                return false;
        } else if (!Publisher.equals(other.Publisher))
            return false;
        if (PublisherOID == null) {
            if (other.PublisherOID != null)
                return false;
        } else if (!PublisherOID.equals(other.PublisherOID))
            return false;
        if (ReleaseDate == null) {
            if (other.ReleaseDate != null)
                return false;
        } else if (!ReleaseDate.equals(other.ReleaseDate))
            return false;
        if (isTop == null) {
            if (other.isTop != null)
                return false;
        } else if (!isTop.equals(other.isTop))
            return false;
        return true;
    }

}
