package com.shuto.mam.workflow.workorder;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-09-27 17:14
 * @desc 判断是否有物料
 * @class com.shuto.mam.workflow.workorder.Womuritem
 * @Copyright: 2017 Shuto版权所有
 **/

public class Womuritem implements ActionCustomClass {
    @Override
    public void applyCustomAction(MboRemote mbo, Object[] objects) throws MXException, RemoteException {
        String wonum = mbo.getString("wonum");
        String siteid = mbo.getString("siteid");
        MboSetRemote WonurMbo = mbo.getMboSet("$womuritem", "womuritem", "wonum='" + wonum + "' and siteid = '" + siteid + "'");
        if (WonurMbo.count() > 0) {
            throw new MXApplicationException("提示", "有物料不能退回");
        } else {
            mbo.setValue("status", "待技术部专工审核", 11L);
        }
    }
}
