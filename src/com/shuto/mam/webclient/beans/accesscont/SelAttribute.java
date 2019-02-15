package com.shuto.mam.webclient.beans.accesscont;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2018-03-27 15:02
 * @desc 界面控制选择字段按钮方法
 * @class com.shuto.mam.webclient.beans.accesscont.SelAttribute
 * @Copyright: 2018 Shuto版权所有
 **/

public class SelAttribute extends DataBean {
    @Override
    public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
        super.getMboSetRemote();
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        //根据关联查询出流程对应的对象名
        MboSetRemote wfprocessMsr = mainMbo.getMboSet("PROCESSNAME");
        wfprocessMsr.setOrderBy("PROCESSREV desc");
        MboRemote appmbo = wfprocessMsr.getMbo(0);
        //获取对象名
        String objectname = appmbo.getString("OBJECTNAME");
        String wheresql = "objectname = '" + objectname + "'";
        return mainMbo.getMboSet("maxattributecfg", "maxattributecfg", wheresql);
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {
        StringBuilder attributesb = new StringBuilder();
        MboRemote mbo = app.getAppBean().getMbo();
        String description = mbo.getString("DESCRIPTION");
        Vector selecteLines = getMboSet().getSelection();
        if (!description.isEmpty()) {
            attributesb.append(description).append(",");
        }
        for (int i = 0; i < selecteLines.size(); i++) {
            MboRemote attribute = (MboRemote) selecteLines.elementAt(i);
            attributesb = attributesb.append(attribute.getString("attributename")).append(",");
        }

        if (!"".contentEquals(attributesb)) {
            mbo.setValue("DESCRIPTION", attributesb.substring(0, attributesb.length() - 1), 11L);
        }
        return super.execute();
    }
}
