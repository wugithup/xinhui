package com.shuto.mam.crontask.stpi.download;

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

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年5月24日
 * @since:优博讯中间库任务推送
 */
public class YouBoXunXJDownload extends PiDownload {

	public YouBoXunXJDownload() {
	}

	@Override
	public Map<String, String> download(MboRemote taskMbo, MboSetRemote areaSet, MboSetRemote itemSet,
			MboSetRemote userSet) {
		{
			Map<String, String> logdetailMap = new HashMap<String, String>();
			try {
				long st_pi_taskid = taskMbo.getUniqueIDValue();
				// 连接数据库
				JdbcUtils jdbcUtils = new JdbcUtils();
				PreparedStatement areaPstmt = null;
				PreparedStatement itemPstmt = null;
				PreparedStatement userPstmt = null;
				PreparedStatement devicePstmt = null;
				try {
					MboRemote dbMbo = taskMbo.getMboSet("ST_PI_DBSOURCE").getMbo(0);
					jdbcUtils.getConnection(dbMbo);
					// 插入任务
					String taskSql = "insert into m_pi_task(PI_TASKID,NO,ISREPLACE,PI_TASKCFG_NO,NUM,"
							+ "SHIFT,DAYSHIFT,TASK_NAME,HASLD,REAL_AREA,IN_PLACE_RATE,REAL_POINT,"
							+ "PI_RATE,MISSED_POINT,MISSED_RATE,IGNORE_POINT,YCD,IS_ASSESSMENT, "
							+ "STATUS,BEGIN_TIME,END_TIME,PROFESSIONAL,PI_POST_NO,TASK_TYPE,NORM_AREA ,NORM_POINT) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					List<Object> taskParams = new ArrayList<Object>();
					taskParams.add(taskMbo.getInt("ST_PI_TASKID"));
					taskParams.add(null);
					taskParams.add("0");// ISREPLACE
					taskParams.add(taskMbo.getInt("ST_PI_TASK.st_pi_taskcfgnum"));// PI_TASKCFG_NO-----------对应st_pi_taskcfgnum，需要建立个关系，子对象taskcfg表
					taskParams.add(taskMbo.getInt("st_pi_tasknum"));// NUM不知道对应st_pi_task中哪个属性
					taskParams.add(taskMbo.getString("shiftvalue"));// 值别
					taskParams.add(taskMbo.getString("shift"));// 班次

					taskParams.add(taskMbo.getString("TASK_NAME"));
					taskParams.add(0);
					taskParams.add(taskMbo.getString("REAL_AREA"));// 实到区域
					taskParams.add(taskMbo.getDouble("IN_PLACE_RATE"));// 到位率
					taskParams.add(taskMbo.getInt("REAL_POINT"));// 实检点
					taskParams.add(taskMbo.getDouble("PI_RATE"));// 点检率
					taskParams.add(taskMbo.getInt("MISSED_POINT"));// 漏检点

					taskParams.add(taskMbo.getDouble("MISSED_RATE"));// 漏检率
					taskParams.add(taskMbo.getString("IGNORE_POINT"));// 过滤点
					taskParams.add(taskMbo.getInt("ABNORMAL_POINT"));// 异常点
					taskParams.add(1);// IS_ASSESSMENT是否考核
					taskParams.add(taskMbo.getString("STATUS"));/*//从st_pi_task表中获取字段，如果是没有推送到中间库，则status=未下载任务，推送完后status=未上传结果，
					 而中间库的表m_pi_task 的status是顺延maximo端推送到中签库之前的状态，也就是status=未下载任务，人员用手持机就无法下载任务。。。。。*/					
					// 时间转化
					taskParams.add(taskMbo.getDate("begin_time"));
					taskParams.add(taskMbo.getDate("end_time"));

					taskParams.add(taskMbo.getString("PROFESSIONAL"));
					taskParams.add(taskMbo.getString("st_pi_task.no"));// pi_post_no
					taskParams.add(taskMbo.getString("type"));
					taskParams.add(taskMbo.getString("NORM_AREA"));// 应到区域
					taskParams.add(taskMbo.getString("NORM_POINT"));// 应检点
					jdbcUtils.updateByPreparedStatement(taskSql, taskParams);

					// 插入区域
					String areaSql = "insert into M_PI_TASK_AREA(PI_TASK_AREAID,HASLD,STATUS,NUM,TYPE,TASK_AREA_NAME,"
							+ "IS_BATCH,PROFESSIONAL,PI_RFID_NO,PI_TASK_NO,SEQ,RFID_CODE,RFID_NO ) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
					areaPstmt = jdbcUtils.LoggableStatement(areaSql);
					for (int i = 0; i < areaSet.count(); i++) {
						MboRemote areaMbo = areaSet.getMbo(i);
						List<Object> areaParams = new ArrayList<Object>();
						areaParams.add(areaMbo.getUniqueIDValue());
						areaParams.add(0);
						areaParams.add(areaMbo.getString("STATUS"));
						areaParams.add(areaMbo.getString("no"));
						areaParams.add(areaMbo.getString("TYPE"));
						areaParams.add(areaMbo.getString("description"));// TASK_AREA_NAME
						areaParams.add(0);// is_batch
						areaParams.add(areaMbo.getString("PROFESSIONAL"));
						areaParams.add("null");// PI_RFID_NO
						areaParams.add(areaMbo.getInt("ST_PI_TASK_AREA.st_pi_tasknum"));// PI_TASK_NO
						areaParams.add(areaMbo.getInt("seq"));
						areaParams.add(areaMbo.getString("RFID_CODE"));
						areaParams.add(areaMbo.getInt("RFID_NO"));
						jdbcUtils.updateBatchByPreparedStatement(areaPstmt, areaParams);
					}

					// 插入设备表
					String deviceSql = "insert into M_PI_TASK_DEVICE(PI_TASK_DEVICEID,NUM,PI_TASK_NO,TYPE,TASK_DEVICE_NAME,"
							+ "LOCATION,IS_BATCH,HASLD,PI_AREA_NO,SEQ,PI_TASK_AREA_NO) values(?,?,?,?,?,?,?,?,?,?,?)";
					devicePstmt = jdbcUtils.LoggableStatement(deviceSql);
					
					Map<String, Long> deveiceMap = new HashMap<String, Long>();// 创建设备集合
					MboRemote itemMbo = null;
					List<Object> midParams = null;
					String deviceName = null;
					Long pi_task_deviceid = 0L;
					for (int i = 0; i < itemSet.count(); i++) {
						itemMbo = itemSet.getMbo(i);
						deviceName = itemMbo.getString("DEVICE_NAME");
						//根据设备名称判重
						if (!deveiceMap.containsKey(deviceName)) {
							pi_task_deviceid = jdbcUtils.getNextSequence("m_deviceSS");
							midParams = new ArrayList<Object>();
							midParams.add(pi_task_deviceid);// PI_TASK_DEVICEID
							midParams.add(taskMbo.getInt("ST_PI_TASKNUM"));// PI_TASK_NO
							midParams.add(itemMbo.getInt("DEVICE_NO"));// NUM
							midParams.add(taskMbo.getString("TYPE"));// type
							midParams.add(deviceName);// TASK_DEVICE_NAME
							midParams.add(itemMbo.getString("LOCATION"));// location对应kks的编码,通过关联关系得到location
							midParams.add(0);
							midParams.add(0);
							midParams.add(itemMbo.getString("ST_PI_TASK_AREA.no"));// PI_TASK_AREA_NO先关联到项目表
							midParams.add(itemMbo.getInt("DEVICESEQ"));
							midParams.add(itemMbo.getString("ST_PI_TASK_AREA.no"));// PI_TASK_AREA_NO先关联到项目表
							deveiceMap.put(deviceName, pi_task_deviceid);
							
							jdbcUtils.updateBatchByPreparedStatement(devicePstmt, midParams);
						}
					}
					
					// 插入项目
					String itemSql = "insert into M_PI_TASK_ITEM(PI_TASK_ITEMID,ispcheck,pi_task_area_no,pi_device_name,"
							+ "PI_ITEM_DESCRIPTION,HASLD,IS_EXCESSIVE,NUM,PI_TASK_NO,TYPE,DEVICE_PART_NAME,POINT_TYPE,"
							+ "POINT_UNIT,HIGHER_LIMIT,LOWER_LIMIT,ON_OFF_POTIN,CHECK_METHOD,SHAKE_TYPE,PI_AREA_DEVICE_NO,"
							+ "SEQ,PI_TASK_DEVICE_NO,POINT_NORM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					itemPstmt = jdbcUtils.LoggableStatement(itemSql);
					
					List<Object> itemParams = null;
					for (int i = 0; i < itemSet.count(); i++) {// 遍历项目
						itemMbo = itemSet.getMbo(i);
						itemParams = new ArrayList<Object>();
						deviceName = itemMbo.getString("DEVICE_NAME");

						itemParams.add(itemMbo.getUniqueIDValue());//PI_TASK_ITEMID
						itemParams.add(itemMbo.getInt("ISPCHECK"));//ispcheck
						itemParams.add(itemMbo.getString("ST_PI_TASK_AREA.no"));// pi_task_area_no
						itemParams.add(deviceName);// pi_device_name
						itemParams.add(itemMbo.getString("DESCRIPTION"));
						itemParams.add(0);
						itemParams.add(itemMbo.getInt("IS_EXCESSIVE"));// IS_EXCESSIVE是否超标

						itemParams.add(itemMbo.getString("ST_PI_TASK_ITEMNUM"));
						itemParams.add(taskMbo.getString("ST_PI_TASKNUM"));// PI_TASK_NO
						itemParams.add(itemMbo.getString("TYPE"));
						itemParams.add(itemMbo.getString("DEVICE_PART_NAME"));// 部位部件名称
						itemParams.add(itemMbo.getString("POINT_TYPE"));// 测点类型
						itemParams.add(itemMbo.getString("POINT_UNIT"));// 测点单位
						itemParams.add(itemMbo.getString("HIGHER_LIMIT"));// HIGHER_LIMIT

						itemParams.add(itemMbo.getString("LOWER_LIMIT"));
						itemParams.add(itemMbo.getString("ON_OFF_POTIN"));// ON_OFF_POTIN
						itemParams.add(itemMbo.getString("CHECK_METHOD"));
						itemParams.add(itemMbo.getString("SHAKE_TYPE"));
						itemParams.add(itemMbo.getString("DEVICE_NO"));// PI_AREA_DEVICE_NO
						itemParams.add(itemMbo.getInt("SEQ"));
						itemParams.add(itemMbo.getString("DEVICE_NO"));// PI_TASK_DEVICE_NO
						itemParams.add(itemMbo.getString("POINT_NORM"));// 点检标准
						
						jdbcUtils.updateBatchByPreparedStatement(itemPstmt, itemParams);
					}

					// 插入人员
					String userSql = "insert into M_PI_TASK_USER(PI_TASK_USERID,HASLD,PERSONID,DISPLAYNAME,PI_POST_NO,DOWNLOAD_USER,PI_TASK_NO ) values(?,?,?,?,?,?,?)";
					userPstmt = jdbcUtils.LoggableStatement(userSql);
					String personid = null;
					for (int i = 0; i < userSet.count(); i++) {
						MboRemote userMbo = userSet.getMbo(i);
						personid = userMbo.getString("personid");
						//如果存在老personid则往中间库插入老personid
						if (userMbo.getString("person.oldpersonid") != null && !"".equals(userMbo.getString("person.oldpersonid"))) {
							personid = userMbo.getString("person.oldpersonid");
						}
						List<Object> userParams = new ArrayList<Object>();
						userParams.add(userMbo.getUniqueIDValue());
						userParams.add(0);
						userParams.add(personid);
						userParams.add(userMbo.getString("person.DISPLAYNAME"));
						userParams.add(taskMbo.getInt("ST_PI_TASK_AREA.ST_PI_TASK_AREANUM"));// pi_post_no----------------------------------
						userParams.add(userMbo.getInt("ISDOWNLOAD_USER"));
						userParams.add(userMbo.getInt("ST_PI_TASK_USER.st_pi_tasknum"));// PI_TASK_NO------------------------
						// st_pi_tasknum
						jdbcUtils.updateBatchByPreparedStatement(userPstmt, userParams);
					}
					
					areaPstmt.executeBatch();
					devicePstmt.executeBatch();
					itemPstmt.executeBatch();
					userPstmt.executeBatch();
					jdbcUtils.commit();
					logdetailMap = null;
				} catch (SQLException e) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					logdetailMap.put("OWNERTABLE", "ST_PI_TASK");
					logdetailMap.put("OWNERID", String.valueOf(st_pi_taskid));
					logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
					logdetailMap.put("DATA", jdbcUtils.getSqlLog());
					logdetailMap.put("ERRORMASSAGE", e.getMessage());
					jdbcUtils.rollback();
					e.printStackTrace();
				} catch (RemoteException e) {
					jdbcUtils.rollback();
					e.printStackTrace();
				} catch (MXException e) {
					jdbcUtils.rollback();
					e.printStackTrace();
				} finally {
					jdbcUtils.closePstmt(devicePstmt);
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

}
