package com.lx.android.webservice.ws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.SoapFault12;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import android.util.Log;
import android.util.Pair;
import org.ksoap2.transport.HttpTransportSE;

public class WSUtils {

    public static WSResult callWebService(WSConfig config) {
        // 命名空间
        String serviceNameSpace = config.getNameSpace();
        // 请求URL
        String serviceURL = config.getServiceUrl();
        // 调用方法
        String methodName = config.getMethodName();
        // 调用的soap action
        String soapAction = serviceNameSpace + methodName;
        // 实例化SoapObject对象
        SoapObject request = new SoapObject(serviceNameSpace, methodName);
        for (Pair<String, String> para : config.getParameters()) {
            request.addProperty(para.first, para.second);
        }
        // 获得序列化的Envelope
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";
        // 传输对象
        HttpTransportSE transport = new HttpTransportSE(serviceURL);
        transport.debug = true;
        try {
            // 调用WebService，最后一个参数可传入一个文件，将返回的xml数据写到本地文件里
            transport.call(soapAction, envelope, null, null);
        } catch (Exception e) {
            Log.e("lx", e.getMessage(), e);
            return null;
        }
        //返回的XML数据，如果上面call方法第四个参数传入了一个文件，这里得到的是FileOutputStream数据
        String xml = transport.responseDump;
        Log.e("lx", "result xml : \n" + xml + "\n");
        // 获取返回的数据
        WSResult result = new WSResult();
        if (envelope.bodyIn instanceof SoapObject) {
            SoapObject object = (SoapObject) envelope.bodyIn;
            result.setResultObject(object);
            String str = object.getProperty(0).toString();
            result.setResultString(str);
        } else if (envelope.bodyIn instanceof SoapFault12) {
            SoapFault12 fault = (SoapFault12) envelope.bodyIn;
            result.setFault(fault);
            String str = fault.toString();
            result.setResultString(str);
        } else if (envelope.bodyIn instanceof SoapFault) {
            SoapFault fault = (SoapFault) envelope.bodyIn;
            result.setFault(fault);
            String str = fault.toString();
            result.setResultString(str);
        } else {
            throw new IllegalStateException("Unknown Object : " + envelope.bodyIn.getClass().getName());
        }
        Log.e("lx", "WSResult = " + result + "\n");
        return result;
    }

}
