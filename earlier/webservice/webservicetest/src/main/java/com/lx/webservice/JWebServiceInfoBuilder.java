package com.lx.webservice;

import org.apache.axis.encoding.XMLType;

import javafx.util.Pair;

public class JWebServiceInfoBuilder {

    public static JWebServiceInfo buildLoginFromMobile(String UserName, String Password, String Mobile) {
        JWebServiceInfo webServiceInfo = new JWebServiceInfo();
        webServiceInfo.setEndpoint("http://10.1.4.164:8080/sys.asmx");
        webServiceInfo.setNameSpace("http://tempuri.org/");
        webServiceInfo.setMethodName("LoginFromMobile");
        // webServiceInfo.setReturnClass(String[].class);
        webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
        webServiceInfo.addParameters(new Pair<String, String>("UserName", UserName));
        webServiceInfo.addParameters(new Pair<String, String>("Password", Password));
        webServiceInfo.addParameters(new Pair<String, String>("Mobile", Mobile));
        return webServiceInfo;
    }

    public static JWebServiceInfo buildListNoticeTitle(String UserOID) {
        JWebServiceInfo webServiceInfo = new JWebServiceInfo();
        webServiceInfo.setEndpoint("http://10.1.4.164:8080/oa.asmx");
        webServiceInfo.setNameSpace("http://tempuri.org/");
        webServiceInfo.setMethodName("ListNoticeTitle");
        webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
        webServiceInfo.addParameters(new Pair<String, String>("UserOID", UserOID));
        return webServiceInfo;
    }

    public static JWebServiceInfo buildListNoticeDetail(String Notice_OID) {
        JWebServiceInfo webServiceInfo = new JWebServiceInfo();
        webServiceInfo.setEndpoint("http://10.1.4.164:8080/oa.asmx");
        webServiceInfo.setNameSpace("http://tempuri.org/");
        webServiceInfo.setMethodName("ListNoticeDetail");
        webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
        webServiceInfo.addParameters(new Pair<String, String>("Notice_OID", Notice_OID));
        return webServiceInfo;
    }

    /**
     * test data *
     */

    public static JWebServiceInfo buildLoginFromMobile() {
        return buildLoginFromMobile("张刚伟", "654321", "13978666622");
    }

    public static JWebServiceInfo buildListNoticeTitle() {
        return buildListNoticeTitle("186");
    }

    public static JWebServiceInfo buildListNoticeDetail() {
        return buildListNoticeDetail("10000960");
    }

}
