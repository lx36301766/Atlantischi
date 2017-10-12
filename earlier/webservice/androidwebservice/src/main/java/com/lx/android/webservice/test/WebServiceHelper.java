package com.lx.android.webservice.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class WebServiceHelper {

    private String loginOASystem() {
        // 命名空间
        String serviceNameSpace = "http://tempuri.org/";
        // 请求URL
        String serviceURL = "http://10.1.4.164:8080/sys.asmx";
        // 调用方法
        String methodName = "LoginFromMobile";

        String soapAction = serviceNameSpace + methodName;

        // 实例化SoapObject对象
        SoapObject request = new SoapObject(serviceNameSpace, methodName);
        request.addProperty("UserName", "张刚伟");
        request.addProperty("Password", "654321");
        request.addProperty("Mobile", "13978666622");

        // 获得序列化的Envelope
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";

        // 注册Envelope
//        MarshalBase64 marshalBase64 = new MarshalBase64();
//        marshalBase64.register(envelope);

        // Android传输对象
        HttpTransportSE transport = new HttpTransportSE(serviceURL);
        transport.debug = true;
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        System.out.println(result);
        return result;
    }

    private String getOAList() {
        // 命名空间
        String serviceNameSpace = "http://tempuri.org/";
        // 请求URL
        String serviceURL = "http://10.1.4.164:8080/oa.asmx";
        // 调用方法
        String ListNoticeTitle = "ListNoticeTitle";
        String ListNoticeDetail = "ListNoticeDetail";
        String methodName = ListNoticeTitle;

        String soapAction = serviceNameSpace + methodName;

        // 实例化SoapObject对象
        SoapObject request = new SoapObject(serviceNameSpace, methodName);
//        request.addProperty("UserName", "张刚伟");
//        request.addProperty("Password", "654321");
//        request.addProperty("Mobile", "13978666622");
        request.addProperty("UserOID", "186");
//        request.addProperty("Notice_OID", "10000966");

        // 获得序列化的Envelope
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";

        // 注册Envelope
//        MarshalBase64 marshalBase64 = new MarshalBase64();
//        marshalBase64.register(envelope);

        // Android传输对象
        HttpTransportSE transport = new HttpTransportSE(serviceURL);
        transport.debug = true;
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        try {
            String result = object.getProperty(0).toString();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return "nil";
        }
    }

    private void getSupportCity() {
        // 命名空间
        String serviceNameSpace = "http://WebXml.com.cn/";
        // 请求URL
        String serviceURL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
        // 调用方法(获得支持的城市)
        String getSupportCity = "getSupportCity";
        // 调用城市的方法(需要带参数)
        String getWeatherbyCityName = "getWeatherbyCityName";
        // 调用省或者直辖市的方法(获得支持的省份或直辖市)
        String getSupportProvince = "getSupportProvince";

        // SOAP Action
        String soapAction = "http://WebXml.com.cn/getSupportCity";

        // 实例化SoapObject对象
        SoapObject request = new SoapObject(serviceNameSpace, getSupportCity);
        request.addProperty("byProvinceName", "北京");

        // 获得序列化的Envelope
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";

        // 注册Envelope
        MarshalBase64 marshalBase64 = new MarshalBase64();
        marshalBase64.register(envelope);

        // Android传输对象
        HttpTransportSE transport = new HttpTransportSE(serviceURL);
        transport.debug = true;
        try {
            transport.call(soapAction, envelope);
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            Log.w("lx", e.getMessage(), e);
        }
        try {
            if (envelope.getResponse() != null) {
//                List<String> citysList = parse(envelope.bodyIn.toString());
                SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                int count = bodyIn.getPropertyCount();
                String result = bodyIn.getProperty(0).toString();
                System.out.println(bodyIn);
            }
        } catch (SoapFault e) {
            e.printStackTrace();
        }
    }

    /**
     * 手机号段归属地查询
     *
     * @param phoneSec 手机号段
     */
    public void getRemoteInfo(String phoneSec) {
        // 命名空间
        String nameSpace = "http://WebXml.com.cn/";
        // 调用的方法名称
        String methodName = "getMobileCodeInfo";
        // EndPoint
        String endPoint = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
        // SOAP Action
        String soapAction = "http://WebXml.com.cn/getMobileCodeInfo";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("mobileCode", phoneSec);
        rpc.addProperty("userId", "");

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();

        System.out.println(result);
        // 将WebService返回的结果显示在TextView中
        // resultView.setText(result);
    }

}
