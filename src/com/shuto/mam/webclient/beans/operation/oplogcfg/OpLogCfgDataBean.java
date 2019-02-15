package com.shuto.mam.webclient.beans.operation.oplogcfg;

import psdi.mbo.MboData;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-11-04 0:00
 * @desc
 * @class com.shuto.mam.webclient.beans.operation.oplogcfg.OpLogCfgDataBean
 * @Copyright: 2017 Shuto版权所有
 **/

public class OpLogCfgDataBean extends DataBean {
    public int addAsset() throws MXException {
        try {
            int num = getMboSet().count() + 1;
            super.addrow();
            getMbo().setValue("ordernum", num);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    public int addPara() throws MXException {
        try {
            int num = getMboSet().count() + 1;
            super.addrow();
            getMbo().setValue("ordernum", num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addHx() throws MXException {
        try {
            int num = getMboSet().count() + 1;
            super.addrow();
            getMbo().setValue("ordernum", num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int addSm() throws MXException {
        try {
            int num = getMboSet().count() + 1;
            super.addrow();
            getMbo().setValue("ordernum", num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public int toggledeleterow() throws MXException {
        try {
            MboRemote mainMbo = this.app.getAppBean().getMbo();
            String appname = mainMbo.getName();
            String siteid = mainMbo.getString("siteid");
            if ("OPLOGCFG".equals(appname)) {
                WebClientEvent event = this.clientSession.getCurrentEvent();
                // 获得当前行
                int row = getRowIndexFromEvent(event);
                // 获得集合
                MboData md = (MboData) this.tableData.get(getCacheRowIndex(row));
                // 如果将该行删除
                if (!md.toBeDeleted()) {
                    // 获得表集合
                    MboSetRemote rowMsr = getMboSet();
                    String personid = rowMsr.getMbo(0).getString("personid");
                    MboSetRemote oplogpersonMsr = mainMbo.getMboSet("$oplogperson", "oplogperson",
                            "personid = '" + personid + "' and siteid = '" + siteid + "'");
                    if (oplogpersonMsr.count() <= 1) {
                        siteid = siteid.substring(0, 4) + "_YX002";
                        //获取交接班的安全组mbo
                        MboSetRemote maxgroupMsr = mainMbo.getMboSet("$MAXGROUP", "MAXGROUP",
                                "GROUPNAME = '" + siteid + "' and DESCRIPTION like '%交接班%'");
                        //判断安全组是否存在
                        if (!maxgroupMsr.isEmpty()) {
                            String groupname = maxgroupMsr.getMbo(0).getString("GROUPNAME");
                            MboSetRemote groupuserMsr = mainMbo.getMboSet("$GROUPUSER", "GROUPUSER",
                                    "GROUPNAME = '" + groupname + "' and USERID = '" + personid + "'");
                            if (!groupuserMsr.isEmpty()) {
                                groupuserMsr.getMbo(0).delete();
                            }
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return super.toggledeleterow();
    }
}
