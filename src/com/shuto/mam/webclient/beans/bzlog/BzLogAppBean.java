package com.shuto.mam.webclient.beans.bzlog;

import psdi.mbo.MboRemote;
import psdi.util.MXAccessException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: BzLogAppBean
 * @Package com.shuto.mam.webclient.beans.bzlog
 * @Description: 班组日志的appbean
 * @date 2019/1/10 17:10
 */
public class BzLogAppBean extends AppBean {

    @Override
    public int DELETE() throws MXException, RemoteException {

        MboRemote mainMbo = getMbo();
        //获取当前登陆用户
        String userName = mainMbo.getUserName();
        //获取当前创建人
        String createBy = mainMbo.getString("CREATEBY");
        if(!userName.equals(createBy))
        {
            throw new MXAccessException("BZLOG","errordelete");
        }



        return super.DELETE();
    }
}
