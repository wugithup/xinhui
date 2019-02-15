package psdi.face.app.sis.handler;

import com.ibm.tivoli.maximo.rest.ResourceRequest;
import com.ibm.tivoli.maximo.rest.ResourceResponse;
import psdi.face.app.base.BaseHandlerAdapter;
import psdi.face.app.sis.dto.SisRealDTO;
import psdi.face.app.sis.service.SisService;
import psdi.face.app.sis.service.SisServiceImpl;
import psdi.face.app.util.SendMsgUtil;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-10-16 15:06
 * @desc http://10.42.5.250/maxrest/rest/sis/unittwo
 * @class psdi.face.app.sis.handler.GetSisRealHandler
 * @Copyright: 2018 Shuto版权所有
 **/

public class GetSisRealHandler extends BaseHandlerAdapter {

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public ResourceResponse handleRequest(ResourceRequest resourceRequest)
            throws RemoteException, MXException {
        /*String resourceName = "查询SIS实时负荷";
        SisService sis = new SisServiceImpl();
        List<SisRealDTO> sisDto = sis.getSisDto();
        return new ResourceResponse(sisDto, "json", APPLICATION_JSON, "json", resourceName);*/
        String resourceName = "查询一号机组负荷";
        String resourceName2 = "查询二号机组负荷";
        SisService sis = new SisServiceImpl();
        List<String> urlList = resourceRequest.getResourcePath();
        if ("unitone".equalsIgnoreCase(urlList.get(0))) {
            List<SisRealDTO> sisDto = sis.getSisDto();
            return new ResourceResponse(sisDto, "json", APPLICATION_JSON, "json", resourceName);
        } else if ("unittwo".equalsIgnoreCase(urlList.get(0))) {
            List<SisRealDTO> sisDto = sis.getSisDtoTwo();
            return new ResourceResponse(sisDto, "json", APPLICATION_JSON, "json", resourceName2);
        } else {
            SendMsgUtil smu = new SendMsgUtil();
            return new ResourceResponse(smu.getErrMsg("输入的用户密码不正确，请重新输入"), "json", APPLICATION_JSON,
                    "json", resourceName);
        }

    }
}
