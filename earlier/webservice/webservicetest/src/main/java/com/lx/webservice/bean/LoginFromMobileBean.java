package com.lx.webservice.bean;

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
        return "LoginFromMobileBean [AreaOID=" + AreaOID + ", HumanName=" + HumanName + ", CompanyOID=" + CompanyOID
                + ", CompanyName=" + CompanyName + ", DepartmentOID=" + DepartmentOID + ", DepartmentName="
                + DepartmentName + ", UserOID=" + UserOID + ", UserName=" + UserName + ", Password=" + Password
                + ", DelFlag=" + DelFlag + ", PublicMobile=" + PublicMobile + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((AreaOID == null) ? 0 : AreaOID.hashCode());
        result = prime * result + ((CompanyName == null) ? 0 : CompanyName.hashCode());
        result = prime * result + ((CompanyOID == null) ? 0 : CompanyOID.hashCode());
        result = prime * result + ((DelFlag == null) ? 0 : DelFlag.hashCode());
        result = prime * result + ((DepartmentName == null) ? 0 : DepartmentName.hashCode());
        result = prime * result + ((DepartmentOID == null) ? 0 : DepartmentOID.hashCode());
        result = prime * result + ((HumanName == null) ? 0 : HumanName.hashCode());
        result = prime * result + ((Password == null) ? 0 : Password.hashCode());
        result = prime * result + ((PublicMobile == null) ? 0 : PublicMobile.hashCode());
        result = prime * result + ((UserName == null) ? 0 : UserName.hashCode());
        result = prime * result + ((UserOID == null) ? 0 : UserOID.hashCode());
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
        LoginFromMobileBean other = (LoginFromMobileBean) obj;
        if (AreaOID == null) {
            if (other.AreaOID != null)
                return false;
        } else if (!AreaOID.equals(other.AreaOID))
            return false;
        if (CompanyName == null) {
            if (other.CompanyName != null)
                return false;
        } else if (!CompanyName.equals(other.CompanyName))
            return false;
        if (CompanyOID == null) {
            if (other.CompanyOID != null)
                return false;
        } else if (!CompanyOID.equals(other.CompanyOID))
            return false;
        if (DelFlag == null) {
            if (other.DelFlag != null)
                return false;
        } else if (!DelFlag.equals(other.DelFlag))
            return false;
        if (DepartmentName == null) {
            if (other.DepartmentName != null)
                return false;
        } else if (!DepartmentName.equals(other.DepartmentName))
            return false;
        if (DepartmentOID == null) {
            if (other.DepartmentOID != null)
                return false;
        } else if (!DepartmentOID.equals(other.DepartmentOID))
            return false;
        if (HumanName == null) {
            if (other.HumanName != null)
                return false;
        } else if (!HumanName.equals(other.HumanName))
            return false;
        if (Password == null) {
            if (other.Password != null)
                return false;
        } else if (!Password.equals(other.Password))
            return false;
        if (PublicMobile == null) {
            if (other.PublicMobile != null)
                return false;
        } else if (!PublicMobile.equals(other.PublicMobile))
            return false;
        if (UserName == null) {
            if (other.UserName != null)
                return false;
        } else if (!UserName.equals(other.UserName))
            return false;
        if (UserOID == null) {
            if (other.UserOID != null)
                return false;
        } else if (!UserOID.equals(other.UserOID))
            return false;
        return true;
    }

}
