package com.shuto.mam.crontask.stpi.pitask;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuto.mam.app.stpi.Toolkit;
import com.shuto.mam.crontask.stpi.StpiSimpleCronTask;
import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.crontask.stpi.interfacelog.Interfacelog;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年11月23日
 * @since:这个类加了jizu字段，不适合所有电厂，只在江苏有
 */
public class CreateXjTaskCron extends StpiSimpleCronTask {

	public void cronAction() {
		MaximoUtils maximoUtils = new MaximoUtils();
		// 获取点检任务配置表
		MboSetRemote taskcfgSet = null;
		try {
			maximoUtils.getMaximoConn();
			String orgid = this.getParamAsString("orgid");
			String siteid = this.getParamAsString("siteid");
			String site = maximoUtils.getSite(siteid);
			// 日志主表信息
			Map<String, String> logMap = new HashMap<String, String>();
			int counts = 0;
			int scounts = 0;
			int ecounts = 0;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = dateFormat.format(new Date());
			// 日志子表信息列表
			List<Map<String, String>> logList = new ArrayList<Map<String, String>>();

			MXServer server = MXServer.getMXServer();
			// 获取点检任务配置表
			taskcfgSet = server.getMboSet("ST_PI_TASKCFG", server.getSystemUserInfo());
			// 得到相应地点点检的记录
			taskcfgSet.setWhere(
					"orgid = '" + orgid + "' and siteid = '" + siteid + "' and type = '巡检' and IS_ACTIVITY=1");
			if (!taskcfgSet.isEmpty()) {
				String iTaskSql = "insert into st_pi_task (ST_PI_TASKID, ORGID, SITEID, HASLD, TASK_NAME, BEGIN_TIME, END_TIME, POST_NAME, PROFESSIONAL, TYPE, STATUS, IS_ASSESSMENT,SHIFTVALUE, ST_PI_TASKCFGID, SHIFT, ST_PI_TASKDATETIMEID,ISREPLACE,ST_PI_TASKNUM,NORM_AREA,NORM_POINT,REAL_AREA,IN_PLACE_RATE,REAL_POINT,PI_RATE,IGNORE_POINT,ABNORMAL_POINT,MISSED_POINT,MISSED_RATE,JIZU) "
						+ "select ? ,?, ?, 0, ?,st_pi_taskdatetime.begin_time,st_pi_taskdatetime.end_time,st_pi_post.description,st_pi_taskcfg.professional, '巡检', '未下载任务', 1, ? ,st_pi_taskcfg.st_pi_taskcfgid ,? ,st_pi_taskdatetime.st_pi_taskdatetimeid ,0 ,?,?,?,0,0,0,0,0,0,?,100,? from st_pi_taskcfg, st_pi_taskdatetime,st_pi_post where st_pi_taskcfg.st_pi_taskcfgid=st_pi_taskdatetime.st_pi_taskcfgid and st_pi_taskcfg.POSTNO = st_pi_post.NO and st_pi_taskdatetime.st_pi_taskdatetimeid = ?";
				String iAreaSql = "insert into st_pi_task_area (ST_PI_TASK_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, NO, PROFESSIONAL, ST_PI_TASKID, SEQ, RFID_CODE, RFID_NO, ST_PI_TASK_AREANUM, TYPE, STATUS) "
						+ "select ? ,DESCRIPTION, ORGID, SITEID, HASLD, NO, PROFESSIONAL, ? , ? ,RFID_CODE, RFID_NO, ?, '巡检', '未到位' from ST_PI_AREA where ST_PI_AREAID = ?";
				String iItemSql = "insert into ST_PI_TASK_ITEM (ST_PI_TASK_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, NO, DEVICE_NO, DEVICE_NAME, LOCATION, DEVICE_PART_NAME, POINT_TYPE, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, ON_OFF_POTIN, CHECK_METHOD, SHAKE_TYPE, SEQ, POINT_NORM, IS_EXCESSIVE, TYPE, ST_PI_TASK_ITEMNUM, ST_PI_TASKID, ISPCHECK, ST_PI_TASK_AREAID, ISNORMAL, ISCHECK, DEVICESEQ) "
						+ "select ?,DESCRIPTION, ORGID, SITEID, HASLD, NO, DEVICE_NO, DEVICE_NAME, LOCATION, DEVICE_PART_NAME, POINT_TYPE, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, ON_OFF_POTIN, CHECK_METHOD, SHAKE_TYPE, ?, POINT_NORM, 0, '巡检', ?, ?, 0, ?, 1, 0 , DEVICESEQ from ST_PI_ITEM where ST_PI_ITEMID = ?";
				String iUserSql = "insert into ST_PI_TASK_USER (ST_PI_TASK_USERID, ORGID, SITEID, HASLD, PERSONID, ISDOWNLOAD_USER, ST_PI_TASKID) "
						+ "select ?,orgid ,siteid,HASLD,PERSONID,0,? from ST_PI_TASKCFG_USER where ST_PI_TASKCFG_USERID = ?";
				String uTimeSql = "update ST_PI_TASKDATETIME set STATUS = '已生成任务' where ST_PI_TASKDATETIMEID = ?";
				PreparedStatement areaPstmt = null;
				PreparedStatement itemPstmt = null;
				PreparedStatement userPstmt = null;
				try {
					areaPstmt = maximoUtils.LoggableStatement(iAreaSql);
					itemPstmt = maximoUtils.LoggableStatement(iItemSql);
					userPstmt = maximoUtils.LoggableStatement(iUserSql);

					// 得到任务配置表区域子表
					MboSetRemote cfgareaSet = null;
					// 得到配置中项目信息
					MboSetRemote cfgitemSet = null;
					//得到任务时间表
					MboSetRemote tasktimeSet = null;
					for (int i = 0; i < taskcfgSet.count(); i++) {
						try {
							MboRemote taskcfgMbo = taskcfgSet.getMbo(i);
							long st_pi_taskcfgid = taskcfgMbo.getUniqueIDValue();
							MboRemote dutyMbo = taskcfgMbo.getMboSet("ST_PI_DUTYINFO").getMbo(0);
							// 得到任务配置表区域子表
							cfgareaSet = taskcfgMbo.getMboSet("ST_PI_TASKCFG_AREA");
							cfgareaSet.setOrderBy("ST_PI_TASKCFG_AREAID");
							// 得到配置中项目信息
							cfgitemSet = taskcfgMbo.getMboSet("ST_PI_TASKCFG_ITEM");
							tasktimeSet = taskcfgMbo.getMboSet("TASKDATETIME");
							if (!tasktimeSet.isEmpty()) {
								counts++;
								// 得到任务配置信息
								String description = taskcfgMbo.getString("description");
								String jizu = taskcfgMbo.getString("jizu");
								for (int j = 0; j < tasktimeSet.count(); j++) {
									MboRemote tasktimeMbo = tasktimeSet.getMbo(j);
									long st_pi_taskdatetimeid = tasktimeMbo.getUniqueIDValue();
									Date begin_time = tasktimeMbo.getDate("begin_time");
									// 得到班次值别
									try {
										Map<String, String> shiftInfo = getShift(begin_time, dutyMbo);
										String shift = shiftInfo.get("SHIFTTYPE");
										String shiftvalue = shiftInfo.get("SHIFT");
										String task_name = description + "_" + shiftvalue + "_" + shift;
										// 得到任务ID
										long st_pi_taskid = maximoUtils.getPubNextSeq("st_pi_taskidseq");
										// 得到AUTOKEY
										long st_pi_tasknum = maximoUtils.getPubNextSeq("ST_PI_TASK_TASKNUMSEQ");
										// 插入任务表
										List<Object> iTaskParams = new ArrayList<Object>();
										iTaskParams.add(st_pi_taskid);
										iTaskParams.add(orgid);
										iTaskParams.add(siteid);
										iTaskParams.add(task_name);
										iTaskParams.add(shiftvalue);
										iTaskParams.add(shift);
										iTaskParams.add(st_pi_tasknum);
										iTaskParams.add(cfgareaSet.count());
										iTaskParams.add(cfgitemSet.count());
										iTaskParams.add(cfgitemSet.count());
										iTaskParams.add(jizu);
										iTaskParams.add(st_pi_taskdatetimeid);
										maximoUtils.updateByPreparedStatement(iTaskSql, iTaskParams);
										if (!cfgareaSet.isEmpty()) {
											// 插入区域表
											for (int k = 0; k < cfgareaSet.count(); k++) {
												MboRemote cfgareaMbo = cfgareaSet.getMbo(k);
												long st_pi_areaid = cfgareaMbo.getLong("st_pi_areaid");
												int seq = cfgareaMbo.getInt("SEQ");
												// 得到区域ID
												long st_pi_task_areaid = maximoUtils.getPubNextSeq("st_pi_task_areaidseq");
												long st_pi_task_areanum = maximoUtils.getPubNextSeq("st_pi_task_areanumseq");
												List<Object> iAreaParams = new ArrayList<Object>();
												iAreaParams.add(st_pi_task_areaid);
												iAreaParams.add(st_pi_taskid);
												iAreaParams.add(seq);
												iAreaParams.add(st_pi_task_areanum);
												iAreaParams.add(st_pi_areaid);
												maximoUtils.updateBatchByPreparedStatement(areaPstmt, iAreaParams);

												// 得到配置中项目信息
												MboSetRemote areaitemSet = cfgareaMbo.getMboSet("ST_PI_TASKCFG_ITEM");
												areaitemSet.setOrderBy("deviceseq,seq");
												if (!areaitemSet.isEmpty()) {
													for (int m = 0; m < areaitemSet.count(); m++) {
														MboRemote cfgitemMbo = areaitemSet.getMbo(m);
														long st_pi_itemid = cfgitemMbo.getLong("st_pi_itemid");
														long st_pi_task_itemnum = maximoUtils.getPubNextSeq("ST_PI_TASK_ITEMNUMSEQ");
														int itemSeq = cfgitemMbo.getInt("SEQ");
														long St_Pi_Task_Itemid = maximoUtils.getPubNextSeq("St_Pi_Task_Itemidseq");
														List<Object> iItemParams = new ArrayList<Object>();
														iItemParams.add(St_Pi_Task_Itemid);
														iItemParams.add(itemSeq);
														iItemParams.add(st_pi_task_itemnum);
														iItemParams.add(st_pi_taskid);
														iItemParams.add(st_pi_task_areaid);
														iItemParams.add(st_pi_itemid);
														maximoUtils.updateBatchByPreparedStatement(itemPstmt, iItemParams);
													}
												}
											}
										}

										// 得到任务配置表人员子表
										MboSetRemote cfguserSet = taskcfgMbo.getMboSet("ST_PI_TASKCFG_USER");
										if (!cfguserSet.isEmpty()) {
											// 插入人员表
											for (int n = 0; n < cfguserSet.count(); n++) {
												MboRemote cfgitemMbo = cfguserSet.getMbo(n);
												long st_pi_userid = cfgitemMbo.getLong("st_pi_taskcfg_userid");
												List<Object> iUserParams = new ArrayList<Object>();
												long st_pi_task_userid = maximoUtils.getPubNextSeq("st_pi_task_useridseq");
												iUserParams.add(st_pi_task_userid);
												iUserParams.add(st_pi_taskid);
												iUserParams.add(st_pi_userid);
												maximoUtils.updateBatchByPreparedStatement(userPstmt, iUserParams);
											}
										}
										// 更新时间表状态
										List<Object> uTimeParams = new ArrayList<Object>();
										uTimeParams.add(st_pi_taskdatetimeid);
										maximoUtils.updateByPreparedStatement(uTimeSql, uTimeParams);

										areaPstmt.executeBatch();
										itemPstmt.executeBatch();
										userPstmt.executeBatch();
										maximoUtils.commit();
										scounts++;
									} catch (Exception e) {
										ecounts++;
										maximoUtils.rollback();
										// 日志子表信息
										Map<String, String> logdetailMap = new HashMap<String, String>();
										logdetailMap.put("OWNERTABLE", taskcfgMbo.getName());
										logdetailMap.put("OWNERID", String.valueOf(st_pi_taskcfgid));
										logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
										logdetailMap.put("DATA", maximoUtils.getSqlLog());
										logdetailMap.put("ERRORMASSAGE", e.getMessage());
										logList.add(logdetailMap);
										e.printStackTrace();
									} finally {
										maximoUtils.setAutoCommit(false);
									}
								}
							}
						} finally {
							if (cfgareaSet != null) {
								cfgareaSet.close();
							}
							if (cfgitemSet != null) {
								cfgitemSet.close();
							}
							if (tasktimeSet != null) {
								tasktimeSet.close();
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					maximoUtils.closePstmt(areaPstmt);
					maximoUtils.closePstmt(itemPstmt);
					maximoUtils.closePstmt(userPstmt);
					maximoUtils.closePub();
				}
			}
			taskcfgSet.close();

			String endtime = dateFormat.format(new Date());
			String status = "正常";
			if (ecounts > 0) {
				status = "异常";
			}
			logMap.put("DESCRIPTION", site + " 巡检任务生成");
			logMap.put("COUNTS", String.valueOf(counts));
			logMap.put("SCOUNTS", String.valueOf(scounts));
			logMap.put("ECOUNTS", String.valueOf(ecounts));
			logMap.put("STARTTIME", starttime);
			logMap.put("ENDTIME", endtime);
			logMap.put("LOGTYPE", "巡检任务数据生成");
			logMap.put("STATUS", status);
			logMap.put("ORGID", orgid);
			logMap.put("SITEID", siteid);

			Interfacelog interfacelog = new Interfacelog();
			interfacelog.setInterfacelog(logMap, logList);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		} finally {
			try {
				if (taskcfgSet != null) {
					taskcfgSet.close();
				}	
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取班次值
	 * @param taskStartTime
	 * @return
	 * @throws MXException 
	 * @throws RemoteException 
	 * @throws ParseException 
	 * @throws Exception
	 */

	public Map<String, String> getShift(Date taskStartTime, MboRemote dutyMbo)
			throws RemoteException, MXException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int cycledays = dutyMbo.getInt("CYCLEDAYS");
		String currentdate = sdf.format(dutyMbo.getMboSet("MINDATE").getMbo(0).getDate("STARTTIME"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		long day = Toolkit.getDays(currentdate, sdf.format(taskStartTime));//1320
		String tasktime = timeFormat.format(taskStartTime);
		long dutyNum = 0;
		if (day % cycledays == 0) {
			dutyNum = 0;
		} else {
			dutyNum = day % cycledays;
		}
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(sdf.parse(currentdate));
		startTime.add(Calendar.DATE, (int) dutyNum);
		Date targetDate = startTime.getTime();
		String sTargetDate = dateFormat.format(targetDate);
		Date targetTime = sdf.parse(sTargetDate + " " + tasktime);
		MboSetRemote dutyconfigSet = dutyMbo.getMboSet("ST_PI_DUTYCONFIG");
		dutyconfigSet.setWhere("to_char(starttime,'yyyy-mm-dd')='" + sTargetDate + "'");
		dutyconfigSet.reset();
		String shift = "";
		String shifttype = "";
		for (int i = 0; i < dutyconfigSet.count(); i++) {
			MboRemote dutyconfigMbo = dutyconfigSet.getMbo(i);
			Date beginTime = dutyconfigMbo.getDate("STARTTIME");
			Date endTime = dutyconfigMbo.getDate("ENDTIME");
			if (beginTime.compareTo(targetTime) <= 0 && endTime.compareTo(targetTime) > 0) {
				shift = dutyconfigMbo.getString("shift");
				shifttype = dutyconfigMbo.getString("shifttype");
				break;
			}
		}
		Map<String, String> shiftInfo = new HashMap<String, String>();
		shiftInfo.put("SHIFTTYPE", shifttype);
		shiftInfo.put("SHIFT", shift);
		return shiftInfo;
	}
}
