package com.lx.webservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;

import com.lx.webservice.bean.ListNoticeDetailBean;
import com.lx.webservice.bean.ListNoticeTitleBean;
import com.lx.webservice.bean.LoginFromMobileBean;

public class JWebServiceXmlParser {

    public static LoginFromMobileBean parseLoginFromMobile(Schema schema) {
        MessageElement[] nodes = schema.get_any();
        LoginFromMobileBean bean = new LoginFromMobileBean();
        for (MessageElement node : nodes) {
            String name = node.getName();
            String value = node.getValue();
            if (name.equalsIgnoreCase("AreaOID")) {
                bean.setAreaOID(value);
            } else if (name.equalsIgnoreCase("HumanName")) {
                bean.setHumanName(value);
            } else if (name.equalsIgnoreCase("CompanyOID")) {
                bean.setCompanyOID(value);
            } else if (name.equalsIgnoreCase("CompanyName")) {
                bean.setCompanyName(value);
            } else if (name.equalsIgnoreCase("DepartmentOID")) {
                bean.setDepartmentOID(value);
            } else if (name.equalsIgnoreCase("DepartmentName")) {
                bean.setDepartmentName(value);
            } else if (name.equalsIgnoreCase("UserOID")) {
                bean.setUserOID(value);
            } else if (name.equalsIgnoreCase("UserName")) {
                bean.setUserName(value);
            } else if (name.equalsIgnoreCase("Password")) {
                bean.setPassword(value);
            } else if (name.equalsIgnoreCase("DelFlag")) {
                bean.setDelFlag(value);
            } else if (name.equalsIgnoreCase("PublicMobile")) {
                bean.setPublicMobile(value);
            }
        }
        return bean;
    }

    public static List<ListNoticeTitleBean> parseListNoticeTitle(Schema schema) {
        MessageElement[] nodes = schema.get_any();
        List<ListNoticeTitleBean> beans = new ArrayList<>();
        for (MessageElement node : nodes) {
            Iterator<MessageElement> iterator = node.getChildElements();
            ListNoticeTitleBean bean = new ListNoticeTitleBean();
            while (iterator.hasNext()) {
                MessageElement element = (MessageElement) iterator.next();
                String name = element.getName();
                String value = element.getValue();
                if (name.equalsIgnoreCase("Notice_OID")) {
                    bean.setNotice_OID(value);
                } else if (name.equalsIgnoreCase("Category_OID")) {
                    bean.setCategory_OID(value);
                } else if (name.equalsIgnoreCase("title")) {
                    bean.setTitle(value);
                } else if (name.equalsIgnoreCase("Publisher")) {
                    bean.setPublisher(value);
                } else if (name.equalsIgnoreCase("PublisherOID")) {
                    bean.setPublisherOID(value);
                } else if (name.equalsIgnoreCase("ReleaseDate")) {
                    bean.setReleaseDate(value);
                } else if (name.equalsIgnoreCase("isTop")) {
                    bean.setIsTop(value);
                } else if (name.equalsIgnoreCase("DelFlag")) {
                    bean.setDelFlag(value);
                }
            }
            beans.add(bean);
        }
        return beans;
    }

    public static ListNoticeDetailBean parseListNoticeDetail(Schema schema) {
        MessageElement[] nodes = schema.get_any();
        ListNoticeDetailBean bean = new ListNoticeDetailBean();
        for (MessageElement node : nodes) {
            String name = node.getName();
            String value = node.getValue();
            if (name.equalsIgnoreCase("Notice_OID")) {
                bean.setNotice_OID(value);
            } else if (name.equalsIgnoreCase("Category_OID")) {
                bean.setCategory_OID(value);
            } else if (name.equalsIgnoreCase("NoticeContent")) {
                bean.setNoticeContent(value);
            } else if (name.equalsIgnoreCase("Publisher")) {
                bean.setPublisher(value);
            } else if (name.equalsIgnoreCase("PublisherOID")) {
                bean.setPublisherOID(value);
            } else if (name.equalsIgnoreCase("ReleaseDate")) {
                bean.setReleaseDate(value);
            } else if (name.equalsIgnoreCase("isTop")) {
                bean.setIsTop(value);
            } else if (name.equalsIgnoreCase("DelFlag")) {
                bean.setDelFlag(value);
            }
        }
        return bean;
    }

}
