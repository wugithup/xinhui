package com.shuto.mam.workflow.sr;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.workflow.WFInstanceRemote;
import psdi.workflow.WFInstanceSetRemote;

import java.rmi.RemoteException;

/**
 * com.shuto.mam.workflow.sr.SrCloseWo 缺陷关闭后自动关闭工单
 *
 * @author quanhw
 * @date 2018/3/28
 */

public class SrCloseWo implements ActionCustomClass {

    @Override
    public void applyCustomAction(MboRemote srMbo, Object[] arg1) throws MXException, RemoteException {
        // 停止工单工作流
        String ticketid = srMbo.getString("ticketid");
        //获取工单mbo
        MboSetRemote womsr = srMbo.getMboSet("maxwo");
        //获取工单唯一ID
        int workorderid = womsr.getMbo(0).getInt("workorderid");
        WFInstanceSetRemote mSet = (WFInstanceSetRemote) srMbo.getMboSet("$WFINSTANCE", "WFINSTANCE", "processname='WOTRACKZLC' and ownerid="
                + workorderid + "");
        if (mSet.count() > 0) {
            WFInstanceRemote instance = (WFInstanceRemote) mSet.getMbo(0);
            instance.stopWorkflow("STOP");
            //设置工单状态为已关闭
            womsr.getMbo(0).setValue("status", "已关闭", MboConstants.NOACCESSCHECK);
            //设置缺陷状态为已关闭
            srMbo.setValue("status", "已关闭", MboConstants.NOACCESSCHECK);
            womsr.save();
            srMbo.getThisMboSet().save();
        }
    }

}
