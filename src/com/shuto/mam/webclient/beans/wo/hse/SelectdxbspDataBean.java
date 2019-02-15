package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class SelectdxbspDataBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException,RemoteException {
		// TODO Auto-generated method stub
		// 获得当前记录的Mbo
		MboRemote thismbo = app.getAppBean().getMbo();
		//获得当前工作票编号
		String wonum = thismbo.getString("wonum");
		//获得当前siteid
		String siteid = thismbo.getString("siteid");
		//获得v_activity_wo的mboset
		MboSetRemote mboset = MXServer.getMXServer().getMboSet("v_activity_wo", MXServer.getMXServer().getUserInfo("maxadmin"));

		mboset.setWhere("wonum = '"+wonum+"'and siteid = '"+siteid+"' and tagoutid is not null and (bspid is not null or dxid is not null)");
		return mboset;
	}
}
