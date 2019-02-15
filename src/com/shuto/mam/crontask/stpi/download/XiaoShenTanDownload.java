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
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import com.shuto.mam.crontask.stpi.dbutils.JdbcUtils;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年5月11日
 * @since:小神探检点推送中间库
 */
public class XiaoShenTanDownload extends PiDownload{
	

	@Override
	public Map<String, String> download(MboRemote taskMbo, MboSetRemote areaSet, MboSetRemote itemSet,
			MboSetRemote userSet) {
		Map<String, String> logdetailMap = new HashMap<String, String>();
		try {
			long st_pi_taskid = taskMbo.getUniqueIDValue();
			//连接数据库
			JdbcUtils jdbcUtils = new JdbcUtils();
			PreparedStatement areaPstmt = null;
			PreparedStatement itemPstmt = null;
			PreparedStatement userPstmt = null;
			PreparedStatement middlePstmt = null;
			try {
				MboRemote dbMbo = taskMbo.getMboSet("ST_PI_DBSOURCE").getMbo(0);
				jdbcUtils.getConnection(dbMbo);
				//插入任务
				String taskSql="insert into dj_line(line_id,name_tx,idtype_cd,query_dt,start_dt,end_dt) values(?,?,?,?,?,?)";
				List<Object> taskParams = new ArrayList<Object>();
				taskParams.add(taskMbo.getInt("ST_PI_TASKID"));
				taskParams.add(taskMbo.getString("task_name"));
				taskParams.add("R");
				taskParams.add(taskMbo.getDate("BEGIN_TIME"));
				taskParams.add(taskMbo.getDate("BEGIN_TIME"));
				taskParams.add(taskMbo.getDate("END_TIME"));
				jdbcUtils.updateByPreparedStatement(taskSql, taskParams);
				
				//插入区域
				String areaSql="insert into dj_idpos( idpos_id,line_id,idpos_cd,place_tx,mustcheck_yn,sortno_nr,idtype_cd) values(?,?,?,?,?,?,?)";
				areaPstmt=jdbcUtils.LoggableStatement(areaSql);
				
				MboRemote areaMbo = null;
				//区域下的项目
				MboSetRemote areaItemSet = null;
				MboRemote itemMbo = null;
				int num = 1;
				for (int i = 0; i < areaSet.count(); i++) {
					areaMbo = areaSet.getMbo(i);
					//获取区域下的项目
					areaItemSet = areaMbo.getMboSet("$st_pi_task_item", "st_pi_task_item", "st_pi_task_areaid=:st_pi_task_areaid");
					areaItemSet.setOrderBy("deviceseq,seq");
	
					List<Object> areaParams = new ArrayList<Object>();
					areaParams.add(areaMbo.getUniqueIDValue());
					areaParams.add(areaMbo.getInt("ST_PI_TASKID"));
					areaParams.add(areaMbo.getString("RFID_CODE"));
					areaParams.add(areaMbo.getString("DESCRIPTION"));
					areaParams.add(0);
					areaParams.add(areaMbo.getUniqueIDValue());//原先是取的seq，现在改成areaMbo.getUniqueIDValue()
					areaParams.add("D");
					jdbcUtils.updateBatchByPreparedStatement(areaPstmt, areaParams);
					
					//插入设备表
					String insertMiddleTableSql="insert into dj_controlpoint(controlpoint_id,line_id,name_tx,srplan_yn,lkplan_yn,sortno_nr) values(?,?,?,?,?,?)";
					middlePstmt = jdbcUtils.LoggableStatement(insertMiddleTableSql);
					Map<String,Long> deveiceMap = new HashMap<String,Long>();//创建设备集合，key=设备名字 value=设备ID
					String areaDeviceName = null;
					Long controlpoint_id = 0L;
					for (int m = 0; m < areaItemSet.count(); m++) {
						itemMbo = areaItemSet.getMbo(m);
						areaDeviceName = itemMbo.getString("DEVICE_NAME");
						//根据设备名称判重
						if (!deveiceMap.containsKey(areaDeviceName)) {
							controlpoint_id = jdbcUtils.getNextSequence("dj_controlpoint_SS");
							List<Object> midParams = new ArrayList<Object>();
							midParams.add(controlpoint_id);
							midParams.add(taskMbo.getInt("ST_PI_TASKID"));
							midParams.add(itemMbo.getString("DEVICE_NAME"));
							midParams.add("1");
							midParams.add("0");
							midParams.add(itemMbo.getInt("DEVICESEQ"));
							deveiceMap.put(areaDeviceName, controlpoint_id);
							
							jdbcUtils.updateBatchByPreparedStatement(middlePstmt, midParams);
						}
					}
					
					//插入项目
					String itemSql="insert into XJ_TASKINTERFACE(DJTASK_ID, LINE_ID, POINT, ZJHRQ, SORT_NR,"
							+ "MOBJECTNAME_TX, CHECKPART_TX, CHECKCONTENT_TX, ESTSTANDARD_TX, DATATYPE_CD,"
							+ "LOWERLIMIT_TX, UPPERLIMIT_TX, METRICUNIT_TX, ORG_ID, ORGNAME_TX, APPUSER_ID,"
							+ "APPUSERNAME_TX, SPANVALUE_NR,SPANTYPE_TX, TASKSTART_DT, TASKEND_DT,IDPOS_ID,"
							+ "IDPOS_CD, PLACE_TX, DATACODEGROUP_ID, IDTYPE_CD, SIGNTYPE_CD, ZHENDONG_PP, "
							+ "LKPOINT_ID,SRPOINT_ID,MOBJECTSTATE_CD) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)  ";
					itemPstmt = jdbcUtils.LoggableStatement(itemSql);
					//同一个区域下的项目从1开始编号
					num = 1;
					List<Object> itemParams = null;
					for (int n = 0; n < areaItemSet.count(); n++) {//遍历项目
						itemMbo = areaItemSet.getMbo(n);
						itemParams = new ArrayList<Object>();
						areaDeviceName = itemMbo.getString("DEVICE_NAME");
						
						itemParams.add(itemMbo.getUniqueIDValue());
						itemParams.add(itemMbo.getInt("ST_PI_TASKID"));
						itemParams.add(itemMbo.getInt("ST_PI_TASKID"));
						itemParams.add(0);
						itemParams.add(num++);
						itemParams.add(itemMbo.getString("DEVICE_NAME"));
						itemParams.add(itemMbo.getString("description"));
						itemParams.add(itemMbo.getString("CHECK_METHOD"));
						itemParams.add(itemMbo.getString("POINT_NORM"));
						itemParams.add(itemMbo.getString("POINT_TYPE"));
						itemParams.add(itemMbo.getDouble("LOWER_LIMIT"));
						itemParams.add(itemMbo.getDouble("HIGHER_LIMIT"));
						itemParams.add(itemMbo.getString("POINT_UNIT"));
						itemParams.add(0);//对应orgid
						itemParams.add(itemMbo.getString("SITEID"));
						itemParams.add(0);
						itemParams.add("-1");//APPUSERNAME_TX
						itemParams.add(1);//SPANVALUE_NR
						itemParams.add("D");//SPANTYPE_TX4
						itemParams.add(taskMbo.getDate("BEGIN_TIME"));
						itemParams.add(taskMbo.getDate("END_TIME"));
						itemParams.add(itemMbo.getInt("ST_PI_TASK_AREAID"));
						itemParams.add(itemMbo.getString("ST_PI_TASK_AREA.RFID_CODE"));
						itemParams.add(itemMbo.getString("ST_PI_TASK_AREA.description"));//place_tx
						itemParams.add(1);
						itemParams.add("D");//IDTYPE_CD
						itemParams.add(itemMbo.getString("SHAKE_TYPE"));
						itemParams.add(1);
						itemParams.add(-1);
						itemParams.add(deveiceMap.get(areaDeviceName));//SRPOINT_ID, 设备id
						String on_off_point = itemMbo.getString("ON_OFF_POTIN");
				        String on_off_state = null;
				        if ("备用".equals(on_off_point))
				          on_off_state = "10";
				        else if ("停机".equals(on_off_point))
				          on_off_state = "100";
				        else {
				          on_off_state = "001";
				        }
						itemParams.add(on_off_state);
						jdbcUtils.updateBatchByPreparedStatement(itemPstmt, itemParams);
					}
					areaItemSet.close();
					middlePstmt.executeBatch();
					itemPstmt.executeBatch();
				}
				
				//插入人员
				String userSql="insert into dj_appuser (appuser_id, line_id, appuser_cd, name_tx, password_tx) values(?,?,?,?,?)";
				userPstmt = jdbcUtils.LoggableStatement(userSql);
				for (int i = 0; i < userSet.count(); i++) {
					MboRemote userMbo = userSet.getMbo(i);
					List<Object> userParams = new ArrayList<Object>();
					userParams.add(userMbo.getUniqueIDValue());
					userParams.add(taskMbo.getInt("ST_PI_TASKID"));
					userParams.add(taskMbo.getInt("ST_PI_TASKCFGID"));
					userParams.add(userMbo.getString("person.DISPLAYNAME"));
					userParams.add("");
					jdbcUtils.updateBatchByPreparedStatement(userPstmt, userParams);
				}
				
				areaPstmt.executeBatch();
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
			}catch (RemoteException e) {
				jdbcUtils.rollback();
				e.printStackTrace();
			} catch (MXException e) {
				jdbcUtils.rollback();
				e.printStackTrace();
			} finally {
				jdbcUtils.closePstmt(middlePstmt);
				jdbcUtils.closePstmt(areaPstmt);
				jdbcUtils.closePstmt(itemPstmt);
				jdbcUtils.closePstmt(userPstmt);
				//关闭数据库连接
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
