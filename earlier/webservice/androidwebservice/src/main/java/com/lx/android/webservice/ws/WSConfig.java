package com.lx.android.webservice.ws;

import java.util.HashSet;
import java.util.Set;

import android.util.Pair;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 14-1-18<br>
 * Time: 下午8:43<br>
 */

public class WSConfig {

    private String serviceUrl = "";
    private String nameSpace = "";
    private String methodName = "";

    private Set<Pair<String, String>> parameters = new HashSet<Pair<String, String>>();

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Set<Pair<String, String>> getParameters() {
        return parameters;
    }

    public void addParameters(Pair<String, String> parameter) {
        parameters.add(parameter);
    }

}
