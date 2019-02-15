package psdi.iface.app.commons;

import com.ibm.tivoli.maximo.rest.ResourceRequest;
import com.ibm.tivoli.maximo.rest.ResourceRequestHandler;
import com.ibm.tivoli.maximo.rest.ResourceResponse;
import psdi.util.MXException;
import psdi.util.MXSession;

import java.rmi.RemoteException;
import java.util.Set;

/**
 * 资源处理器适配器。
 * 用于对具体资源处理器提供默认业务处理方法，降低业务具体业务资源处理器的实现逻辑。
 * =============================================================
 * Version          Author          Date            Description
 * 1.0              Neal.Y         2017/6/6             创建
 * =============================================================
 */
public class ResourceRequestHandlerAdapter implements ResourceRequestHandler {

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
