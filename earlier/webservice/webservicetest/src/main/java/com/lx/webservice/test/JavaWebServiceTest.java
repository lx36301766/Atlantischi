package com.lx.webservice.test;

import javafx.util.Pair;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;

import com.lx.webservice.JWebServiceInfo;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import java.util.*;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 14-1-17<br>
 * Time: 下午6:50<br>
 */

public class JavaWebServiceTest {

    public static void main(String[] args) {
        JavaWebServiceTest test = new JavaWebServiceTest();
        try {
            //            test.getCityWeatherInfo();
//            test.getMobileCodeInfo();
//            test.getWeatherInfo();

            JWebServiceInfo webServiceInfo = new JWebServiceInfo();

            /*
            webServiceInfo.setEndpoint("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx");
            webServiceInfo.setNameSpace("http://WebXml.com.cn/");
            webServiceInfo.setMethodName("getSupportCity");
            webServiceInfo.setReturnClass(String[].class);
            webServiceInfo.addParameters(new Pair<String, String>("byProvinceName", "北京"));

            webServiceInfo.setEndpoint("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
            webServiceInfo.setNameSpace("http://WebXml.com.cn/");
            webServiceInfo.setMethodName("getMobileCodeInfo");
            webServiceInfo.setReturnClass(String.class);
            webServiceInfo.addParameters(new Pair<String, String>("mobileCode", "15928115878"));
            webServiceInfo.addParameters(new Pair<String, String>("userID", ""));

            webServiceInfo.setEndpoint("http://www.webxml.com.cn/WebServices/WeatherWS.asmx");
            webServiceInfo.setNameSpace("http://WebXml.com.cn/");
            webServiceInfo.setMethodName("getWeather");
            webServiceInfo.setReturnClass(String[].class);
            webServiceInfo.addParameters(new Pair<String, String>("theCityCode", "shanghai"));
            webServiceInfo.addParameters(new Pair<String, String>("theUserID", ""));
            */

            /*
            webServiceInfo.setEndpoint("http://10.1.4.164:8080/sys.asmx");
            webServiceInfo.setNameSpace("http://tempuri.org/");
            webServiceInfo.setMethodName("LoginFromMobile");
            webServiceInfo.setReturnClass(String[].class);
            webServiceInfo.addParameters(new Pair<String, String>("UserName", "张刚伟"));
            webServiceInfo.addParameters(new Pair<String, String>("Password", "654321"));
            webServiceInfo.addParameters(new Pair<String, String>("Mobile", "13978666622"));

            webServiceInfo.setEndpoint("http://10.1.4.164:8080/oa.asmx");
            webServiceInfo.setNameSpace("http://tempuri.org/");
            webServiceInfo.setMethodName("ListNoticeTitle");
            webServiceInfo.setReturnClass(String[].class);
            webServiceInfo.addParameters(new Pair<String, String>("UserOID", "186"));
            */

//            test.call(webServiceInfo);
//            test.loginOASystem();
            test.getOAList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginOASystem() throws Exception {
        String endpoint = "http://10.1.4.164:8080/sys.asmx";
        String nameSpace = "http://tempuri.org/";
        String methodName = "LoginFromMobile";
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(endpoint));
        call.setOperationName(new QName(nameSpace, methodName));
        call.setSOAPActionURI(nameSpace + methodName);
        call.setUseSOAPAction(true);
        call.addParameter(new QName(nameSpace, "UserName"), XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(nameSpace, "Password"), XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(nameSpace, "Mobile"), XMLType.XSD_STRING, ParameterMode.IN);
        call.setReturnClass(String[].class);
//        call.setReturnType(XMLType.SOAP_VECTOR);
//        call.setReturnType(XMLType.XSD_ANYTYPE);
        String[] values = (String[]) call.invoke(new Object[]{"张刚伟", "654321", "13978666622"});
        for (String value : values) {
            System.out.println(value);
        }
    }

    public void getOAList() throws Exception {
        String endpoint = "http://10.1.4.164:8080/oa.asmx";
        String nameSpace = "http://tempuri.org/";
        String methodName = "ListNoticeTitle";
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(endpoint));
        call.setOperationName(new QName(nameSpace, methodName));
        call.setSOAPActionURI(nameSpace + methodName);
        call.setUseSOAPAction(true);
        call.addParameter(new QName(nameSpace, "UserOID"), XMLType.XSD_INTEGER, ParameterMode.IN);
//        call.setReturnClass(java.lang.String[].class);
//        call.setReturnType(XMLType.XSD_SCHEMA);
        call.setReturnType(XMLType.XSD_SCHEMA);
        Schema values = (Schema) call.invoke(new Object[]{"186"});

        MessageElement[] nodes = values.get_any();
        for (MessageElement node : nodes) {
            Iterator<MessageElement> iterator = node.getChildElements();
            while (iterator.hasNext()) {
                MessageElement element = (MessageElement) iterator.next();
                String name = element.getName();
                String value = element.getValue();
                System.out.println(name + "=" + value);
            }
            System.out.println();
        }
    }

    public void getCityWeatherInfo() throws Exception {
        String endpoint = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
        String nameSpace = "http://WebXml.com.cn/";
        String methodName = "getSupportCity";
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(endpoint));
        call.setOperationName(new QName(nameSpace, methodName));
        call.setSOAPActionURI(nameSpace + methodName);
        call.setUseSOAPAction(true);
        call.addParameter(new QName(nameSpace, "byProvinceName"), XMLType.XSD_STRING, ParameterMode.IN);
        call.setReturnClass(String[].class);
//        call.setReturnType(XMLType.XSD_STRING);
//        call.setReturnType(new QName(nameSpace, ""), String[].class);
        String[] values = (String[]) call.invoke(new Object[]{"北京"});
        for (String value : values) {
            System.out.println(value);
        }
    }

    public void getMobileCodeInfo() throws Exception {
        String endpoint = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
        String nameSpace = "http://WebXml.com.cn/";
        String methodName = "getMobileCodeInfo";
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(endpoint));
        call.setOperationName(new QName(nameSpace, methodName));
        call.setSOAPActionURI(nameSpace + methodName);
        call.setUseSOAPAction(true);
        call.addParameter(new QName(nameSpace, "mobileCode"), XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(nameSpace, "userID"), XMLType.XSD_STRING, ParameterMode.IN);
//        call.setReturnClass(java.lang.String.class);
//        call.setReturnType(XMLType.XSD_STRING);
        call.setReturnType(XMLType.SOAP_STRING);
        String result = (String) call.invoke(new Object[]{"15928115878", ""});
        System.out.println(result);
    }

    public void getWeatherInfo() throws Exception {
        String endpoint = "http://www.webxml.com.cn/WebServices/WeatherWS.asmx";
        String nameSpace = "http://WebXml.com.cn/";
        String methodName = "getWeather";
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(endpoint));
        call.setOperationName(new QName(nameSpace, methodName));
        call.setSOAPActionURI(nameSpace + methodName);
        call.setUseSOAPAction(true);
        call.addParameter(new QName(nameSpace, "theCityCode"), XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(nameSpace, "theUserID"), XMLType.XSD_STRING, ParameterMode.IN);
        call.setReturnClass(String[].class);
        String[] values = (String[]) call.invoke(new Object[]{"shanghai", ""});
        for (String value : values) {
            System.out.println(value);
        }
    }

}
