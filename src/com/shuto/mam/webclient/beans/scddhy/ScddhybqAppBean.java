/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.scddhy;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScddhybqAppBean extends AppBean {

    @Override
    public int INSERT() throws MXException, RemoteException {

        super.INSERT();

        return 1;
    }

    @Override
    public int SAVE() throws MXException, RemoteException {

        MboRemote mbo = this.app.getAppBean().getMbo();
        Date time = mbo.getDate("ZKHYRQ");
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        if (time != null) {
            String timel = dateformat.format(time);
            mbo.setValue("SCDDNUM", timel, 11L);
            MboSetRemote dljg = mbo.getMboSet("DLZD");
            int j = dljg.count();
            if (j < 1) {
                String siteid = mbo.getString("SITEID");
                String num = mbo.getString("SCDDNUM");
                MboRemote dla = dljg.add();
                dla.setValue("num", num);
                MboSetRemote mtjg = mbo.getMboSet("MTQK");
                MboRemote mta = mtjg.add();
                mta.setValue("num", num);
                MboSetRemote ryjg = mbo.getMboSet("DDHYRY");
                MboSetRemote ryjgmb = mbo.getMboSet("DDHYRYMB");
                MboRemote hya = ryjg.add();
                hya.setValue("num", num);
                hya.setValue("cxry", ryjgmb.getMbo(0).getString("CXRY"));
                MboSetRemote jzjg = mbo.getMboSet("JZYX");
                MboRemote jza = jzjg.add();
                jza.setValue("num", num);
                Connection conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
                Statement statement = null;
                ResultSet rs = null;
                try {
                    statement = conn.createStatement();
                    String sql =
                            "select num from jzyx where num  in (select scddnum from (select * from SCDDHY where SCDDNUM<'" +
                                    num + "'  order by SCDDNUM desc) where rownum<2)";
                    rs = statement.executeQuery(sql);
                    if (rs.next()) {
                        String lastnum = rs.getString("num");
                        MboSetRemote lastjzyx =
                                mbo.getMboSet("#JZYX", "JZYX", "num='" + lastnum + "'");
                        String yqzm = "";
                        String YQFM = "";
                        String EQ = "";
                        String MLKGYX = "";
                        String ZJ = "";
                        String MMJ = "";
                        String KYJ = "";
                        String XHSB = "";
                        String NJSB = "";
                        String BSSB = "";
                        String KSSB = "";
                        String XHJYB = "";
                        String YHFJ = "";
                        String HX = "";
                        String MT = "";
                        String MC = "";
                        String SM = "";
                        String SANLRLM = "";
                        String ZRJCWNL = "";
                        String ZRRLWNL = "";
                        String WNKCCL = "";
                        String YLWNCSL = "";
                        String HB = "";
                        String JJXZDGZSB = "";
                        if (!lastjzyx.isEmpty()) {
                            yqzm = lastjzyx.getMbo(0).getString("YQZM");
                            YQFM = lastjzyx.getMbo(0).getString("YQFM");
                            EQ = lastjzyx.getMbo(0).getString("EQ");
                            MLKGYX = lastjzyx.getMbo(0).getString("MLKGYX");
                            ZJ = lastjzyx.getMbo(0).getString("ZJ");
                            MMJ = lastjzyx.getMbo(0).getString("MMJ");
                            KYJ = lastjzyx.getMbo(0).getString("KYJ");
                            XHSB = lastjzyx.getMbo(0).getString("XHSB");
                            NJSB = lastjzyx.getMbo(0).getString("NJSB");
                            BSSB = lastjzyx.getMbo(0).getString("BSSB");
                            KSSB = lastjzyx.getMbo(0).getString("KSSB");
                            XHJYB = lastjzyx.getMbo(0).getString("XHJYB");
                            YHFJ = lastjzyx.getMbo(0).getString("YHFJ");
                            HX = lastjzyx.getMbo(0).getString("HX");
                            MT = lastjzyx.getMbo(0).getString("MT");
                            MC = lastjzyx.getMbo(0).getString("MC");
                            SM = lastjzyx.getMbo(0).getString("SM");
                            SANLRLM = lastjzyx.getMbo(0).getString("SANLRLM");
                            ZRJCWNL = lastjzyx.getMbo(0).getString("ZRJCWNL");
                            ZRRLWNL = lastjzyx.getMbo(0).getString("ZRRLWNL");
                            WNKCCL = lastjzyx.getMbo(0).getString("WNKCCL");
                            YLWNCSL = lastjzyx.getMbo(0).getString("YLWNCSL");
                            HB = lastjzyx.getMbo(0).getString("HB");
                            JJXZDGZSB = lastjzyx.getMbo(0).getString("JJXZDGZSB");
                        }
                        jza.setValue("YQZM", yqzm);
                        jza.setValue("YQFM", YQFM);
                        jza.setValue("EQ", EQ);
                        jza.setValue("MLKGYX", MLKGYX);
                        jza.setValue("ZJ", ZJ);
                        jza.setValue("MMJ", MMJ);
                        jza.setValue("KYJ", KYJ);
                        jza.setValue("XHSB", XHSB);
                        jza.setValue("NJSB", NJSB);
                        jza.setValue("BSSB", BSSB);
                        jza.setValue("KSSB", KSSB);
                        jza.setValue("XHJYB", XHJYB);
                        jza.setValue("YHFJ", YHFJ);
                        jza.setValue("HX", HX);
                        jza.setValue("MT", MT);
                        jza.setValue("MC", MC);
                        jza.setValue("SM", SM);
                        jza.setValue("SANLRLM", SANLRLM);
                        jza.setValue("ZRJCWNL", ZRJCWNL);
                        jza.setValue("ZRRLWNL", ZRRLWNL);
                        jza.setValue("WNKCCL", WNKCCL);
                        jza.setValue("YLWNCSL", YLWNCSL);
                        jza.setValue("HB", HB);
                        jza.setValue("JJXZDGZSB", JJXZDGZSB);
                    }
                    conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (statement != null) {
                            statement.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                dljg.close();
                mtjg.close();
                ryjg.close();
                jzjg.close();
            }
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
            if ((reportSet.getMbo(0) != null) && (reportSet.getMbo(1) == null)) {
                MboRemote reportMbo = reportSet.getMbo(0);
                String dialogID = reportMbo.getString("DIALOGID");
                String rqreportname = reportMbo.getString("RQREPORTNAME");
                String rqreportnum = reportMbo.getString("rqreportnum");
                String remark = reportMbo.getString("REMARK");
                System.out.println(remark);
                if (!"".equals(dialogID)) {
                    this.clientSession.setAttribute("rqreportname", rqreportname);
                    this.clientSession.setAttribute("rqreportnum", rqreportnum);
                    this.clientSession.setAttribute("remark", remark);
                    this.clientSession.loadDialog(dialogID);
                    return 0;
                }
                StringBuffer url =
                        new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
                url.append("/" + rqreportname.toLowerCase() + ".rpx");
                MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
                        "rqreportnum = '" + rqreportnum + "'");
                if (!rqreportset.isEmpty()) {
                    for (int i = 0; i < rqreportset.count(); ++i) {
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