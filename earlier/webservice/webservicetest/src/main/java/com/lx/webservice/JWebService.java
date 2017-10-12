package com.lx.webservice;

import java.util.List;

import org.apache.axis.types.Schema;

import com.lx.webservice.bean.ListNoticeDetailBean;
import com.lx.webservice.bean.ListNoticeTitleBean;
import com.lx.webservice.bean.LoginFromMobileBean;

public class JWebService {

    public static void main(String[] args) {
        try {
            LoginFromMobileBean loginFromMobileBean = callLoginFromMobile();
            System.out.println(loginFromMobileBean);
            String UserOID = loginFromMobileBean.getUserOID();
            List<ListNoticeTitleBean> listNoticeTitleBeans = callListNoticeTitle(UserOID);
            for (ListNoticeTitleBean listNoticeTitleBean : listNoticeTitleBeans) {
                System.out.println(listNoticeTitleBean);
            }
            for (ListNoticeTitleBean listNoticeTitleBean : listNoticeTitleBeans) {
                String Notice_OID = listNoticeTitleBean.getNotice_OID();
                ListNoticeDetailBean listNoticeDetailBean = callListNoticeDetail(Notice_OID);
                System.out.println(listNoticeDetailBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LoginFromMobileBean callLoginFromMobile() throws Exception {
        JWebServiceInfo info = JWebServiceInfoBuilder.buildLoginFromMobile();
        Schema returnValue = (Schema) JWebServiceUtils.callWebService(info);
        return JWebServiceXmlParser.parseLoginFromMobile(returnValue);
    }

    private static List<ListNoticeTitleBean> callListNoticeTitle(String UserOID) throws Exception {
        JWebServiceInfo info = JWebServiceInfoBuilder.buildListNoticeTitle(UserOID);
        Schema returnValue = (Schema) JWebServiceUtils.callWebService(info);
        return JWebServiceXmlParser.parseListNoticeTitle(returnValue);
    }

    private static ListNoticeDetailBean callListNoticeDetail(String Notice_OID) throws Exception {
        JWebServiceInfo info = JWebServiceInfoBuilder.buildListNoticeDetail(Notice_OID);
        Schema returnValue = (Schema) JWebServiceUtils.callWebService(info);
        return JWebServiceXmlParser.parseListNoticeDetail(returnValue);
    }

}
