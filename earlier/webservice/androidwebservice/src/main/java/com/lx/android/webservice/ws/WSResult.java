package com.lx.android.webservice.ws;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 14-1-23<br>
 * Time: 上午12:34<br>
 */

public class WSResult {

    private SoapObject resultObject;
    private String resultXml;
    private String resultString;
    private SoapFault fault;

    public SoapObject getResultObject() {
        return resultObject;
    }

    public void setResultObject(SoapObject resultObject) {
        this.resultObject = resultObject;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public String getResultXml() {
        return resultXml;
    }

    public void setResultXml(String resultXml) {
        this.resultXml = resultXml;
    }

    public SoapFault getFault() {
        return fault;
    }

    public void setFault(SoapFault fault) {
        this.fault = fault;
    }

    @Override
    public String toString() {
        return "WSResult {" +
                "resultObject=" + resultObject +
                ", resultString='" + resultString + '\'' +
                ", resultXml='" + resultXml + '\'' +
                ", fault=" + fault +
                '}';
    }

}
