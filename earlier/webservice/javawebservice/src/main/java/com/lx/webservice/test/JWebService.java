package com.lx.webservice.test;

import java.rmi.RemoteException;
import java.util.List;

import com.linkage.lcsmp.interfaces.soap.epg.epgauth.server.ui.EpgAuthInterface;
import com.linkage.lcsmp.interfaces.soap.epg.epgauth.server.ui.EpgAuthInterfaceProxy;
import com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.req.EpgAuthReq;
import com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.rsp.EpgAuthRsp;
import com.lx.webservice.bean.ListAttachBean;
import com.lx.webservice.data.*;
import javafx.util.Pair;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.types.Schema;

import com.lx.webservice.bean.ListNoticeDetailBean;
import com.lx.webservice.bean.ListNoticeTitleBean;
import com.lx.webservice.bean.LoginFromMobileBean;

public class JWebService {

    public static void main(String[] args) {
        String transactionID = "90000003" + "20150413152930" + "351231285538348334";
        String userToken = AuthHelper.getAuthToken();
        String epgURL = "www.baidu.com";
        EpgAuthReq epgAuthReq = new EpgAuthReq(transactionID, epgURL, userToken);

        try {
//            mainTest();
//            System.out.println(buildListString(callListAttach()));

            JWSConfig webServiceInfo = new JWSConfig();
            webServiceInfo.setEndpoint("http://124.232.135.227:8298/services/EpgAuthInterface");
            webServiceInfo.setNameSpace("http://epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com");
            webServiceInfo.setMethodName("epgAuth");
            webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
//            String transactionID = "90000003" + "20150413152930" + "351231285538348334";
//            webServiceInfo.addParameters(new Pair<String, String>("transactionID", transactionID));
//            webServiceInfo.addParameters(new Pair<String, String>("userToken", token));
//            webServiceInfo.addParameters(new Pair<String, String>("epgURL", "www.baidu.com"));
//            webServiceInfo.addParameters(new Pair<String, String>("epgURL", "www.baidu.com"));

            Object o = JWSUtils.callWebService(webServiceInfo);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String endPoint = "http://124.232.135.227:8298/services/EpgAuthInterface";
        EpgAuthInterface epgAuthInterface = new EpgAuthInterfaceProxy(endPoint);
        try {
            EpgAuthRsp epgAuthRsp = epgAuthInterface.epgAuth(epgAuthReq);
            System.out.println(epgAuthRsp);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void mainTest() throws Exception {
        System.out.println();
        LoginFromMobileBean loginFromMobileBean = callLoginFromMobile();
        System.out.println(loginFromMobileBean + "\n");
        String UserOID = loginFromMobileBean.getUserOID();
        List<ListNoticeTitleBean> listNoticeTitleBeans = callListNoticeTitle(UserOID);
        for (ListNoticeTitleBean listNoticeTitleBean : listNoticeTitleBeans) {
            System.out.println(listNoticeTitleBean);
        }
        System.out.println("\n\n\n");
        for (ListNoticeTitleBean listNoticeTitleBean : listNoticeTitleBeans) {
            String Notice_OID = listNoticeTitleBean.getNotice_OID();
            ListNoticeDetailBean listNoticeDetailBean = callListNoticeDetail(Notice_OID);
            System.out.println("/**************************************************************************************/");
            System.out.println(listNoticeDetailBean);
            List<ListAttachBean> listAttachBeans = callListAttach(listNoticeDetailBean.getAttachmentsOID());
            for (ListAttachBean listAttachBean : listAttachBeans) {
//                System.out.println(listAttachBean);
            }
            System.out.println("/**************************************************************************************/");
            System.out.println();
        }
    }

    private static LoginFromMobileBean callLoginFromMobile(String UserName, String Password, String Mobile) throws Exception {
        JWSConfig info = JWSConfigBuilder.buildLoginFromMobile(UserName, Password, Mobile);
        Schema returnValue = (Schema) JWSUtils.callWebService(info);
        return JWSXmlParser.parseLoginFromMobile(returnValue);
    }

    private static List<ListNoticeTitleBean> callListNoticeTitle(String UserOID) throws Exception {
        JWSConfig info = JWSConfigBuilder.buildListNoticeTitle(UserOID);
        Schema returnValue = (Schema) JWSUtils.callWebService(info);
        return JWSXmlParser.parseListNoticeTitle(returnValue);
    }

    private static ListNoticeDetailBean callListNoticeDetail(String Notice_OID) throws Exception {
        JWSConfig info = JWSConfigBuilder.buildListNoticeDetail(Notice_OID);
        Schema returnValue = (Schema) JWSUtils.callWebService(info);
        return JWSXmlParser.parseListNoticeDetail(returnValue);
    }

    private static List<ListAttachBean> callListAttach(String ATTACHMENT_ID) throws Exception {
        JWSConfig info = JWSConfigBuilder.buildListAttach(ATTACHMENT_ID);
        Schema returnValue = (Schema) JWSUtils.callWebService(info);
        return JWSXmlParser.parseListAttach(returnValue);
    }


    /**
     * test data*
     */

    private static LoginFromMobileBean callLoginFromMobile() throws Exception {
        return callLoginFromMobile("张刚伟", "654321", "13978666622");
    }

    private static List<ListNoticeTitleBean> callListNoticeTitle() throws Exception {
        return callListNoticeTitle("186");
    }

    private static ListNoticeDetailBean callListNoticeDetail() throws Exception {
        return callListNoticeDetail("10000970");
    }

    private static List<ListAttachBean> callListAttach() throws Exception {
        return callListAttach("605824@1401_950179949");
    }

    private static String buildListString(List<?> list) {
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
