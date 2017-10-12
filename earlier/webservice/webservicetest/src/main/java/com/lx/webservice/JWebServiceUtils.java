package com.lx.webservice;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.util.Pair;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;

public class JWebServiceUtils {

    public static Object callWebService(JWebServiceInfo webServiceInfo) throws ServiceException, MalformedURLException,
            RemoteException {
        if (webServiceInfo == null) {
            return null;
        }
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(webServiceInfo.getEndpoint()));
        call.setOperationName(new QName(webServiceInfo.getNameSpace(), webServiceInfo.getMethodName()));
        call.setSOAPActionURI(webServiceInfo.getNameSpace() + webServiceInfo.getMethodName());
        call.setUseSOAPAction(true);
        List<Object> objects = new ArrayList<Object>();
        for (Pair<String, String> para : webServiceInfo.getParameters()) {
            call.addParameter(new QName(webServiceInfo.getNameSpace(), para.getKey()), XMLType.XSD_STRING,
                    ParameterMode.IN);
            objects.add(para.getValue());
        }
        if (webServiceInfo.getReturnClass() != null) {
            call.setReturnClass(webServiceInfo.getReturnClass());
        } else {
            call.setReturnType(webServiceInfo.getReturnType());
        }
        Object values = call.invoke(objects.toArray());
        // if (values instanceof String[]) {
        // String[] v = (String[]) values;
        // for (String value : v) {
        // System.out.println(value);
        // }
        // } else if (values instanceof String) {
        // System.out.println(values);
        // }
        return values;
    }

}
