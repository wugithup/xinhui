/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.pm;

import psdi.app.pm.PM;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: PMTaskAppBean
 * @Package com.shuto.mam.webclient.beans.pm
 * @Description: 预维护任务
 * @date 2018-12-03 0003 10:55
 */
public class PmTaskAppBean extends AppBean {

    public void CREATEWO() throws RemoteException, MXException {

        MboRemote mbo = this.getMbo();
        String pmnum = mbo.getString("PMNUM");
        String status = mbo.getString("APPSTATUS");
        WebClientEvent event = this.sessionContext.getCurrentEvent();
        //final String woStatus = "已关闭";


        //判断工单是否存在，存在不则提示
        MboSetRemote woSet = mbo.getMboSet("$WORKORDER", "WORKORDER", "PMNUM='" + pmnum + "'");
        woSet.reset();
        //如果工单已生成，不能重复生成
        if (!woSet.isEmpty()) {
            throw new MXApplicationException("designer", "generic", new Object[]{
                    "已生成工单，不能重复生成！\n已生成的工单编号为：" + woSet.getMbo(0).getString("WONUM") + ""});
        } else if (!"已关闭".equals(status) && !"已生成".equals(status)) {
            //只有在已生成或已关闭的状态下才能生成工单
            throw new MXApplicationException("designer", "prompt");

        }

        //生成工单
        ((PM) mbo).generateWork(false, 0);
        //查询生成后的工单，并把worktype类型更换为预维护工单
        MboSetRemote woSet1 = mbo.getMboSet("$WORKORDER1", "WORKORDER", "PMNUM='" + pmnum + "'");
        woSet1.getMbo(0).setValue("WORKTYPE", "预维护工单",11L);
        woSet1.getMbo(0).setValue("STATUS", "新建",11L);
        woSet1.getMbo(0).setValue("BIANHAO", mbo.getString("BGNUM"),11L);
        woSet1.getMbo(0).setValue("LEAD", mbo.getString("SUPERVISOR"),11L);
        woSet1.getMbo(0).setFieldFlag("LOCATION", MboConstants.READONLY,false);
        woSet1.getMbo(0).setValue("LOCATION", mbo.getString("LOCATION"),11L);
        woSet1.getMbo(0).setValue("PROFESSION", mbo.getString("S_PROFESSION"),11L);
        String wotracktId = woSet1.getMbo(0).getUniqueIDValue() + "";
        woSet1.save();
        woSet1.close();

        this.sessionContext.queueRefreshEvent();
        this.sessionContext.queueRefreshEvent();
        WebClientSession wcs = event.getWebClientSession();
        String additionalEvent = event.getAdditionalEvent();
        String additionalEventValue = event.getAdditionalEventValue();
        String queryString = "?event=loadapp&value=h_wotrack";
        if (!WebClientRuntime.isNull(additionalEvent)) {
            queryString = queryString + "&additionalevent=" + additionalEvent;
            if (!WebClientRuntime.isNull(additionalEventValue)) {
                queryString = queryString + "&additionaleventvalue=" + additionalEventValue;
            }
        }
        queryString = queryString + "&uniqueid=" + wotracktId;
        wcs.getCurrentApp().put("forcereload", "true");
        wcs.gotoApplink(queryString);
        event.cancelRender();
    }
    @Override
    protected void setCurrentRecordData(MboRemote mbo) throws MXException,
    		RemoteException {
    	String status = mbo.getString("APPSTATUS");
    	if("已完成".equals(status)||"已取消".equals(status)||"已生成".equals(status)){
    		mbo.setFlag(MboConstants.READONLY, true);
    	}
    	super.setCurrentRecordData(mbo);
    }
}
