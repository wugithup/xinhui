package com.shuto.mam.crontask.stpi.pi_databf;

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

import com.shuto.mam.app.stpi.st_pi_databackup.St_pi_databackup;
import com.shuto.mam.crontask.stpi.StpiSimpleCronTask;
import com.shuto.mam.crontask.stpi.dbutils.JdbcUtils;
import com.shuto.mam.crontask.stpi.interfacelog.Interfacelog;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

public class DataBackupsCron extends StpiSimpleCronTask {

	@Override
	public void cronAction() {
		JdbcUtils jdbcUtils = new JdbcUtils();
		try {
			String taskSql = "insert into ST_PI_TASK" + " (ST_PI_TASKID," + " DESCRIPTION," + " ORGID," + " SITEID,"
					+ " HASLD," + " NO," + " TASK_NAME," + " BEGIN_TIME," + " END_TIME," + " POST_NAME,"
					+ " PROFESSIONAL," + " IN_PLACE_RATE," + " NORM_AREA," + " REAL_AREA," + " NORM_POINT,"
					+ " REAL_POINT," + " PI_RATE," + " MISSED_POINT," + " MISSED_RATE," + " TYPE," + " STATUS,"
					+ " IS_ASSESSMENT," + " REASON," + " ST_PI_TASKNUM," + " TASK_UP_TIME," + " IGNORE_POINT,"
					+ " SHIFTVALUE," + " ABNORMAL_POINT," + " ST_PI_TASKCFGID," + " SHIFT," + " ISREPLACE,"
					+ " REPLACEPER," + " DOWNLOADER," + " MISSREASON," + " ST_PI_TASKDATETIMEID," + " TASK_DOWN_TIME)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String areaSql = "insert into St_Pi_Task_Area (ST_PI_TASK_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, NO, PROFESSIONAL, ST_PI_TASKID, SEQ, RFID_CODE, RFID_NO, ST_PI_TASK_AREANUM, TYPE, SCANTIME, SCANENDTIME, STATUS)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String itemSql = "insert into st_pi_task_item (ST_PI_TASK_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, NO, DEVICE_NO, DEVICE_NAME, LOCATION, DEVICE_PART_NAME, POINT_TYPE, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, ON_OFF_POTIN, CHECK_METHOD, SHAKE_TYPE, SEQ, POINT_NORM, PI_TASK_ITEM_RESULT, IS_EXCESSIVE, PI_ITEM_DESCRIPTION, TYPE, ST_PI_TASK_ITEMNUM, ST_PI_TASKID, DOUBLERET, CHECK_ON_OFF, REMARK, ISPCHECK, ST_PI_TASK_AREAID, PI_TASK_ITEM_USER, ISNORMAL, NOTE, PI_AMEND_USER, PI_TASK_ITEM_LJREASON, CHECK_TIME, ISCHECK, TICKETID, WONUM)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String userSql = "insert into ST_PI_TASK_USER (ST_PI_TASK_USERID, DESCRIPTION, ORGID, SITEID, HASLD, PERSONID, ISDOWNLOAD_USER, ST_PI_TASKID)"
					+ " values (?,?,?,?,?,?,?,?)";

			PreparedStatement areaPstmt = jdbcUtils.LoggableStatement(areaSql);
			PreparedStatement itemPstmt = jdbcUtils.LoggableStatement(itemSql);
			PreparedStatement userPstmt = jdbcUtils.LoggableStatement(userSql);

			MXServer server = MXServer.getMXServer();
			// 获取数据备份配置
			MboSetRemote databfSet = server.getMboSet("ST_PI_DATABACKUP", server.getSystemUserInfo());
			// 得到相应数据备份配置的记录
			databfSet.setWhere("IS_ACTIVITY=1");
			if (!databfSet.isEmpty()) {
				Date backuptime = new Date();
				for (int i = 0; i < databfSet.count(); i++) {

					// 日志主表信息
					Map<String, String> logMap = new HashMap<String, String>();
					// 日志子表信息列表
					List<Map<String, String>> logList = new ArrayList<Map<String, String>>();
					int counts = 0;
					int scounts = 0;
					int ecounts = 0;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String starttime = dateFormat.format(new Date());

					St_pi_databackup databfMbo = (St_pi_databackup) databfSet.getMbo(i);
					int keepterm = databfMbo.getInt("keepterm");
					String orgid = databfMbo.getString("orgid");
					String siteid = databfMbo.getString("siteid");
					String nodeDate = getNodeDate(keepterm);
					// 备份点检任务表
					MboSetRemote taskSet = server.getMboSet("ST_PI_TASK", server.getSystemUserInfo());
					taskSet.setWhere("task_up_time <= to_date('" + nodeDate + "','yyyy-mm-dd hh:mi:ss')");
					counts = taskSet.count();
					if (counts > 0) {
						try {
							// 连接数据库
							jdbcUtils.getConnection(databfMbo.getMboSet("DBSOURCENO").getMbo(0));
							for (int j = 0; j < counts; j++) {
								long st_pi_taskid = 0;
								try {
									MboRemote taskMbo = taskSet.getMbo(j);
									List<Object> taskParams = new ArrayList<Object>();
									st_pi_taskid = taskMbo.getLong("ST_PI_TASKID");
									taskParams.add(st_pi_taskid);
									taskParams.add(taskMbo.getString("DESCRIPTION"));
									taskParams.add(taskMbo.getString("ORGID"));
									taskParams.add(taskMbo.getString("SITEID"));
									taskParams.add(taskMbo.getInt("HASLD"));
									taskParams.add(taskMbo.getString("NO"));
									taskParams.add(taskMbo.getString("TASK_NAME"));
									taskParams.add(taskMbo.getDate("BEGIN_TIME"));
									taskParams.add(taskMbo.getDate("END_TIME"));
									taskParams.add(taskMbo.getString("POST_NAME"));
									taskParams.add(taskMbo.getString("PROFESSIONAL"));
									taskParams.add(taskMbo.getString("IN_PLACE_RATE"));
									taskParams.add(taskMbo.getString("NORM_AREA"));
									taskParams.add(taskMbo.getString("REAL_AREA"));
									taskParams.add(taskMbo.getString("NORM_POINT"));
									taskParams.add(taskMbo.getString("REAL_POINT"));
									taskParams.add(taskMbo.getString("PI_RATE"));
									taskParams.add(taskMbo.getString("MISSED_POINT"));
									taskParams.add(taskMbo.getString("MISSED_RATE"));
									taskParams.add(taskMbo.getString("TYPE"));
									taskParams.add(taskMbo.getString("STATUS"));
									taskParams.add(taskMbo.getInt("IS_ASSESSMENT"));
									taskParams.add(taskMbo.getString("REASON"));
									taskParams.add(taskMbo.getString("ST_PI_TASKNUM"));
									taskParams.add(taskMbo.getDate("TASK_UP_TIME"));
									taskParams.add(taskMbo.getString("IGNORE_POINT"));
									taskParams.add(taskMbo.getString("SHIFTVALUE"));
									taskParams.add(taskMbo.getString("ABNORMAL_POINT"));
									taskParams.add(taskMbo.getLong("ST_PI_TASKCFGID"));
									taskParams.add(taskMbo.getString("SHIFT"));
									taskParams.add(taskMbo.getInt("ISREPLACE"));
									taskParams.add(taskMbo.getString("REPLACEPER"));
									taskParams.add(taskMbo.getString("DOWNLOADER"));
									taskParams.add(taskMbo.getString("MISSREASON"));
									taskParams.add(taskMbo.getLong("ST_PI_TASKDATETIMEID"));
									taskParams.add(taskMbo.getDate("TASK_DOWN_TIME"));
									jdbcUtils.updateByPreparedStatement(taskSql, taskParams);
									// 备份点检任务区域表
									MboSetRemote areaSet = taskMbo.getMboSet("ST_PI_TASK_AREA");
									for (int k = 0; k < areaSet.count(); k++) {
										MboRemote areaMbo = areaSet.getMbo(k);
										List<Object> areaParams = new ArrayList<Object>();
										areaParams.add(areaMbo.getLong("ST_PI_TASK_AREAID"));
										areaParams.add(areaMbo.getString("DESCRIPTION"));
										areaParams.add(areaMbo.getString("ORGID"));
										areaParams.add(areaMbo.getString("SITEID"));
										areaParams.add(areaMbo.getInt("HASLD"));
										areaParams.add(areaMbo.getString("NO"));
										areaParams.add(areaMbo.getString("PROFESSIONAL"));
										areaParams.add(areaMbo.getLong("ST_PI_TASKID"));
										areaParams.add(areaMbo.getString("SEQ"));
										areaParams.add(areaMbo.getString("RFID_CODE"));
										areaParams.add(areaMbo.getString("RFID_NO"));
										areaParams.add(areaMbo.getString("ST_PI_TASK_AREANUM"));
										areaParams.add(areaMbo.getString("TYPE"));
										areaParams.add(areaMbo.getDate("SCANTIME"));
										areaParams.add(areaMbo.getDate("SCANENDTIME"));
										areaParams.add(areaMbo.getString("STATUS"));
										jdbcUtils.updateBatchByPreparedStatement(areaPstmt, areaParams);
									}
									// 备份点检任务项目表
									MboSetRemote itemSet = taskMbo.getMboSet("ST_PI_TASK_ITEM");
									for (int k = 0; k < itemSet.count(); k++) {
										MboRemote itemMbo = itemSet.getMbo(k);
										List<Object> itemParams = new ArrayList<Object>();
										itemParams.add(itemMbo.getLong("ST_PI_TASK_ITEMID"));
										itemParams.add(itemMbo.getString("DESCRIPTION"));
										itemParams.add(itemMbo.getString("ORGID"));
										itemParams.add(itemMbo.getString("SITEID"));
										itemParams.add(itemMbo.getInt("HASLD"));
										itemParams.add(itemMbo.getString("NO"));
										itemParams.add(itemMbo.getString("DEVICE_NO"));
										itemParams.add(itemMbo.getString("DEVICE_NAME"));
										itemParams.add(itemMbo.getString("LOCATION"));
										itemParams.add(itemMbo.getString("DEVICE_PART_NAME"));
										itemParams.add(itemMbo.getString("POINT_TYPE"));
										itemParams.add(itemMbo.getString("POINT_UNIT"));
										itemParams.add(itemMbo.getString("HIGHER_LIMIT"));
										itemParams.add(itemMbo.getString("LOWER_LIMIT"));
										itemParams.add(itemMbo.getString("ON_OFF_POTIN"));
										itemParams.add(itemMbo.getString("CHECK_METHOD"));
										itemParams.add(itemMbo.getString("SHAKE_TYPE"));
										itemParams.add(itemMbo.getString("SEQ"));
										itemParams.add(itemMbo.getString("POINT_NORM"));
										itemParams.add(itemMbo.getString("PI_TASK_ITEM_RESULT"));
										itemParams.add(itemMbo.getInt("IS_EXCESSIVE"));
										itemParams.add(itemMbo.getString("PI_ITEM_DESCRIPTION"));
										itemParams.add(itemMbo.getString("TYPE"));
										itemParams.add(itemMbo.getString("ST_PI_TASK_ITEMNUM"));
										itemParams.add(itemMbo.getLong("ST_PI_TASKID"));
										itemParams.add(itemMbo.getString("DOUBLERET"));
										itemParams.add(itemMbo.getString("CHECK_ON_OFF"));
										itemParams.add(itemMbo.getString("REMARK"));
										itemParams.add(itemMbo.getInt("ISPCHECK"));
										itemParams.add(itemMbo.getLong("ST_PI_TASK_AREAID"));
										itemParams.add(itemMbo.getString("PI_TASK_ITEM_USER"));
										itemParams.add(itemMbo.getInt("ISNORMAL"));
										itemParams.add(itemMbo.getString("NOTE"));
										itemParams.add(itemMbo.getString("PI_AMEND_USER"));
										itemParams.add(itemMbo.getString("PI_TASK_ITEM_LJREASON"));
										itemParams.add(itemMbo.getDate("CHECK_TIME"));
										itemParams.add(itemMbo.getInt("ISCHECK"));
										itemParams.add(itemMbo.getString("TICKETID"));
										itemParams.add(itemMbo.getString("WONUM"));
										jdbcUtils.updateBatchByPreparedStatement(itemPstmt, itemParams);
									}
									// 备份点检任务人员表
									MboSetRemote userSet = taskMbo.getMboSet("ST_PI_TASK_USER");
									for (int k = 0; k < userSet.count(); k++) {
										MboRemote userMbo = userSet.getMbo(k);
										List<Object> userParams = new ArrayList<Object>();
										userParams.add(userMbo.getLong("ST_PI_TASK_USERID"));
										userParams.add(userMbo.getString("DESCRIPTION"));
										userParams.add(userMbo.getString("ORGID"));
										userParams.add(userMbo.getString("SITEID"));
										userParams.add(userMbo.getInt("HASLD"));
										userParams.add(userMbo.getString("PERSONID"));
										userParams.add(userMbo.getInt("ISDOWNLOAD_USER"));
										userParams.add(userMbo.getLong("ST_PI_TASKID"));
										jdbcUtils.updateBatchByPreparedStatement(userPstmt, userParams);
									}
									areaPstmt.executeBatch();
									itemPstmt.executeBatch();
									userPstmt.executeBatch();
									jdbcUtils.commit();
									taskMbo.delete(11L);
									++scounts;
								} catch (SQLException e) {
									ecounts++;
									jdbcUtils.rollback();
									// 日志子表信息
									Map<String, String> logdetailMap = new HashMap<String, String>();
									logdetailMap.put("OWNERTABLE", "ST_PI_TASK");
									logdetailMap.put("OWNERID", String.valueOf(st_pi_taskid));
									logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
									logdetailMap.put("DATA", jdbcUtils.getSqlLog());
									logdetailMap.put("ERRORMASSAGE", e.getMessage());
									logList.add(logdetailMap);
									e.printStackTrace();
								}
							}
							taskSet.save();
						} catch (Exception e) {
							ecounts = counts;
							// 日志子表信息
							Map<String, String> logdetailMap = new HashMap<String, String>();
							logdetailMap.put("OWNERTABLE", "ST_PI_DATABACKUP");
							logdetailMap.put("OWNERID", String.valueOf(databfMbo.getUniqueIDValue()));
							logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
							logdetailMap.put("DATA", jdbcUtils.getSqlLog());
							logdetailMap.put("ERRORMASSAGE", e.getMessage());
							logList.add(logdetailMap);
							e.printStackTrace();
						}
					}
					// 插入备份截止时间
					databfMbo.setValue("backuptime", backuptime, 11L);
					databfMbo.setLog(scounts, backuptime);

					String endtime = dateFormat.format(new Date());
					String status = "正常";
					if (ecounts > 0) {
						status = "异常";
					}
					logMap.put("DESCRIPTION", databfMbo.getString("DESCRIPTION"));
					logMap.put("COUNTS", String.valueOf(counts));
					logMap.put("SCOUNTS", String.valueOf(scounts));
					logMap.put("ECOUNTS", String.valueOf(ecounts));
					logMap.put("STARTTIME", starttime);
					logMap.put("ENDTIME", endtime);
					logMap.put("LOGTYPE", "任务数据备份");
					logMap.put("STATUS", status);
					logMap.put("ORGID", orgid);
					logMap.put("SITEID", siteid);

					Interfacelog interfacelog = new Interfacelog();
					interfacelog.setInterfacelog(logMap, logList);

					// 关闭数据库连接
					jdbcUtils.closeAll();
				}
				databfSet.save();
			}
			jdbcUtils.closePstmt(areaPstmt);
			jdbcUtils.closePstmt(itemPstmt);
			jdbcUtils.closePstmt(userPstmt);
			databfSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到备份截止日期
	 * 
	 * @param keepterm
	 * @return
	 * @throws ParseException
	 */
	private String getNodeDate(int keepterm) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(new Date());
		calendar.add(calendar.DATE, -keepterm);
		String nodeDate = df.format(calendar.getTime());
		return nodeDate;
	}

}
