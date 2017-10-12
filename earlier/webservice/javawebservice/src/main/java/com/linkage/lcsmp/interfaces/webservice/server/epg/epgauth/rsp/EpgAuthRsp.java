/**
 * EpgAuthRsp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.rsp;

public class EpgAuthRsp implements java.io.Serializable {
    private String transactionID;

    private String description;

    private String epgModelID;

    private String mac;

    private String products;

    private String result;

    private String stbID;

    private String tokenExpiredTime;

    private String userGroupNMB;

    private String userID;

    private String areano;

    private String tradeID;

    private String curChannelID;

    public EpgAuthRsp() {
    }

    public EpgAuthRsp(
            String transactionID,
            String description,
            String epgModelID,
            String mac,
            String products,
            String result,
            String stbID,
            String tokenExpiredTime,
            String userGroupNMB,
            String userID,
            String areano,
            String tradeID,
            String curChannelID) {
        this.transactionID = transactionID;
        this.description = description;
        this.epgModelID = epgModelID;
        this.mac = mac;
        this.products = products;
        this.result = result;
        this.stbID = stbID;
        this.tokenExpiredTime = tokenExpiredTime;
        this.userGroupNMB = userGroupNMB;
        this.userID = userID;
        this.areano = areano;
        this.tradeID = tradeID;
        this.curChannelID = curChannelID;
    }


    /**
     * Gets the transactionID value for this EpgAuthRsp.
     *
     * @return transactionID
     */
    public String getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this EpgAuthRsp.
     *
     * @param transactionID
     */
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the description value for this EpgAuthRsp.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this EpgAuthRsp.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Gets the epgModelID value for this EpgAuthRsp.
     *
     * @return epgModelID
     */
    public String getEpgModelID() {
        return epgModelID;
    }


    /**
     * Sets the epgModelID value for this EpgAuthRsp.
     *
     * @param epgModelID
     */
    public void setEpgModelID(String epgModelID) {
        this.epgModelID = epgModelID;
    }


    /**
     * Gets the mac value for this EpgAuthRsp.
     *
     * @return mac
     */
    public String getMac() {
        return mac;
    }


    /**
     * Sets the mac value for this EpgAuthRsp.
     *
     * @param mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }


    /**
     * Gets the products value for this EpgAuthRsp.
     *
     * @return products
     */
    public String getProducts() {
        return products;
    }


    /**
     * Sets the products value for this EpgAuthRsp.
     *
     * @param products
     */
    public void setProducts(String products) {
        this.products = products;
    }


    /**
     * Gets the result value for this EpgAuthRsp.
     *
     * @return result
     */
    public String getResult() {
        return result;
    }


    /**
     * Sets the result value for this EpgAuthRsp.
     *
     * @param result
     */
    public void setResult(String result) {
        this.result = result;
    }


    /**
     * Gets the stbID value for this EpgAuthRsp.
     *
     * @return stbID
     */
    public String getStbID() {
        return stbID;
    }


    /**
     * Sets the stbID value for this EpgAuthRsp.
     *
     * @param stbID
     */
    public void setStbID(String stbID) {
        this.stbID = stbID;
    }


    /**
     * Gets the tokenExpiredTime value for this EpgAuthRsp.
     *
     * @return tokenExpiredTime
     */
    public String getTokenExpiredTime() {
        return tokenExpiredTime;
    }


    /**
     * Sets the tokenExpiredTime value for this EpgAuthRsp.
     *
     * @param tokenExpiredTime
     */
    public void setTokenExpiredTime(String tokenExpiredTime) {
        this.tokenExpiredTime = tokenExpiredTime;
    }


    /**
     * Gets the userGroupNMB value for this EpgAuthRsp.
     *
     * @return userGroupNMB
     */
    public String getUserGroupNMB() {
        return userGroupNMB;
    }


    /**
     * Sets the userGroupNMB value for this EpgAuthRsp.
     *
     * @param userGroupNMB
     */
    public void setUserGroupNMB(String userGroupNMB) {
        this.userGroupNMB = userGroupNMB;
    }


    /**
     * Gets the userID value for this EpgAuthRsp.
     *
     * @return userID
     */
    public String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this EpgAuthRsp.
     *
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }


    /**
     * Gets the areano value for this EpgAuthRsp.
     *
     * @return areano
     */
    public String getAreano() {
        return areano;
    }


    /**
     * Sets the areano value for this EpgAuthRsp.
     *
     * @param areano
     */
    public void setAreano(String areano) {
        this.areano = areano;
    }


    /**
     * Gets the tradeID value for this EpgAuthRsp.
     *
     * @return tradeID
     */
    public String getTradeID() {
        return tradeID;
    }


    /**
     * Sets the tradeID value for this EpgAuthRsp.
     *
     * @param tradeID
     */
    public void setTradeID(String tradeID) {
        this.tradeID = tradeID;
    }


    /**
     * Gets the curChannelID value for this EpgAuthRsp.
     *
     * @return curChannelID
     */
    public String getCurChannelID() {
        return curChannelID;
    }


    /**
     * Sets the curChannelID value for this EpgAuthRsp.
     *
     * @param curChannelID
     */
    public void setCurChannelID(String curChannelID) {
        this.curChannelID = curChannelID;
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof EpgAuthRsp)) return false;
        EpgAuthRsp other = (EpgAuthRsp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.transactionID == null && other.getTransactionID() == null) ||
                        (this.transactionID != null &&
                                this.transactionID.equals(other.getTransactionID()))) &&
                ((this.description == null && other.getDescription() == null) ||
                        (this.description != null &&
                                this.description.equals(other.getDescription()))) &&
                ((this.epgModelID == null && other.getEpgModelID() == null) ||
                        (this.epgModelID != null &&
                                this.epgModelID.equals(other.getEpgModelID()))) &&
                ((this.mac == null && other.getMac() == null) ||
                        (this.mac != null &&
                                this.mac.equals(other.getMac()))) &&
                ((this.products == null && other.getProducts() == null) ||
                        (this.products != null &&
                                this.products.equals(other.getProducts()))) &&
                ((this.result == null && other.getResult() == null) ||
                        (this.result != null &&
                                this.result.equals(other.getResult()))) &&
                ((this.stbID == null && other.getStbID() == null) ||
                        (this.stbID != null &&
                                this.stbID.equals(other.getStbID()))) &&
                ((this.tokenExpiredTime == null && other.getTokenExpiredTime() == null) ||
                        (this.tokenExpiredTime != null &&
                                this.tokenExpiredTime.equals(other.getTokenExpiredTime()))) &&
                ((this.userGroupNMB == null && other.getUserGroupNMB() == null) ||
                        (this.userGroupNMB != null &&
                                this.userGroupNMB.equals(other.getUserGroupNMB()))) &&
                ((this.userID == null && other.getUserID() == null) ||
                        (this.userID != null &&
                                this.userID.equals(other.getUserID()))) &&
                ((this.areano == null && other.getAreano() == null) ||
                        (this.areano != null &&
                                this.areano.equals(other.getAreano()))) &&
                ((this.tradeID == null && other.getTradeID() == null) ||
                        (this.tradeID != null &&
                                this.tradeID.equals(other.getTradeID()))) &&
                ((this.curChannelID == null && other.getCurChannelID() == null) ||
                        (this.curChannelID != null &&
                                this.curChannelID.equals(other.getCurChannelID())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTransactionID() != null) {
            _hashCode += getTransactionID().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEpgModelID() != null) {
            _hashCode += getEpgModelID().hashCode();
        }
        if (getMac() != null) {
            _hashCode += getMac().hashCode();
        }
        if (getProducts() != null) {
            _hashCode += getProducts().hashCode();
        }
        if (getResult() != null) {
            _hashCode += getResult().hashCode();
        }
        if (getStbID() != null) {
            _hashCode += getStbID().hashCode();
        }
        if (getTokenExpiredTime() != null) {
            _hashCode += getTokenExpiredTime().hashCode();
        }
        if (getUserGroupNMB() != null) {
            _hashCode += getUserGroupNMB().hashCode();
        }
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        if (getAreano() != null) {
            _hashCode += getAreano().hashCode();
        }
        if (getTradeID() != null) {
            _hashCode += getTradeID().hashCode();
        }
        if (getCurChannelID() != null) {
            _hashCode += getCurChannelID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(EpgAuthRsp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "EpgAuthRsp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "transactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("epgModelID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "epgModelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mac");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "mac"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("products");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "products"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stbID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "stbID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tokenExpiredTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "tokenExpiredTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userGroupNMB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "userGroupNMB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "userID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areano");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "areano"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tradeID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "tradeID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curChannelID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://rsp.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "curChannelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}
