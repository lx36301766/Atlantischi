package com.linkage.lcsmp.interfaces.soap.epg.epgauth.server.ui;

import com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.req.EpgAuthReq;
import com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.rsp.EpgAuthRsp;

public class EpgAuthInterfaceProxy implements EpgAuthInterface {
    private String _endpoint = null;
    private EpgAuthInterface epgAuthInterface = null;

    public EpgAuthInterfaceProxy() {
        _initEpgAuthInterfaceProxy();
    }

    public EpgAuthInterfaceProxy(String endpoint) {
        _endpoint = endpoint;
        _initEpgAuthInterfaceProxy();
    }

    private void _initEpgAuthInterfaceProxy() {
        try {
            epgAuthInterface = (new EpgAuthInterfaceServiceLocator()).getEpgAuthInterface();
            if (epgAuthInterface != null) {
                if (_endpoint != null)
                    ((javax.xml.rpc.Stub) epgAuthInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                else
                    _endpoint = (String) ((javax.xml.rpc.Stub) epgAuthInterface)._getProperty("javax.xml.rpc.service.endpoint.address");
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (epgAuthInterface != null)
            ((javax.xml.rpc.Stub) epgAuthInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    }

    public EpgAuthInterface getEpgAuthInterface() {
        if (epgAuthInterface == null)
            _initEpgAuthInterfaceProxy();
        return epgAuthInterface;
    }

    public EpgAuthRsp epgAuth(EpgAuthReq req) throws java.rmi.RemoteException {
        if (epgAuthInterface == null)
            _initEpgAuthInterfaceProxy();
        return epgAuthInterface.epgAuth(req);
    }

}