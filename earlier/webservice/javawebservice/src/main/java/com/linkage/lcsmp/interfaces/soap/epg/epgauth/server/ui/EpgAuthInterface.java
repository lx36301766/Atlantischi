/**
 * EpgAuthInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkage.lcsmp.interfaces.soap.epg.epgauth.server.ui;

import com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.req.EpgAuthReq;
import com.linkage.lcsmp.interfaces.webservice.server.epg.epgauth.rsp.EpgAuthRsp;

public interface EpgAuthInterface extends java.rmi.Remote {
    public EpgAuthRsp epgAuth(EpgAuthReq req) throws java.rmi.RemoteException;
}
