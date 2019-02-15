package psdi.iface.app.commons;

import com.ibm.tivoli.maximo.rest.ResourceRequest;

/**
 * 解析ResourceRequestHandler数据通用接口
 * =============================================================
 * Version          Author          Date            Description
 * 1.0              Neal.Y         2017/5/19             创建
 * =============================================================
 */
public interface CustResourceRequestHandlerDataParseable {

    /**
     * 解析资源请求数据
     *
     * @param request Resource Request
     * @return data
     */
    String parse(ResourceRequest request);
}
