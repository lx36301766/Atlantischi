package com.lx.webservice.data;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class JWSUtils {

    public static Object callWebService(JWSConfig webServiceInfo) throws ServiceException, MalformedURLException,
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
            call.addParameter(new QName(webServiceInfo.getNameSpace(), para.getKey()), XMLType.XSD_STRING, ParameterMode.IN);
            objects.add(para.getValue());
        }
        if (webServiceInfo.getReturnClass() != null) {
            call.setReturnClass(webServiceInfo.getReturnClass());
        } else {
            call.setReturnType(webServiceInfo.getReturnType());
        }
        return call.invoke(objects.toArray());
    }

}
