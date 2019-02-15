package com.shuto.mam.workflow.bhtt;

import com.shuto.mam.app.expand.AutoDateSiteNum;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wuqi
 * @version V1.0
 * @Title: BhttNumWorkAction.java
 * @Package com.shuto.mam.workflow.bhtt
 * @Description: (生成保护投退申请编号)
 * @date 2017-6-16 下午04:17:00
 */
public class BhttNumWorkAction implements ActionCustomClass {

    @Override
    public void applyCustomAction(MboRemote mbo, Object[] arg1) throws MXException, RemoteException {
        String bhttNum = mbo.getString("BHTTNUM");
        if ("".equals(bhttNum) || bhttNum == null) {
            String profession = mbo.getString("SQZY");
            String siteid = mbo.getString("SITEID");
            String orgid = mbo.getString("ORGID");
            MboSetRemote professionMbo = mbo.getMboSet("$PROFESSION", "PROFESSION",
                    "siteid='" + siteid + "' and professionnum='" + profession + "'"); // 专业MBO
            String professionnum = professionMbo.getMbo(0).getString("professionabbr");
            String appname = mbo.getThisMboSet().getApp();
            AutoDateSiteNum autoDateSiteNum = new AutoDateSiteNum(mbo.getThisMboSet());
            int num = autoDateSiteNum.getNextAutoDateSiteNum(orgid, siteid, appname, profession);

            bhttNum = new StringBuffer(orgid).append("-")
                    .append(siteid).append("-").append(professionnum).append("-")
                    .append(new SimpleDateFormat("yyyyMM").format(new Date()))
                    .append(new DecimalFormat("000").format(num)).toString();
            mbo.setValue("BHTTNUM", bhttNum, 11L);
        }

    }

}
