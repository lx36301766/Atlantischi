package com.lx.android.webservice.ws;

import com.lx.android.webservice.bean.ListAttachBean;
import com.lx.android.webservice.bean.ListNoticeDetailBean;
import com.lx.android.webservice.bean.ListNoticeTitleBean;
import com.lx.android.webservice.bean.LoginFromMobileBean;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 14-1-23<br>
 * Time: 上午12:24<br>
 */

public class WSSoapParser {

    public static LoginFromMobileBean parseLoginFromMobile(SoapObject soapObject) {
        SoapObject so = (SoapObject) soapObject.getProperty("LoginFromMobileResult");
        LoginFromMobileBean bean = new LoginFromMobileBean();
        for (int i = 0; i < so.getPropertyCount(); i++) {
            PropertyInfo propertyInfo = new PropertyInfo();
            so.getPropertyInfo(i, propertyInfo);
            String name = propertyInfo.getName();
            SoapPrimitive sp = (SoapPrimitive) propertyInfo.getValue();
            String value = sp == null ? null : (String) sp.getValue();
//            Log.d("lx", String.format("name=%s, value=%s", name, value));
            if ("AreaOID".equalsIgnoreCase(name)) {
                bean.setAreaOID(value);
            } else if ("HumanName".equalsIgnoreCase(name)) {
                bean.setHumanName(value);
            } else if ("CompanyOID".equalsIgnoreCase(name)) {
                bean.setCompanyOID(value);
            } else if ("CompanyName".equalsIgnoreCase(name)) {
                bean.setCompanyName(value);
            } else if ("DepartmentOID".equalsIgnoreCase(name)) {
                bean.setDepartmentOID(value);
            } else if ("DepartmentName".equalsIgnoreCase(name)) {
                bean.setDepartmentName(value);
            } else if ("UserOID".equalsIgnoreCase(name)) {
                bean.setUserOID(value);
            } else if ("UserName".equalsIgnoreCase(name)) {
                bean.setUserName(value);
            } else if ("Password".equalsIgnoreCase(name)) {
                bean.setPassword(value);
            } else if ("DelFlag".equalsIgnoreCase(name)) {
                bean.setDelFlag(value);
            } else if ("PublicMobile".equalsIgnoreCase(name)) {
                bean.setPublicMobile(value);
            } else {
                throw new IllegalArgumentException("wrong property name : " + name);
            }
        }
        return bean;
    }

    public static List<ListNoticeTitleBean> parseListNoticeTitle(SoapObject soapObject) {
        List<ListNoticeTitleBean> list = new ArrayList<ListNoticeTitleBean>();
        SoapObject listNoticeTitle = (SoapObject) soapObject.getProperty("ListNoticeTitleResult");
        for (int i = 0; i < listNoticeTitle.getPropertyCount(); i++) {
            PropertyInfo propertyInfo = new PropertyInfo();
            listNoticeTitle.getPropertyInfo(i, propertyInfo);
            SoapObject so = (SoapObject) propertyInfo.getValue();
            ListNoticeTitleBean bean = parseListNoticeTitleBean(so);
            list.add(bean);
        }
        return list;
    }

    private static ListNoticeTitleBean parseListNoticeTitleBean(SoapObject so) {
        ListNoticeTitleBean bean = new ListNoticeTitleBean();
        for (int i = 0; i < so.getPropertyCount(); i++) {
            PropertyInfo propertyInfo = new PropertyInfo();
            so.getPropertyInfo(i, propertyInfo);
            String name = propertyInfo.getName();
            SoapPrimitive sp = (SoapPrimitive) propertyInfo.getValue();
            String value = sp == null ? null : (String) sp.getValue();
            if ("Notice_OID".equalsIgnoreCase(name)) {
                bean.setNotice_OID(value);
            } else if ("Category_OID".equalsIgnoreCase(name)) {
                bean.setCategory_OID(value);
            } else if ("title".equalsIgnoreCase(name)) {
                bean.setTitle(value);
            } else if ("Publisher".equalsIgnoreCase(name)) {
                bean.setPublisher(value);
            } else if ("PublisherOID".equalsIgnoreCase(name)) {
                bean.setPublisherOID(value);
            } else if ("ReleaseDate".equalsIgnoreCase(name)) {
                bean.setReleaseDate(value);
            } else if ("isTop".equalsIgnoreCase(name)) {
                bean.setIsTop(value);
            } else if ("DelFlag".equalsIgnoreCase(name)) {
                bean.setDelFlag(value);
            } else {
                throw new IllegalArgumentException("wrong property name : " + name);
            }
        }
        return bean;
    }

    public static ListNoticeDetailBean parseListNoticeDetail(SoapObject soapObject) {
        SoapObject so = (SoapObject) soapObject.getProperty("ListNoticeDetailResult");
        ListNoticeDetailBean bean = new ListNoticeDetailBean();
        for (int i = 0; i < so.getPropertyCount(); i++) {
            PropertyInfo propertyInfo = new PropertyInfo();
            so.getPropertyInfo(i, propertyInfo);
            String name = propertyInfo.getName();
            String value = null;
            Object object = propertyInfo.getValue();
            if (object instanceof SoapPrimitive) {
                value = ((SoapPrimitive) object).getValue().toString();
            }
            if ("Notice_OID".equalsIgnoreCase(name)) {
                bean.setNotice_OID(value);
            } else if ("Category_OID".equalsIgnoreCase(name)) {
                bean.setCategory_OID(value);
            } else if ("NoticeContent".equalsIgnoreCase(name)) {
                bean.setNoticeContent(value);
            } else if ("Publisher".equalsIgnoreCase(name)) {
                bean.setPublisher(value);
            } else if ("PublisherOID".equalsIgnoreCase(name)) {
                bean.setPublisherOID(value);
            } else if ("ReleaseDate".equalsIgnoreCase(name)) {
                bean.setReleaseDate(value);
            } else if ("isTop".equalsIgnoreCase(name)) {
                bean.setIsTop(value);
            } else if ("DelFlag".equalsIgnoreCase(name)) {
                bean.setDelFlag(value);
            } else if ("AttachmentsOID".equalsIgnoreCase(name)) {
                bean.setAttachmentsOID(value);
            } else if ("AttachmentsName".equalsIgnoreCase(name)) {
                bean.setAttachmentsName(value);
            } else if ("Attachments".equalsIgnoreCase(name)) {
                bean.setAttachments(value);
                if (object instanceof SoapObject) {
                    List<ListAttachBean> listAttachBeans = new ArrayList<ListAttachBean>();
                    int size = ((SoapObject) object).getPropertyCount();
                    for (int j = 0; j < size; j++) {
                        PropertyInfo propertyInfo2 = new PropertyInfo();
                        ((SoapObject) object).getPropertyInfo(j, propertyInfo2);
                        SoapObject i_so = (SoapObject) propertyInfo2.getValue();
                        listAttachBeans.add(parseListAttachBean(i_so));
                    }
                    bean.setListAttachBeans(listAttachBeans);
                }
            } else {
                throw new IllegalArgumentException("wrong property name : " + name);
            }
        }
        return bean;
    }

    public static List<ListAttachBean> parseListAttach(SoapObject soapObject) {
        List<ListAttachBean> beans = new ArrayList<ListAttachBean>();
        SoapObject listAttach = (SoapObject) soapObject.getProperty("ListAttachResult");
        for (int i = 0; i < listAttach.getPropertyCount(); i++) {
            PropertyInfo propertyInfo = new PropertyInfo();
            listAttach.getPropertyInfo(i, propertyInfo);
            SoapObject so = (SoapObject) propertyInfo.getValue();
            ListAttachBean bean = parseListAttachBean(so);
            beans.add(bean);
        }
        return beans;
    }

    private static ListAttachBean parseListAttachBean(SoapObject so) {
        ListAttachBean bean = new ListAttachBean();
        for (int i = 0; i < so.getPropertyCount(); i++) {
            PropertyInfo propertyInfo = new PropertyInfo();
            so.getPropertyInfo(i, propertyInfo);
            String name = propertyInfo.getName();
            SoapPrimitive sp = (SoapPrimitive) propertyInfo.getValue();
            String value = sp == null ? null : (String) sp.getValue();
            if ("AttachmentOID".equalsIgnoreCase(name)) {
                bean.setAttachmentOID(value);
            } else if ("ModuleCode".equalsIgnoreCase(name)) {
                bean.setModuleCode(value);
            } else if ("AttachFile".equalsIgnoreCase(name)) {
                bean.setAttachFile(value);
            } else if ("AttachName".equalsIgnoreCase(name)) {
                bean.setAttachName(value);
            } else if ("UserOID".equalsIgnoreCase(name)) {
                bean.setUserOID(value);
            } else if ("AddTime".equalsIgnoreCase(name)) {
                bean.setAddTime(value);
            } else if ("Size".equalsIgnoreCase(name)) {
                bean.setSize(value);
            } else {
                throw new IllegalArgumentException("wrong property name : " + name);
            }
        }
        return bean;
    }


}
