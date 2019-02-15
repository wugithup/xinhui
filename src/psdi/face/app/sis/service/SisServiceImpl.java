package psdi.face.app.sis.service;

import psdi.face.app.sis.dto.SisRealDTO;
import psdi.face.app.util.ConnUrlInterfaceUtil;
import psdi.face.app.util.ReadPro;
import psdi.iface.app.util.JsonUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-10-16 15:53
 * @desc
 * @class psdi.face.app.sis.service.SisServiceImpl
 * @Copyright: 2018 Shuto版权所有
 **/

public class SisServiceImpl implements SisService {

    @Override
    public List<SisRealDTO> getSisDto() {
        //获取接口地址
        String urlPath = new ReadPro().getPro().getProperty("sis.real.path");
        List<SisRealDTO> sisDto = null;
        ConnUrlInterfaceUtil conUtil = new ConnUrlInterfaceUtil();
        String xml = conUtil.returnStr(urlPath);
        try {
            sisDto = JsonUtil.fromJsonArray(xml, SisRealDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sisDto;
    }

    @Override
    public List<SisRealDTO> getSisDtoTwo() {
        //获取接口地址
        String urlPath = new ReadPro().getPro().getProperty("sis.real.path2");
        List<SisRealDTO> sisDto = null;
        ConnUrlInterfaceUtil conUtil = new ConnUrlInterfaceUtil();
        String xml = conUtil.returnStr(urlPath);
        try {
            sisDto = JsonUtil.fromJsonArray(xml, SisRealDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sisDto;
    }
}
