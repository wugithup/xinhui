package com.shuto.mam.webclient.beans.cardapply;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-02-05 11:41
 * @desc
 * @class com.shuto.mam.webclient.beans.cardapply.XfDataBean
 * @Copyright: 2018 Shuto版权所有
 **/

public class XfDataBean extends DataBean{

    @Override
    public int addrow() throws MXException {
        try {
            MboRemote mainMbo = this.app.getAppBean().getMbo();
            mainMbo.setValue("XFCARD","1",11L);
            String status = mainMbo.getString("status");
            if(status.equals("已关闭")){
            	throw new MXApplicationException("system", "messager", new String[] { "当前状态下不能进行新建行操作" });
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return super.addrow();
    }


}
