package com.shuto.mam.webclient.beans.xqsp;

import com.shuto.mam.webclient.beans.base.CAppBean;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2018-02-27 18:35
 * @desc 需求审批appbaen
 * @class com.shuto.mam.webclient.beans.xqsp.XqspAppBean
 * @Copyright: 2018 Shuto版权所有
 **/

public class XqspAppBean extends CAppBean {
    @Override
    public int INSERT() throws MXException, RemoteException {
        super.INSERT();
        MboRemote mbo = this.app.getAppBean().getMbo();
        String createperson = mbo.getString("createperson");
        String where = "personid='" + createperson + "'";
        MboSetRemote msr = mbo.getMboSet("$person", "person", where);
        mbo.setValue("DEPARTMENT", msr.getMbo(0).getString("department"));
        this.app.getAppBean().refreshTable();
        this.app.getAppBean().reloadTable();
        return 1;
    }
}
