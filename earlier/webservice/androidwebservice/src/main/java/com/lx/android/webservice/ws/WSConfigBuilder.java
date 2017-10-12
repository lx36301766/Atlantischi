package com.lx.android.webservice.ws;

import android.util.Pair;

public class WSConfigBuilder {

    public static WSConfig buildLoginFromMobile(String UserName, String Password, String Mobile) {
        WSConfig config = new WSConfig();
        config.setNameSpace(WSUrl.NAMESPACE);
        config.setServiceUrl(WSUrl.SYS_URL);
        config.setMethodName("LoginFromMobile");
        config.addParameters(Pair.create("UserName", UserName));
        config.addParameters(Pair.create("Password", Password));
        config.addParameters(Pair.create("Mobile", Mobile));
        return config;
    }

    public static WSConfig buildListNoticeTitle(String UserOID) {
        WSConfig config = new WSConfig();
        config.setNameSpace(WSUrl.NAMESPACE);
        config.setServiceUrl(WSUrl.OA_URL);
        config.setMethodName("ListNoticeTitle");
        config.addParameters(Pair.create("UserOID", UserOID));
        return config;
    }

    public static WSConfig buildListNoticeDetail(String Notice_OID) {
        WSConfig config = new WSConfig();
        config.setNameSpace(WSUrl.NAMESPACE);
        config.setServiceUrl(WSUrl.OA_URL);
        config.setMethodName("ListNoticeDetail");
        config.addParameters(Pair.create("Notice_OID", Notice_OID));
        return config;
    }

    public static WSConfig buildListAttach(String ATTACHMENT_ID) {
        WSConfig config = new WSConfig();
        config.setNameSpace(WSUrl.NAMESPACE);
        config.setServiceUrl(WSUrl.OA_URL);
        config.setMethodName("ListAttach");
        config.addParameters(Pair.create("ATTACHMENT_ID", ATTACHMENT_ID));
        return config;
    }


    /**
     * 下面是网上一些可用于测试的开放WebService地址*
     */

    public static WSConfig buildMobile() {
        WSConfig config = new WSConfig();
        config.setNameSpace("http://WebXml.com.cn/");
        config.setServiceUrl("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
        //getMobileCodeInfo
        //getDatabaseInfo
        config.setMethodName("getMobileCodeInfo");
        config.addParameters(Pair.create("mobileCode", "15882304213"));
        config.addParameters(Pair.create("userId", ""));
        return config;
    }

    public static WSConfig buildCityWeather() {
        WSConfig config = new WSConfig();
        config.setNameSpace("http://WebXml.com.cn/");
        config.setServiceUrl("http://www.webxml.com.cn/webservices/weatherwebservice.asmx");
        //getSupportCity
        //getSupportDataSet
        //getSupportProvince
        //getWeatherbyCityName
        config.setMethodName("getSupportCity");
        config.addParameters(Pair.create("byProvinceName", "北京"));
        return config;
    }

    public static WSConfig buildCityWeather2() {
        WSConfig config = new WSConfig();
        config.setNameSpace("http://WebXml.com.cn/");
        config.setServiceUrl("http://www.webxml.com.cn/WebServices/WeatherWS.asmx");
        //getRegionCountry
        //getRegionDataset
        //getRegionProvince
        //getSupportCityDataset
        //getSupportCityString
        //getWeather
        config.setMethodName("getRegionCountry");
        return config;
    }

}
