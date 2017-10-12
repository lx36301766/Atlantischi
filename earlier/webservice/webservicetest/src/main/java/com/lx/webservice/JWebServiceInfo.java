package com.lx.webservice;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import javafx.util.Pair;

public class JWebServiceInfo {

    private String endpoint = "";
    private String nameSpace = "";
    private String methodName = "";

    private Class<?> returnClass = null;
    private QName returnType = null;

    private Set<Pair<String, String>> parameters = new HashSet<Pair<String, String>>();

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public Class<?> getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class<?> returnClass) {
        this.returnClass = returnClass;
    }

    public QName getReturnType() {
        return returnType;
    }

    public void setReturnType(QName returnType) {
        this.returnType = returnType;
    }

    public Set<Pair<String, String>> getParameters() {
        return parameters;
    }

    public void addParameters(Pair<String, String> parameter) {
        parameters.add(parameter);
    }

}
