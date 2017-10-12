package com.lx.phonerecycle.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RequestParams {

    public static RequestParams build(Object name, Object value) {
        return new RequestParams(String.valueOf(name), String.valueOf(value));
    }

    private String name;
    private String value;

    private boolean encode = true;
    private boolean sign = true;

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public RequestParams(String name) {
        this.name = name;
    }

    public RequestParams(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        try {
            String encode = URLEncoder.encode(value, "utf-8");
            encode = encode.replace("+", "%20");
            return encode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String toString() {
        return "&" + getName() + "=" + getValue();
    }

}
