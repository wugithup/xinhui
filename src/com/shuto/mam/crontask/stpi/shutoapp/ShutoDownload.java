package com.shuto.mam.crontask.stpi.shutoapp;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuto.mam.crontask.stpi.dbutils.JdbcUtils;
import com.shuto.mam.crontask.stpi.download.PiDownload;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class ShutoDownload extends PiDownload {
	/**
	 * 推送任务到中间库
	 * 
	 * @param taskMbo
	 * @param areaSet
	 * @param itemSet
	 * @param userSet
	 * @return
	 */
	@Override
	public Map<String, String> download(MboRemote taskMbo, MboSetRemote areaSet, MboSetRemote itemSet,
			MboSetRemote userSet) {
		// 日志子表信息
		Map<String, String> logdetailMap = new HashMap<String, String>();
		try {
			long st_pi_taskid = taskMbo.getUniqueIDValue();
			// 连接数据库
			JdbcUtils jdbcUtils = new JdbcUtils();
			PreparedStatement areaPstmt = null;
			PreparedStatement itemPstmt = null;
			PreparedStatement userPstmt = null;
			try {
				MboRemote dbMbo = taskMbo.getMboSet("ST_PI_DBSOURCE").getMbo(0);
				jdbcUtils.getConnection(dbMbo);
				// 插入任务
				String taskSql = "insert into xdj_line (LINE_ID, LINE_NAME, START_TIME, END_TIME) values (?, ?, ?, ?)";
				List<Object> taskParams = new ArrayList<Object>();
				taskParams.add(taskMbo.getUniqueIDValue());
				taskParams.add(taskMbo.getString("TASK_NAME"));
				taskParams.add(taskMbo.getDate("BEGIN_TIME"));
				taskParams.add(taskMbo.getDate("END_TIME"));
				jdbcUtils.updateByPreparedStatement(taskSql, taskParams);

				// 插入区域
				String areaSql = "insert into xdj_place (PLACE_ID, PLACE_CODE, PLACE_NAME, LINE_ID) values (?, ?, ?, ?)";
				areaPstmt = jdbcUtils.LoggableStatement(areaSql);
				for (int i = 0; i < areaSet.count(); i++) {
					MboRemote areaMbo = areaSet.getMbo(i);
					List<Object> areaParams = new ArrayList<Object>();
					areaParams.add(areaMbo.getUniqueIDValue());
					areaParams.add(areaMbo.getString("NO"));
					areaParams.add(areaMbo.getString("DESCRIPTION"));
					areaParams.add(st_pi_taskid);
					jdbcUtils.updateBatchByPreparedStatement(areaPstmt, areaParams);
				}

				// 插入项目
				String itemSql = "insert into xdj_item (ITEM_ID, SN, EQUIPMENT, CHECK_PART, CHECK_CONTENT, STANDARD, DATA_TYPE, LOWER_LIMIT, UPPER_LIMIT, UNIT, PLACE_ID,SIGNAL_TYPE,EQUIPMENT_STATE,ISCHECK) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,0)";
				itemPstmt = jdbcUtils.LoggableStatement(itemSql);
				for (int i = 0; i < itemSet.count(); i++) {
					MboRemote itemMbo = itemSet.getMbo(i);
					List<Object> itemParams = new ArrayList<Object>();
					itemParams.add(itemMbo.getUniqueIDValue());
					itemParams.add(itemMbo.getInt("SEQ"));
					itemParams.add(itemMbo.getString("DEVICE_NAME"));
					itemParams.add(itemMbo.getString("DEVICE_PART_NAME"));
					itemParams.add(itemMbo.getString("CHECK_METHOD"));
					itemParams.add(itemMbo.getString("POINT_NORM"));
					itemParams.add(itemMbo.getString("POINT_TYPE"));
					itemParams.add(itemMbo.getString("LOWER_LIMIT"));
					itemParams.add(itemMbo.getString("HIGHER_LIMIT"));
					itemParams.add(itemMbo.getString("POINT_UNIT"));
					itemParams.add(itemMbo.getLong("ST_PI_TASK_AREAID"));
					itemParams.add(itemMbo.getString("SHAKE_TYPE"));
					itemParams.add(itemMbo.getString("ON_OFF_POTIN"));
					jdbcUtils.updateBatchByPreparedStatement(itemPstmt, itemParams);
				}
				// 插入人员
				String userSql = "insert into xdj_line_user (LINE_USER_ID, LINE_ID, USER_ID) values (?, ?, ?)";
				userPstmt = jdbcUtils.LoggableStatement(userSql);
				for (int i = 0; i < userSet.count(); i++) {
					MboRemote userMbo = userSet.getMbo(i);
					List<Object> userParams = new ArrayList<Object>();
					userParams.add(userMbo.getUniqueIDValue());
					userParams.add(st_pi_taskid);
					userParams.add(userMbo.getString("personid"));
					jdbcUtils.updateBatchByPreparedStatement(userPstmt, userParams);
				}
				areaPstmt.executeBatch();
				itemPstmt.executeBatch();
				userPstmt.executeBatch();
				jdbcUtils.commit();
				logdetailMap = null;
			} catch (SQLException e) {
				jdbcUtils.rollback();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logdetailMap.put("OWNERTABLE", "ST_PI_TASK");
				logdetailMap.put("OWNERID", String.valueOf(st_pi_taskid));
				logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
				logdetailMap.put("DATA", jdbcUtils.getSqlLog());
				logdetailMap.put("ERRORMASSAGE", e.getMessage());
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MXException e) {
				e.printStackTrace();
			} finally {
				jdbcUtils.closePstmt(areaPstmt);
				jdbcUtils.closePstmt(itemPstmt);
				jdbcUtils.closePstmt(userPstmt);
				// 关闭数据库连接
				jdbcUtils.closeAll();
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (MXException e1) {
			e1.printStackTrace();
		}
		return logdetailMap;
	}
}
