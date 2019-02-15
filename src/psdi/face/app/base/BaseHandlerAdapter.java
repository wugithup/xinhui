/*
 * Copyright (c) 2018. Shuto版权所有
 */

package psdi.face.app.base;

import com.ibm.tivoli.maximo.rest.ResourceRequest;
import com.ibm.tivoli.maximo.rest.ResourceRequestHandler;
import com.ibm.tivoli.maximo.rest.ResourceResponse;
import psdi.util.MXException;
import psdi.util.MXSession;

import java.rmi.RemoteException;
import java.util.Set;

public class BaseHandlerAdapter implements ResourceRequestHandler {

    @Override
    public ResourceResponse handleRequest(ResourceRequest resourceRequest)
            throws RemoteException, MXException {

        return null;
    }

    @Override
    public void setBlockAccessList(Set<String> set) {

    }

    @Override
    public void setMXSession(MXSession mxSession) {

    }

    @Override
    public void setHandlerName(String s) {

    }

    @Override
    public boolean isRoot() {

        return false;
    }

    @Override
    public void setRoot(boolean b) {

    }
}
