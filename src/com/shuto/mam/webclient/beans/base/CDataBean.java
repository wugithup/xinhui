package com.shuto.mam.webclient.beans.base;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2018-02-01 17:11
 * @desc
 * @class com.shuto.mam.webclient.beans.base.CDateBean
 * @Copyright: 2018 Shuto版权所有
 **/

public class CDataBean extends DataBean {
    @Override
    protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
        // long startTime = System.currentTimeMillis(); // 获取开始时间
        String status = mbo.getOwner().getString("status");
        String relationship = mbo.getThisMboSet().getRelationName();
        String appname = mbo.getOwner().getThisMboSet().getApp();
        String[] readonlyStr = WfUtil(appname, status, "READONLY", relationship);
        String[] requiredStr = WfUtil(appname, status, "REQUIRED", relationship);
        String[] noreadonlyStr = WfUtil(appname, status, "NOREADONLY", relationship);
        if ((noreadonlyStr != null) && (noreadonlyStr.length > 0)) {
            mbo.setFieldFlag(noreadonlyStr, MboConstants.READONLY, false);
        }
        if ((readonlyStr != null) && (readonlyStr.length > 0)) {
            mbo.setFieldFlag(readonlyStr, MboConstants.READONLY, true);
        }
        if ((requiredStr != null) && (requiredStr.length > 0)) {
            mbo.setFieldFlag(requiredStr, MboConstants.REQUIRED, true);
        }
        if (("已关闭".equals(status)) || ("已作废".equals(status)) || ("已取消".equals(status))) {
            mbo.setFlag(MboConstants.READONLY, true);
        }
        super.setCurrentRecordData(mbo);
    }


    private String[] WfUtil(String appname, String status, String str, String relationship) throws RemoteException, MXException {
        MboSetRemote accessControlSet = getMbo().getMboSet("$ACCESSCONTROL", "ACCESSCONTROL",
                "PROCESSNAME = '" + appname + "'AND status = '" + status + "' and ISBTORZD = '" + str + "'  and relationship ='" + relationship + "'");
        String[] rdStr = new String[0];
        if (!accessControlSet.isEmpty()) {
            rdStr = accessControlSet.getMbo(0).getString("description").split(",");
        }
        accessControlSet.close();
        return rdStr;
    }
}
