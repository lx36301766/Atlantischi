package com.lx.webservice.data;

import org.apache.axis.encoding.XMLType;

import javafx.util.Pair;

public class JWSConfigBuilder {

    public static JWSConfig buildLoginFromMobile(String UserName, String Password, String Mobile) {
        JWSConfig webServiceInfo = new JWSConfig();
        webServiceInfo.setEndpoint(JWSUrl.SYS_URL);
        webServiceInfo.setNameSpace(JWSUrl.NAMESPACE);
        webServiceInfo.setMethodName("LoginFromMobile");
        // webServiceInfo.setReturnClass(String[].class);
        webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
        webServiceInfo.addParameters(new Pair<String, String>("UserName", UserName));
        webServiceInfo.addParameters(new Pair<String, String>("Password", Password));
        webServiceInfo.addParameters(new Pair<String, String>("Mobile", Mobile));
        return webServiceInfo;
    }

    public static JWSConfig buildListNoticeTitle(String UserOID) {
        JWSConfig webServiceInfo = new JWSConfig();
        webServiceInfo.setEndpoint(JWSUrl.OA_URL);
        webServiceInfo.setNameSpace(JWSUrl.NAMESPACE);
        webServiceInfo.setMethodName("ListNoticeTitle");
        webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
        webServiceInfo.addParameters(new Pair<String, String>("UserOID", UserOID));
        return webServiceInfo;
    }

    public static JWSConfig buildListNoticeDetail(String Notice_OID) {
        JWSConfig webServiceInfo = new JWSConfig();
        webServiceInfo.setEndpoint(JWSUrl.OA_URL);
        webServiceInfo.setNameSpace(JWSUrl.NAMESPACE);
        webServiceInfo.setMethodName("ListNoticeDetail");
        webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
        webServiceInfo.addParameters(new Pair<String, String>("Notice_OID", Notice_OID));
        return webServiceInfo;
    }

    public static JWSConfig buildListAttach(String ATTACHMENT_ID) {
        JWSConfig webServiceInfo = new JWSConfig();
        webServiceInfo.setEndpoint(JWSUrl.OA_URL);
        webServiceInfo.setNameSpace(JWSUrl.NAMESPACE);
        webServiceInfo.setMethodName("ListAttach");
        webServiceInfo.setReturnType(XMLType.XSD_SCHEMA);
        webServiceInfo.addParameters(new Pair<String, String>("ATTACHMENT_ID", ATTACHMENT_ID));
        return webServiceInfo;
    }

}
