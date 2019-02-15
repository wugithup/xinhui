package psdi.face.app.todo.handler;

import com.ibm.tivoli.maximo.rest.HttpHeaders;
import com.ibm.tivoli.maximo.rest.ResourceRequest;
import com.ibm.tivoli.maximo.rest.ResourceResponse;
import org.dom4j.DocumentException;
import psdi.face.app.base.BaseHandlerAdapter;
import psdi.face.app.todo.dto.OaTodoDto;
import psdi.face.app.todo.dto.WfCountDTO;
import psdi.face.app.todo.service.WfService;
import psdi.face.app.todo.service.WfServiceImpl;
import psdi.face.app.util.ConnetOaTodoUtil;
import psdi.face.app.util.SendMsgUtil;
import psdi.util.MXException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-09-04 9:39
 * @desc http://10.42.5.250/maxrest/rest/wfassignment/eam
 * @Copyright: 2018 Shuto版权所有
 **/

public class GetTodoList extends BaseHandlerAdapter {

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public ResourceResponse handleRequest(ResourceRequest resourceRequest) throws RemoteException, MXException {
        String resourceName = "查询EAM待办";
        String resourceName2 = "查询OA待办";
        List<String> urlList = resourceRequest.getResourcePath();
        List<WfCountDTO> wfList;
        int size = urlList.size();
        SendMsgUtil smu = new SendMsgUtil();
        if (size == 1) {
            HttpHeaders head = resourceRequest.getHeaderParams();
            if ("eam".equalsIgnoreCase(urlList.get(0))) {
                String personid = head.getHeader("PERSONID").get(0);
                WfService wfService = new WfServiceImpl();
                wfList = wfService.getWfCount(personid);
                return new ResourceResponse(wfList, "json", APPLICATION_JSON, "json", resourceName);
            } else if ("oa".equalsIgnoreCase(urlList.get(0))) {
                String personid = head.getHeader("PERSONID").get(0);
                String password = head.getHeader("PASSWORD").get(0);
                ConnetOaTodoUtil cotu = new ConnetOaTodoUtil();
                OaTodoDto dto = null;
                try {
                    dto = cotu.getOaTodo(personid, password);
                } catch (IOException | DocumentException e) {
                    return new ResourceResponse(smu.getErrMsg("输入的用户密码不正确，请重新输入"), "json", APPLICATION_JSON, "json", resourceName);
                }
                List<OaTodoDto> list = new ArrayList<>();
                list.add(dto);
                return new ResourceResponse(list, "json", APPLICATION_JSON, "json", resourceName2);
            } else {
                return new ResourceResponse(smu.getErrMsg("输入的网址有误，请重新输入"), "json", APPLICATION_JSON, "json", resourceName);
            }
        } else {
            return new ResourceResponse(smu.getErrMsg("输入的网址有误，请重新输入"), "json", APPLICATION_JSON, "json", resourceName);
        }
    }

}
