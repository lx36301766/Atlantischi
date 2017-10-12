package com.lx.android.webservice.bean;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 13-2-5<br>
 * Time: 下午2:29<br>
 */

public class ListAttachBean {

    private String AttachmentOID;
    private String ModuleCode;
    private String AttachFile;
    private String AttachName;
    private String UserOID;
    private String AddTime;
    private String Size;

    public String getAttachmentOID() {
        return AttachmentOID;
    }

    public void setAttachmentOID(String attachmentOID) {
        AttachmentOID = attachmentOID;
    }

    public String getModuleCode() {
        return ModuleCode;
    }

    public void setModuleCode(String moduleCode) {
        ModuleCode = moduleCode;
    }

    public String getAttachFile() {
        return AttachFile;
    }

    public void setAttachFile(String attachFile) {
        AttachFile = attachFile;
    }

    public String getAttachName() {
        return AttachName;
    }

    public void setAttachName(String attachName) {
        AttachName = attachName;
    }

    public String getUserOID() {
        return UserOID;
    }

    public void setUserOID(String userOID) {
        UserOID = userOID;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    @Override
    public String toString() {
        return "ListAttachBean {" +
                "AttachmentOID='" + AttachmentOID + '\'' +
                ", ModuleCode='" + ModuleCode + '\'' +
                ", AttachFile='" + AttachFile + '\'' +
                ", AttachName='" + AttachName + '\'' +
                ", UserOID='" + UserOID + '\'' +
                ", AddTime='" + AddTime + '\'' +
                ", Size='" + Size + '\'' +
                '}';
    }
}
