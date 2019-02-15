package com.shuto.mam.crontask.stpi.shutoapp;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuto.mam.crontask.stpi.dbutils.JdbcUtils;
import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.crontask.stpi.interfacelog.Interfacelog;
import com.shuto.mam.crontask.stpi.pitask.UpdateTaskStatistics;
import com.shuto.mam.crontask.stpi.upload.PiUpload;

import psdi.mbo.MboRemote;

public class ShutoUpload extends PiUpload {
	/**
	 * 从中间库取数据
	 * 
	 * @param taskMbo
	 * @param areaSet
	 * @param itemSet
	 * @param userSet
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Override
	public int[] upload(MboRemote dbMbo) {
		int[] result = new int[2];
		UpdateTaskStatistics UpdateTaskStatistics = new UpdateTaskStatistics();
		// 连接中间数据库
		JdbcUtils jdbcUtils = new JdbcUtils();
		// 连接maximo数据库
		MaximoUtils maximoUtils = new MaximoUtils();
		Connection connection = maximoUtils.getMaximoConn();
		// 查询移动端更新的数据，xdj_line表中line_state为U和D的数据
		String sLineSql = "select * from xdj_line where line_state = 'U'";
		// 任务插入语句
		String uTaskSql = "update ST_PI_TASK set downloader=?,task_down_time=?,task_up_time=?,status='已上传结果' where ST_PI_TASKID = ?";
		String sPlaceSql = "select * from xdj_place where line_id = ?";
		String uAreaSql = "update st_pi_task_area set SCANTIME= ?, SCANENDTIME=? ,status = ? where st_pi_task_areaid = ?";
		String sItemSql = "select * from xdj_item where place_id = ?";
		String uItemSql = "update st_pi_task_item set pi_task_item_user= ?, doubleret=? ,"
				+ "check_time = ?,pi_task_item_result = ? ,isnormal = ? ,check_on_off=?,remark=?,ischeck=? where st_pi_task_itemid = ?";
		String uUserSql = "update st_pi_task_user set isdownload_user = 1 where st_pi_taskid = ? and personid = ?";
		String ulineSql = "update xdj_line set line_state='S' where LINE_ID =?";

		// 日志主表信息
		Map<String, String> logMap = new HashMap<String, String>();
		// 日志子表信息列表
		List<Map<String, String>> logList = new ArrayList<Map<String, String>>();
		int counts = 0;
		int scounts = 0;
		int ecounts = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String starttime = dateFormat.format(new Date());
		long st_pi_dbsourceid = 0;
		String orgid = "";
		String siteid = "";
		String description = "";
		try {
			st_pi_dbsourceid = dbMbo.getUniqueIDValue();
			orgid = dbMbo.getString("orgid");
			siteid = dbMbo.getString("siteid");
			description = dbMbo.getString("DESCRIPTION");
			jdbcUtils.getConnection(dbMbo);
			PreparedStatement areaPstmt = maximoUtils.LoggableStatement(uAreaSql);
			PreparedStatement itemPstmt = maximoUtils.LoggableStatement(uItemSql);

			List<Map<String, Object>> lineList = jdbcUtils.findModeResult(sLineSql, null);
			counts = lineList.size();
			if (counts > 0) {
				result[0] = lineList.size();
				result[1] = 0;
				for (Map<String, Object> lineMap : lineList) {
					int checkCount = 0; // 检查点
					int ignoreCount = 0;// 过滤点
					Object lineid = null;
					try {
						lineid = lineMap.get("LINE_ID");
						Object downloader = lineMap.get("DOWNLOADER");
						List<Object> taskParams = new ArrayList<Object>();
						taskParams.add(lineMap.get("DOWNLOADER"));
						taskParams.add(lineMap.get("TASK_DOWN_TIME"));
						taskParams.add(lineMap.get("TASK_UP_TIME"));
						taskParams.add(lineMap.get("LINE_ID"));
						// 更新任务表st_pi_task
						maximoUtils.updateByPreparedStatement(uTaskSql, taskParams);

						// 更新st_pi_task_area
						List<Object> placeParams = new ArrayList<Object>();
						placeParams.add(lineid);
						List<Map<String, Object>> placeList = jdbcUtils.findModeResult(sPlaceSql, placeParams);
						for (Map<String, Object> placeMap : placeList) {
							Object placeid = placeMap.get("PLACE_ID");
							List<Object> areaParams = new ArrayList<Object>();
							Object startTime = placeMap.get("START_TIME");
							String status = "已到位";
							if (startTime == null || "".equals(startTime)) {
								status = "未到位";
							}
							areaParams.add(startTime);
							areaParams.add(placeMap.get("END_TIME"));
							areaParams.add(status);
							areaParams.add(placeid);
							maximoUtils.updateBatchByPreparedStatement(areaPstmt, areaParams);

							// 更新st_pi_task_item
							List<Object> itemParams = new ArrayList<Object>();
							itemParams.add(placeid);
							List<Map<String, Object>> itemList = jdbcUtils.findModeResult(sItemSql, itemParams);

							for (Map<String, Object> itemMap : itemList) {
								Object itemid = itemMap.get("ITEM_ID");
								boolean isnormal = true;
								Object check_state = itemMap.get("CHECK_STATE");
								Object ischeck = itemMap.get("ISCHECK");
								if ("异常".equals(check_state)) {
									isnormal = false;
								}
								Object equipment_state = itemMap.get("EQUIPMENT_STATE");
								if (Integer.valueOf(String.valueOf(ischeck)) == 1) {
									if ("停机".equals(equipment_state) || "备用".equals(equipment_state)) {
										// 得到是否为过滤点
										boolean b = isignore(connection, itemid, equipment_state);
										if (b) {
											ignoreCount++;
										} else {
											checkCount++;
										}
									} else {
										checkCount++;
									}
								}
								List<Object> piitemParams = new ArrayList<Object>();
								piitemParams.add(itemMap.get("CHECKER_ID"));
								piitemParams.add(itemMap.get("RESULT"));
								piitemParams.add(itemMap.get("CHECK_TIME"));
								piitemParams.add(check_state);
								piitemParams.add(isnormal);
								piitemParams.add(equipment_state);
								piitemParams.add(itemMap.get("MEMO"));
								piitemParams.add(itemMap.get("ISCHECK"));
								piitemParams.add(itemid);
								maximoUtils.updateBatchByPreparedStatement(itemPstmt, piitemParams);
							}

						}

						// 更新st_pi_task_user
						List<Object> userParams = new ArrayList<Object>();
						userParams.add(lineid);
						userParams.add(downloader);
						maximoUtils.updateByPreparedStatement(uUserSql, userParams);
						// 更新中间表同步情况
						List<Object> lineParams = new ArrayList<Object>();
						lineParams.add(lineid);
						jdbcUtils.updateByPreparedStatement(ulineSql, lineParams);

						areaPstmt.executeBatch();
						itemPstmt.executeBatch();
						jdbcUtils.commit();
						maximoUtils.commit();
						// 更新任务点检率等信息
						UpdateTaskStatistics.updateTaskStatistics(lineid, checkCount, ignoreCount);
						scounts++;
					} catch (SQLException e) {
						result[1]++;
						ecounts++;
						jdbcUtils.rollback();
						maximoUtils.rollback();
						String sqlLog = jdbcUtils.getSqlLog() + maximoUtils.getSqlLog();
						// 日志子表信息
						Map<String, String> logdetailMap = new HashMap<String, String>();
						logdetailMap.put("OWNERTABLE", "ST_PI_TASK");
						logdetailMap.put("OWNERID", String.valueOf(lineid));
						logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
						logdetailMap.put("DATA", sqlLog);
						logdetailMap.put("ERRORMASSAGE", e.getMessage());
						logList.add(logdetailMap);
						e.printStackTrace();
					}
				}
				maximoUtils.closePstmt(areaPstmt);
				maximoUtils.closePstmt(itemPstmt);
			}
		} catch (Exception e) {
			// 日志子表信息
			Map<String, String> logdetailMap = new HashMap<String, String>();
			logdetailMap.put("OWNERTABLE", "ST_PI_DBSOURCE");
			logdetailMap.put("OWNERID", String.valueOf(st_pi_dbsourceid));
			logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
			logdetailMap.put("DATA", jdbcUtils.getSqlLog());
			logdetailMap.put("ERRORMASSAGE", e.getMessage());
			logList.add(logdetailMap);

			e.printStackTrace();
		}

		String endtime = dateFormat.format(new Date());
		String status = "正常";
		if (ecounts > 0) {
			status = "异常";
		}
		logMap.put("DESCRIPTION", description + " 任务数据上传");
		logMap.put("COUNTS", String.valueOf(counts));
		logMap.put("SCOUNTS", String.valueOf(scounts));
		logMap.put("ECOUNTS", String.valueOf(ecounts));
		logMap.put("STARTTIME", starttime);
		logMap.put("ENDTIME", endtime);
		logMap.put("LOGTYPE", "任务数据上传");
		logMap.put("STATUS", status);
		logMap.put("ORGID", orgid);
		logMap.put("SITEID", siteid);

		Interfacelog interfacelog = new Interfacelog();
		interfacelog.setInterfacelog(logMap, logList);

		// 关闭数据库连接
		jdbcUtils.closeAll();
		maximoUtils.closePs();
		maximoUtils.setAutoCommit(true);
		return result;
	}

	/**
	 * 判断是否过滤点
	 * 
	 * @param connection
	 * @param itemno
	 * @param equipment_state
	 * @return
	 */
	private boolean isignore(Connection connection, Object itemid, Object equipment_state) {
		boolean flag = true;
		int count = 0;
		String getSql = "select count(ST_PI_TASK_ITEMID) from ST_PI_TASK_ITEM where ST_PI_TASK_ITEMID = '" + itemid
				+ "' and ON_OFF_POTIN ='" + equipment_state + "'";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(getSql);
			if (rs.next()) {
				count = rs.getInt(1);
			}

			if (count > 0) {
				flag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
}
