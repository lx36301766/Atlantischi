package com.lx.android.webservice.bean;

public class LoginFromMobileBean {

    private String AreaOID;
    private String HumanName;
    private String CompanyOID;
    private String CompanyName;
    private String DepartmentOID;
    private String DepartmentName;
    private String UserOID;
    private String UserName;
    private String Password;
    private String DelFlag;
    private String PublicMobile;

    public String getAreaOID() {
        return AreaOID;
    }

    public void setAreaOID(String areaOID) {
        AreaOID = areaOID;
    }

    public String getHumanName() {
        return HumanName;
    }

    public void setHumanName(String humanName) {
        HumanName = humanName;
    }

    public String getCompanyOID() {
        return CompanyOID;
    }

    public void setCompanyOID(String companyOID) {
        CompanyOID = companyOID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDepartmentOID() {
        return DepartmentOID;
    }

    public void setDepartmentOID(String departmentOID) {
        DepartmentOID = departmentOID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getUserOID() {
        return UserOID;
    }

    public void setUserOID(String userOID) {
        UserOID = userOID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDelFlag() {
        return DelFlag;
    }

    public void setDelFlag(String delFlag) {
        DelFlag = delFlag;
    }

    public String getPublicMobile() {
        return PublicMobile;
    }

    public void setPublicMobile(String publicMobile) {
        PublicMobile = publicMobile;
    }

    @Override
    public String toString() {
        return "LoginFromMobileBean {" +
                "AreaOID='" + AreaOID + '\'' +
                ", HumanName='" + HumanName + '\'' +
                ", CompanyOID='" + CompanyOID + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", DepartmentOID='" + DepartmentOID + '\'' +
                ", DepartmentName='" + DepartmentName + '\'' +
                ", UserOID='" + UserOID + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", DelFlag='" + DelFlag + '\'' +
                ", PublicMobile='" + PublicMobile + '\'' +
                '}';
    }

}
