package com.shuto.mam.crontask.stpi.interfacelog;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

public class Interfacelog {
	/**
	 * 数据库连接错误，生成日志
	 */
	public void setInterfacelog(MboRemote dbMbo){
		try {
			MXServer server = MXServer.getMXServer();
			MboSetRemote logSet = server.getMboSet("ST_PI_INTERFACELOG",server.getSystemUserInfo());
			MboRemote logMbo = logSet.add(11L);
			String orgid = dbMbo.getString("ORGID");
			String siteid = dbMbo.getString("SITEID");
			String dbdescription = dbMbo.getString("DESCRIPTION");
			Date date = new Date();
			logMbo.setValue("DESCRIPTION", "连接"+dbdescription,11L);
			logMbo.setValue("COUNTS", 0,11L);
			logMbo.setValue("SCOUNTS", 0,11L);
			logMbo.setValue("ECOUNTS", 0,11L);
			logMbo.setValue("STARTTIME", date,11L);
			logMbo.setValue("ENDTIME", date,11L);
			logMbo.setValue("LOGTYPE", "数据源连接",11L);
			logMbo.setValue("STATUS", "异常",11L);
			logMbo.setValue("SITEID", siteid,11L);
			logMbo.setValue("ORGID", orgid,11L);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 生成日志
	 * @param ownerid
	 * @param sqlLog
	 * @param errorLog
	 * @param logtype
	 */
	public void setInterfacelog(long ownerid, String sqlLog,String errorLog,int logtype){
		String logType = "";
		String ownertable = "";
		String st_pi_taskid = "";
		if(logtype==1){
			logType = "任务数据下载";
			ownertable="ST_PI_TASK";
			st_pi_taskid = String.valueOf(ownerid);
		}else if(logtype==2){
			logType = "任务数据上传";
			ownertable="ST_PI_TASK";
			st_pi_taskid = String.valueOf(ownerid);
		}else if(logtype==3){
			logType = "任务数据生成";
			ownertable="ST_PI_TASKCFG";
		}else if(logtype==4){
			logType = "任务数据备份";
			ownertable="ST_PI_DATABACKUP";
		}
		try {
			MXServer server = MXServer.getMXServer();
			MboSetRemote logSet = server.getMboSet("ST_PI_INTERFACELOG",server.getSystemUserInfo());
			MboRemote logMbo = logSet.add(11L);
			logMbo.setValue("DATA", sqlLog,11L);
			logMbo.setValue("OWNERTABLE", ownertable,11L);
			logMbo.setValue("OWNERID", ownerid,11L);
			logMbo.setValue("LOGTYPE", logType,11L);
			logMbo.setValue("STATUS", "异常",11L);
			logMbo.setValue("ERRORMASSAGE", errorLog,11L);
			logMbo.setValue("ST_PI_TASKID", st_pi_taskid,11L);
			logSet.save(11L);
			logSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
	}
	
	public void setInterfacelog(Map<String,String> logMap,List<Map<String,String>> logList){
		try {
			MXServer server = MXServer.getMXServer();
			MboSetRemote logSet = server.getMboSet("ST_PI_INTERFACELOG",server.getSystemUserInfo());
			MboRemote logMbo = logSet.add(11L);
			long logMboid = logMbo.getUniqueIDValue();
			String orgid = logMap.get("ORGID");
			String siteid = logMap.get("SITEID");
			logMbo.setValue("DESCRIPTION", logMap.get("DESCRIPTION"),11L);
			logMbo.setValue("COUNTS", logMap.get("COUNTS"),11L);
			logMbo.setValue("SCOUNTS", logMap.get("SCOUNTS"),11L);
			logMbo.setValue("ECOUNTS", logMap.get("ECOUNTS"),11L);
			logMbo.setValue("STARTTIME", logMap.get("STARTTIME"),11L);
			logMbo.setValue("ENDTIME", logMap.get("ENDTIME"),11L);
			logMbo.setValue("LOGTYPE", logMap.get("LOGTYPE"),11L);
			logMbo.setValue("STATUS", logMap.get("STATUS"),11L);
			logMbo.setValue("SITEID", siteid,11L);
			logMbo.setValue("ORGID", orgid,11L);
			
			if(logList.size()>0){
				MboSetRemote logdetailSet = logMbo.getMboSet("ST_PI_INTERFACELOGDETAIL");
				for (Map<String, String> lineMap : logList) {
					MboRemote logdetailMbo = logdetailSet.add(11L);
					logdetailMbo.setValue("OWNERTABLE", lineMap.get("OWNERTABLE"),11L);
					logdetailMbo.setValue("OWNERID", lineMap.get("OWNERID"),11L);
					logdetailMbo.setValue("CREATETIME", lineMap.get("CREATETIME"),11L);
					logdetailMbo.setValue("DATA", lineMap.get("DATA"),11L);
					logdetailMbo.setValue("ERRORMASSAGE", lineMap.get("ERRORMASSAGE"),11L);
					logdetailMbo.setValue("SITEID", siteid,11L);
					logdetailMbo.setValue("ORGID", orgid,11L);
					logdetailMbo.setValue("ST_PI_INTERFACELOGID", logMboid,11L);
				}
				logdetailSet.save(11L);
			}
			logSet.save(11L);
			logSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
	}
}
