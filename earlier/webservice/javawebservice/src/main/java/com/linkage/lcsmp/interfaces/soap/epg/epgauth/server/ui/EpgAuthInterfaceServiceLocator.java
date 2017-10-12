/**
 * EpgAuthInterfaceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.lcsmp.interfaces.soap.epg.epgauth.server.ui;

public class EpgAuthInterfaceServiceLocator extends org.apache.axis.client.Service implements EpgAuthInterfaceService {

    public EpgAuthInterfaceServiceLocator() {
    }


    public EpgAuthInterfaceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EpgAuthInterfaceServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EpgAuthInterface
    private String EpgAuthInterface_address = "http://124.232.135.227:8298/services/EpgAuthInterface";

    public String getEpgAuthInterfaceAddress() {
        return EpgAuthInterface_address;
    }

    // The WSDD service name defaults to the port name.
    private String EpgAuthInterfaceWSDDServiceName = "EpgAuthInterface";

    public String getEpgAuthInterfaceWSDDServiceName() {
        return EpgAuthInterfaceWSDDServiceName;
    }

    public void setEpgAuthInterfaceWSDDServiceName(String name) {
        EpgAuthInterfaceWSDDServiceName = name;
    }

    public EpgAuthInterface getEpgAuthInterface() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EpgAuthInterface_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEpgAuthInterface(endpoint);
    }

    public EpgAuthInterface getEpgAuthInterface(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            EpgAuthInterfaceSoapBindingStub _stub = new EpgAuthInterfaceSoapBindingStub(portAddress, this);
            _stub.setPortName(getEpgAuthInterfaceWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEpgAuthInterfaceEndpointAddress(String address) {
        EpgAuthInterface_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (EpgAuthInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                EpgAuthInterfaceSoapBindingStub _stub = new EpgAuthInterfaceSoapBindingStub(new java.net.URL(EpgAuthInterface_address), this);
                _stub.setPortName(getEpgAuthInterfaceWSDDServiceName());
                return _stub;
            }
        } catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("EpgAuthInterface".equals(inputPortName)) {
            return getEpgAuthInterface();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ui.server.epgauth.epg.soap.interfaces.lcsmp.linkage.com", "EpgAuthInterfaceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ui.server.epgauth.epg.soap.interfaces.lcsmp.linkage.com", "EpgAuthInterface"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

        if ("EpgAuthInterface".equals(portName)) {
            setEpgAuthInterfaceEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
