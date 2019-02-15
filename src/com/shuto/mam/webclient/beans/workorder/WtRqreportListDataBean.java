package com.shuto.mam.webclient.beans.workorder;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.io.IOException;
import java.rmi.RemoteException;

public class WtRqreportListDataBean extends DataBean {

    @Override
    public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        MboSetRemote localMboSetRemote2 = mainMbo.getMboSet("$RQREPORT", "RQREPORT");
        // 工作票类型
        String wotype = mainMbo.getString("S_ORDERTYPE");
        String appname = clientSession.getCurrentApp().getApp();
        localMboSetRemote2.setWhere(" appname ='" + appname.toUpperCase() + "' and ( description='" + wotype + "'  or description like '%安全措施附页%')");
        return localMboSetRemote2;

    }

    public int toshowreport() throws IOException, MXException {
        MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
        DataBean localDataBean = this.app.getDataBean("rqreport_table");
        MboSetRemote localMboSetRemote = localDataBean.getMboSet();
        MboRemote localMboRemote2 = localMboSetRemote.getMbo(getCurrentRow());
        String str1 = localMboRemote2.getString("RQREPORTNAME");
        String str2 = localMboRemote2.getString("rqreportnum");
        StringBuffer localStringBuffer = new StringBuffer();
        StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
        url.append("/" + str1.toLowerCase() + ".rpx");
        MboSetRemote rqreportset = localMboRemote1.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM", "rqreportnum = '" + str2 + "'");
        if (!rqreportset.isEmpty()) {
            for (int i = 0; i < rqreportset.count(); i++) {
                url.append("&");
                url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
                url.append("=");
                url.append(localMboRemote1.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
            }
            rqreportset.close();
            this.app.openURL(url.toString(), true);
        } else {
            localStringBuffer.append(str1);
        }
        return 1;

    }
}