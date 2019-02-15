/**
 * 
 */
package com.shuto.mam.webclient.beans.operation.speoplog;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * 航运交接班        SPEOPLOG
 com.shuto.mam.webclient.beans.operation.speoplog.SpeOplogAppBean 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:56:20
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class SpeOplogAppBean extends AppBean {
	/**
	 * 交接班
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	public int JJB() throws RemoteException, MXException {
		MboRemote mbo = app.getAppBean().getMbo();
		String dbperson = mbo.getString("dbperson");
		String jbperson = mbo.getUserInfo().getPersonId();
		String status = mbo.getString("status");
		if (!"ONDUTY".equals(status)) {
			throw new MXApplicationException("交接班失败", "该状态下不能进行交接班操作");
		}
		if (dbperson.equals(jbperson)) {
			throw new MXApplicationException("交接班失败", "接班人和当班人不能为同一人");
		}
		WebClientEvent event = clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet < 0 ) {
			throw new MXApplicationYesNoCancelException("BMXAA9304E","speoplog", "jjb");			  
		}
		if (msgRet == 8) {
			mbo.setValue("jbperson", jbperson);
			mbo.setValue("zbenddate", MXServer.getMXServer().getDate());
			mbo.setValue("jbdate", MXServer.getMXServer().getDate());
			mbo.setValue("status", "CLOSE", 11L);
			MboRemote newoplog = mbo.getThisMboSet().addAtIndex(0);
			newoplog.setValue("zbstartdate", MXServer.getMXServer().getDate());
			newoplog.setValue("dbperson", jbperson);
			newoplog.setValue("status", "ONDUTY");
			super.SAVE();
			((AppBean)this.app.getAppBean()).moveToUniqueId(newoplog.getLong("speoplogid"));
		}
		return 1;
	}
}
