/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.purchase.servicesq;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;
import java.util.Calendar;

public class ServicesqBean extends AppBean {

    public boolean hasAuth() throws MXException, RemoteException {

        long tablesid = getMbo().getLong("SERVICESQID");
        String s = getMbo().getString("STATUS");
        String s1 = getMbo().getUserInfo().getPersonId();
        if ("maxadmin".equalsIgnoreCase(s1)) {
            return true;
        }
        if ("等待批准".equals(s)) {
            return true;
        }
        MboSetRemote mbosetremote = getMbo().getMboSet("$INSTANCE", "WFINSTANCE",
                "OWNERTABLE='SERVICESQ' AND OWNERID='" + tablesid + "' AND ACTIVE = 1");
        if (!mbosetremote.isEmpty()) {
            String s2 = "OWNERID='" + tablesid +
                    "' AND OWNERTABLE='SERVICESQ' AND ASSIGNSTATUS='活动' AND ASSIGNCODE='" + s1 +
                    "'";
            MboSetRemote mbosetremote1 = getMbo().getMboSet("$ASSIGNCODE", "WFASSIGNMENT", s2);
            return !mbosetremote1.isEmpty();
        }
        return false;
    }

    public int ROUTEWF() throws MXException, RemoteException {

        if (!hasAuth()) {
            throw new MXApplicationException("system", "SYSROUTEWF2");
        }
        return super.ROUTEWF();
    }

    public int SAVE() throws MXException, RemoteException {

        MboRemote mbo = this.app.getAppBean().getMbo();

        String personid = mbo.getUserInfo().getPersonId();

        String createperson = mbo.getString("CREATEPERSON");
        if (!hasAuth()) {
            throw new MXApplicationException("system", "SYSSAVE2");
        }

        if (("等待批准".equals(getString("status"))) && (!createperson.equals(personid)) &&
                (!"MAXADMIN".equals(personid))) {
            throw new MXApplicationException("system", "SYSSAVE2");
        }

        String num = mbo.getString("NUM");

        Calendar now = Calendar.getInstance();

        String year3 = String.format("%tY", now.getTime());

        if ((num == null) || ("".equals(num))) {
            mbo.setValue("num", "CRPSYS(E)-" + year3 + "-" + "TJ-WT280", 11L);
        }

        return super.SAVE();
    }

    public int OPENREPORT() {

        try {
            MboRemote mbo = this.app.getAppBean().getMbo();
            MboSetRemote reportSet = null;
            if (mbo == null) {
                String mboname = this.app.getAppBean().getMboName();
                MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
                        MXServer.getMXServer().getSystemUserInfo());
                mbo = mboSet.getMbo(0);
            }
            reportSet = mbo.getMboSet("RQREPORT");
            if (reportSet.getMbo(0) != null && reportSet.getMbo(1) == null) {
                MboRemote reportMbo = reportSet.getMbo(0);
                String dialogID = reportMbo.getString("DIALOGID");
                String rqreportname = reportMbo.getString("RQREPORTNAME");// 报表名
                String rqreportnum = reportMbo.getString("rqreportnum");// 报表编号
                String remark = reportMbo.getString("REMARK"); // 备注
                System.out.println(remark);
                if (!"".equals(dialogID)) {
                    this.clientSession.setAttribute("rqreportname", rqreportname);
                    this.clientSession.setAttribute("rqreportnum", rqreportnum);
                    this.clientSession.setAttribute("remark", remark);
                    this.clientSession.loadDialog(dialogID);
                    return 0;
                }
                StringBuilder url =
                        new StringBuilder(MXServer.getMXServer().getProperty("mxe.runqian.url"));
                url.append("/").append(rqreportname.toLowerCase()).append(".rpx");
                MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
                        "rqreportnum = '" + rqreportnum + "'");
                if (!rqreportset.isEmpty()) {
                    for (int i = 0; i < rqreportset.count(); i++) {
                        url.append("&");
                        url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
                        url.append("=");
                        url.append(mbo.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
                    }

                }
                rqreportset.close();
                this.app.openURL(url.toString(), true);
            } else if (reportSet.count() > 1) {
                this.clientSession.loadDialog("RQREPORT1");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        }
        return 1;
    }
}