/**
 * EpgAuthReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.req;

public class EpgAuthReq implements java.io.Serializable {
    private String transactionID;

    private String epgURL;

    private String userToken;

    public EpgAuthReq() {
    }

    public EpgAuthReq(
            String transactionID,
            String epgURL,
            String userToken) {
        this.transactionID = transactionID;
        this.epgURL = epgURL;
        this.userToken = userToken;
    }


    /**
     * Gets the transactionID value for this EpgAuthReq.
     *
     * @return transactionID
     */
    public String getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this EpgAuthReq.
     *
     * @param transactionID
     */
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the epgURL value for this EpgAuthReq.
     *
     * @return epgURL
     */
    public String getEpgURL() {
        return epgURL;
    }


    /**
     * Sets the epgURL value for this EpgAuthReq.
     *
     * @param epgURL
     */
    public void setEpgURL(String epgURL) {
        this.epgURL = epgURL;
    }


    /**
     * Gets the userToken value for this EpgAuthReq.
     *
     * @return userToken
     */
    public String getUserToken() {
        return userToken;
    }


    /**
     * Sets the userToken value for this EpgAuthReq.
     *
     * @param userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof EpgAuthReq)) return false;
        EpgAuthReq other = (EpgAuthReq) obj;
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
                ((this.epgURL == null && other.getEpgURL() == null) ||
                        (this.epgURL != null &&
                                this.epgURL.equals(other.getEpgURL()))) &&
                ((this.userToken == null && other.getUserToken() == null) ||
                        (this.userToken != null &&
                                this.userToken.equals(other.getUserToken())));
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
        if (getEpgURL() != null) {
            _hashCode += getEpgURL().hashCode();
        }
        if (getUserToken() != null) {
            _hashCode += getUserToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(EpgAuthReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "EpgAuthReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://req.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "transactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("epgURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://req.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "epgURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userToken");
        elemField.setXmlName(new javax.xml.namespace.QName("http://req.epgauth.epg.server.webservice.interfaces.lcsmp.linkage.com", "userToken"));
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
