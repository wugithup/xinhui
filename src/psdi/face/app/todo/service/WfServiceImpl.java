package psdi.face.app.todo.service;

import psdi.face.app.todo.dto.WfCountDTO;
import psdi.face.app.todo.dto.WfDTO;
import psdi.face.app.util.ReadPro;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-09-04 9:51
 * @desc
 * @Copyright: 2018 Shuto版权所有
 **/

public class WfServiceImpl implements WfService {

    @Override
    public List<WfCountDTO> getWfCount(String personid) {

        List<WfCountDTO> responseResultList = new ArrayList<>();
//        WfDTO wfDto =
        MXServer mxServer;
        UserInfo systemUserInfo;
        String servletBase = new ReadPro().getPro().getProperty("maximo.path");
        try {
            mxServer = MXServer.getMXServer();
            systemUserInfo = mxServer.getSystemUserInfo();
            MboSetRemote wfMboSet = mxServer.getMboSet("WFASSIGNMENT", systemUserInfo);
            wfMboSet.setWhere(" assignstatus='活动' and ASSIGNCODE = '" + personid + "'");
            wfMboSet.reset();
            int cou = wfMboSet.count();
            WfCountDTO wfDto = new WfCountDTO();
            wfDto.setCount(cou);
            wfDto.setUrl(servletBase);
            wfDto.setPersonid(personid);
            responseResultList.add(wfDto);
        } catch (RemoteException | MXException e) {
            e.printStackTrace();
        }

        return responseResultList;
    }

    @Override
    public List<WfDTO> getWfList(String personid) {
        List<WfDTO> responseResultList = new ArrayList<>();
        MXServer mxServer;
        UserInfo systemUserInfo;
        try {
            mxServer = MXServer.getMXServer();
            systemUserInfo = mxServer.getSystemUserInfo();
            MboSetRemote wfMboSet = mxServer.getMboSet("WFASSIGNMENT", systemUserInfo);
            wfMboSet.setWhere(" assignstatus='活动' and ASSIGNCODE = '" + personid + "'");
            wfMboSet.reset();
            int cou = wfMboSet.count();
            String servletBase;
            String appToGo;
            long ownerid;
            String url;
            servletBase = new ReadPro().getPro().getProperty("maximo.path");
            for (int i = 0; i < cou; i++) {
                MboRemote wfMbo = wfMboSet.getMbo(i);
                WfDTO wfDto = new WfDTO();
                wfDto.setDescription(wfMbo.getString("DESCRIPTION"));
                wfDto.setPersonid(wfMbo.getString("ASSIGNCODE"));
                wfDto.setWfid(wfMbo.getLong("WFID") + "");
                wfDto.setCount(cou);
                appToGo = wfMbo.getString("app");
                ownerid = wfMbo.getLong("ownerid");
                url = servletBase + "sso?event=loadapp&value=" + appToGo + "&additionalevent=inboxwf&uniqueid="
                        + ownerid + "";
                wfDto.setUrl(url);
                responseResultList.add(wfDto);
            }
            wfMboSet.close();
        } catch (RemoteException | MXException e) {
            e.printStackTrace();
        }
        return responseResultList;
    }

}
