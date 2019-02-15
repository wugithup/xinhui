package psdi.iface.app.commons;

import com.ibm.tivoli.maximo.rest.MaximoRestServlet;
import com.ibm.tivoli.maximo.rest.ResourceRequest;

/**
 * 请求数据解析器的默认实现
 * =============================================================
 * Version          Author          Date            Description
 * 1.0              Neal.Y         2017/5/19             创建
 * =============================================================
 */
public class DefaultResourceRequestHandlerDataParseableImpl implements CustResourceRequestHandlerDataParseable {

    @Override
    public String parse(ResourceRequest request) {

        String[] dataArray = request.getQueryParams().get(MaximoRestServlet.CUST_MAX_POST_DATA_KEY);
        if (dataArray == null || dataArray.length == 0) {
            return null;
        }
        return dataArray[0];
    }
}
