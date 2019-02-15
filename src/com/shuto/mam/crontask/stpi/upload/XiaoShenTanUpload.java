package com.shuto.mam.crontask.stpi.upload;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shuto.mam.crontask.stpi.dbutils.JdbcUtils;
import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.crontask.stpi.interfacelog.Interfacelog;
import com.shuto.mam.crontask.stpi.upload.PiUpload;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年6月9日
 * @since:中间库上传巡点检任务到maximo端(已经可使用)
 */
public class XiaoShenTanUpload extends PiUpload {
	
	@Override
	public int[] upload(MboRemote dbMbo) {//dbMbo是已经得到任务id的
		int[] result = new int[2];  
		JdbcUtils jdbcUtils = new JdbcUtils();//连接中间数据库
		MaximoUtils maximoUtils = new MaximoUtils();// 连接maximo数据库
		Connection connection = jdbcUtils.getConnection(dbMbo);//中间库的连接
		Connection connection2 = maximoUtils.getMaximoConn();//maximo的连接
		PreparedStatement itemps=null;//查询中间库项目表检查过但没同步到maximo的
		PreparedStatement itempsDetailps=null;//查询中间库项目表，看那些点未检查
		PreparedStatement updateMaxTask=null;//更新maximo任务表
		
		PreparedStatement updateItem_1=null;//更新maximo项目表
		PreparedStatement updateItem_2=null;//更新maximo项目表
		PreparedStatement updateItem_3=null;//更新maximo项目表
		
		PreparedStatement updatePerson=null;//更新max人员表
		PreparedStatement updateAreaps=null;//更新max区域表
		PreparedStatement updateinterfaceps=null;//更新中间库项目表
		PreparedStatement updateDj_line=null;//更新中间库任务表
		PreparedStatement taskps=null;//maximo查询表
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		MboSetRemote piTasks=null;
		Set<String> placeSet=null;
		
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
			connection.setAutoCommit(false);
			connection2.setAutoCommit(false);
			
			st_pi_dbsourceid = dbMbo.getUniqueIDValue();
			orgid = dbMbo.getString("orgid");
			siteid = dbMbo.getString("siteid");
			description = dbMbo.getString("DESCRIPTION");
			//中间库查询已经检查过但是未同步到max的数据,放到集合里,同一条任务对应多项目且不重复，所以吧line_id放到set里
			Set<Integer> lineidSet=new HashSet<Integer>();//创建任务集合
			String itemsql="select line_id from xj_taskinterface where checktime is not null and suc_yn is null group by line_id";//检查过但未同步
		    itemps=connection.prepareStatement(itemsql);
			ResultSet rs=itemps.executeQuery();
			while(rs.next()){
				lineidSet.add(rs.getInt("line_id"));//得到的任务id放到集合里
			}
			counts=lineidSet.size();
			result[0] =lineidSet.size();//要同步的
			result[1] =0;//同步失败的
			rs.close();
			//巡点检项目里检查过但是没同步到maximo的; mobjectstate_cd='001',place_tx=区域名称；djtask_id=自增的id;
			//datatype_cd=观察记录等方法；checkresult_tx=应该是检查结果（无数据）；exceptiondesc_tx=异常信息（无数据）；
			//checkname_tx=应该是检查人名字（无数据）；srmobjectstatename_tx=启停点（无数据）；
			String detailsql="select mobjectstate_cd ,place_tx ,djtask_id ,idpos_id, datatype_cd,checkresult_tx,exceptiondesc_tx,"
						+ "to_char(checktime,'yyyy-MM-dd HH24:mi:ss') as checktime ,checkname_tx,srmobjectstatename_tx"
						+ " from xj_taskinterface where checktime is not null and suc_yn is null and  line_id  = ? ";
			itempsDetailps = connection.prepareStatement(detailsql);
			
			String queryTaskSql = "select * from st_pi_task where st_pi_taskid =?";
			taskps = connection2.prepareStatement(queryTaskSql);
			
			 //更新maximo项目表
			 String updateSql_1 = "update ST_PI_TASK_ITEM set PI_TASK_ITEM_RESULT  = ?,CHECK_ON_OFF  =?,CHECK_TIME =to_date(?,'yyyy-mm-dd hh24:mi:ss'), ISCHECK=? where ST_PI_TASK_ITEMID =?";
			 String updateSql_2 = "update ST_PI_TASK_ITEM set  PI_TASK_ITEM_RESULT  = ?,CHECK_ON_OFF  =?,CHECK_TIME =to_date(?,'yyyy-mm-dd hh24:mi:ss'),DOUBLERET  =?,ISCHECK=? where ST_PI_TASK_ITEMID =?";
	         String updateSql_3 = "update ST_PI_TASK_ITEM set PI_TASK_ITEM_RESULT  = ?,CHECK_ON_OFF  =?,CHECK_TIME =to_date(?,'yyyy-mm-dd hh24:mi:ss') ,ISCHECK=? where ST_PI_TASK_ITEMID =?";
			 updateItem_1 = connection2.prepareStatement(updateSql_1);
			 updateItem_2 =connection2.prepareStatement(updateSql_2);
			 updateItem_3 = connection2.prepareStatement(updateSql_3);
			 //更新中间库项目表状态为已同步
			 String interFaceSql = "update xj_taskinterface set suc_yn ='Y' where djtask_id =?";
		     updateinterfaceps = connection.prepareStatement(interFaceSql);
		     //更新中间库任务表
		     String dj_lineSql = "update dj_line set suc_yn='Y' where line_id=?";
		     updateDj_line = connection.prepareStatement(dj_lineSql);
		     //更新maximo人员表谁是下载人
		     String updateUpLoadUser = "update st_pi_task_user set isdownload_user =1 where st_pi_taskid = ? "
		     		+ "and personid = (select tu.personid from st_pi_task_user tu,person p where tu.personid=p.personid(+) and tu.st_pi_taskid=? and p.displayname=?) ";
		     updatePerson = connection2.prepareStatement(updateUpLoadUser);
		     //更新maximo区域表
		     String updateArea = "update st_pi_task_area set status='已到位' where st_pi_task_areaid=? ";
			 updateAreaps = connection2.prepareStatement(updateArea);
			 //更新maximo任务表
			 String updateTask = "update ST_PI_TASK set status =?,ABNORMAL_POINT =?, "
			          + "IN_PLACE_RATE =?,PI_RATE =? ,MISSED_RATE =?,REAL_POINT =?,MISSED_POINT =? ,IGNORE_POINT  =? ,REAL_AREA  =?, DOWNLOADER = (select tu.personid from st_pi_task_user tu,person p where tu.personid=p.personid(+) and tu.st_pi_taskid=? and p.displayname=?) where  st_pi_taskid =?";
			 updateMaxTask = connection2.prepareStatement(updateTask);
			 
			 for(Integer lineid:lineidSet) {//遍历未上传的任务
				double checkCount=0.0d;//检查点数
				double ignoreCount=0.0d;//过滤点数
				int ycd=0;//异常点
				try {
					//查询系统是否有生成的任务（防止A/B厂上传C厂数据，C厂上传A/B厂数据）
			        piTasks = dbMbo.getMboSet("$st_pi_task"+lineid,"ST_PI_TASK", "siteid='"+siteid+"' and st_pi_taskid="+lineid);
			        //如果没有数据则跳过这条任务
			        if(piTasks.isEmpty()){
			        	continue;
			        }
			        
			        placeSet=new HashSet<String>();//每条任务对应多个区域，所以每遍历一条任务就建一个集合，放进去实到区域
			       
					itempsDetailps.setLong(1, lineid);
					rs2 = itempsDetailps.executeQuery();
					while(rs2.next()) {//遍历项目的结果集  362
						String data_type = rs2.getString("datatype_cd");//观察方法（GC，CZ...）
				        //double doubleResult = 0.0D;//数值结果
						Double doubleResult = 0.0D;
				        String strResult = "";//结果描述
				        String check_user = rs2.getString("checkname_tx");//检查人
				        int taskItemNum = rs2.getInt("djtask_id");//得到项目表中的项目id
				        int areaid=rs2.getInt("idpos_id");//取出区域ID
				        String on_off = rs2.getString("srmobjectstatename_tx");//213库启停点  有三个值:运行，备用，停机，和空
				        String begin_On_off = rs2.getString("mobjectstate_cd");//目前数据是001 213库也是001
				        String checkTime = rs2.getString("checktime");//取出字符串的时间
				        if ("100".equals(begin_On_off)){
				            begin_On_off = "停机";
				          }
				          else if ("10".equals(begin_On_off)){
				            begin_On_off = "备用";
				          }    
				          else {
				            begin_On_off = "运行";//mobjectstate_cd=001
				          }
				        if ((begin_On_off.equals(on_off)) && (rs2.getString("exceptiondesc_tx") != null)){
				            checkCount += 1.0D;//由于begin_on_off无数据，所以对应不上，checkcount为0 ，以致后面的巡店率为0 
				          }
				          else if ((!begin_On_off.equals(on_off)) && (rs2.getString("srmobjectstatename_tx") != null)) {
				            ignoreCount += 1.0D;
				          }
				        String place = rs2.getString("place_tx");//取出实到区域名称
				        placeSet.add(place);//集合里添加上地点
				        if ("异常".equals(rs2.getString("exceptiondesc_tx"))) {
				           ycd++;
				        }

				        //更新maximo项目表
				        if ("GC".equals(data_type)) {
				            strResult = rs2.getString("exceptiondesc_tx");//异常信息
				            if (strResult == null) {
				              strResult = "";
				            }
				            updateItem_1.setString(1, strResult);
				            updateItem_1.setString(2, on_off);
				            updateItem_1.setString(3,checkTime);
				            updateItem_1.setInt(4, 1);
				            updateItem_1.setInt(5, taskItemNum);
				            updateItem_1.addBatch();
				            
				        } else if ((rs2.getString("checkresult_tx") != null) && (!"".equals(rs2.getString("checkresult_tx")))) {
				        	doubleResult = Double.parseDouble(rs2.getString("checkresult_tx"));//checkresult_tx类型是string要装成double
				            strResult = rs2.getString("exceptiondesc_tx");
				            updateItem_2.setString(1, strResult);
				            updateItem_2.setString(2, on_off);
				            updateItem_2.setString(3,checkTime);
				            updateItem_2.setDouble(4, doubleResult);  
				            updateItem_2.setInt(5, 1);
				            updateItem_2.setInt(6, taskItemNum);
				            updateItem_2.addBatch();
				        } else {
				        	updateItem_3.setString(1, strResult);
				        	updateItem_3.setString(2, on_off);
				        	updateItem_3.setString(3,checkTime);
				        	updateItem_3.setInt(4, 1);
				        	updateItem_3.setInt(5, taskItemNum);
				        	updateItem_3.addBatch();
				        }
				        
				        //更新中间库项目表状态为已同步
				        updateinterfaceps.setInt(1, taskItemNum);
				        updateinterfaceps.addBatch();
				        
				        //更新中间库任务表
				        updateDj_line.setInt(1, lineid);
				        updateDj_line.addBatch();
				        
				        //更新maximo人员表谁是下载人
				        updatePerson.setInt(1, lineid);
				        updatePerson.setInt(2, lineid);
				        updatePerson.setString(3, check_user);
				        updatePerson.addBatch();
				        
				        //更新max区域表
						updateAreaps.setInt(1, areaid);
						updateAreaps.addBatch();
						
						//更新maximo任务表中到位率
					    taskps.setInt(1, lineid);
					    rs3 =taskps.executeQuery();
					    double totalArea = 0.0D;
					    double totalPoint = 0.0D;
					    double missPoint = 0.0D;
					    String IN_PLACE_RATE = "";//maximo任务表的到位率
					    String PI_RATE = "";//点检率
					    String MISSED_RATE = "";//漏检率
					    if (rs3.next()) {
					        totalArea = rs3.getInt("NORM_AREA");//st_pi_task表的应到区域
					        totalPoint = rs3.getInt("NORM_POINT");//st_pi_task的应检点
					        missPoint = totalPoint - checkCount - ignoreCount;//漏检点=应检查点-检查点-过滤点
					        IN_PLACE_RATE = getRate(placeSet.size() / totalArea);//实到区域：总区域
					        PI_RATE = getRate((ignoreCount + checkCount) / totalPoint);//点检率
					        MISSED_RATE = getRate((totalPoint - ignoreCount - checkCount) / totalPoint);
					      
					        updateMaxTask.setString(1, "已上传结果");
					        updateMaxTask.setInt(2, ycd);//异常点
					        updateMaxTask.setString(3, IN_PLACE_RATE);//到位率
					        updateMaxTask.setString(4, PI_RATE);//点检率
					        updateMaxTask.setString(5, MISSED_RATE);//漏检率
					        updateMaxTask.setDouble(6, checkCount);//实检点
					        updateMaxTask.setDouble(7, missPoint);//漏检点
					        updateMaxTask.setDouble(8, ignoreCount);//过滤点
					        updateMaxTask.setInt(9, placeSet.size());//实到区域
					        updateMaxTask.setInt(10, lineid);
					        updateMaxTask.setString(11, check_user);
					        updateMaxTask.setInt(12, lineid);
					        updateMaxTask.addBatch();
					        scounts++;
					    }
					}
					
					//按任务分批提交
					updateItem_1.executeBatch();
					updateItem_2.executeBatch();
					updateItem_3.executeBatch();
					updateinterfaceps.executeBatch();
					updateDj_line.executeBatch();
					updatePerson.executeBatch();
					updateAreaps.executeBatch();
					updateMaxTask.executeBatch();
					
					connection.commit();
					connection2.commit();
					connection.setAutoCommit(false);
					connection2.setAutoCommit(false);
				} catch (SQLException e) {
					result[1]++;
					ecounts++;
					jdbcUtils.rollback();
					maximoUtils.rollback();//关闭的连接出现在这里
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
				} finally {
					if (piTasks != null) {
						piTasks.close();
					}
					if (rs2 != null) {
						rs2.close();
					}
					if (rs3 != null) {
						rs3.close();
					}
				}
			}//for的结束
		} catch (Exception e) {
			Map<String, String> logdetailMap = new HashMap<String, String>();
			logdetailMap.put("OWNERTABLE", "ST_PI_DBSOURCE");
			logdetailMap.put("OWNERID", String.valueOf(st_pi_dbsourceid));
			logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
			logdetailMap.put("DATA", jdbcUtils.getSqlLog());
			logdetailMap.put("ERRORMASSAGE", e.getMessage());
			logList.add(logdetailMap);
			e.printStackTrace();
		}finally{
			try {
				if(itempsDetailps!=null){
					itempsDetailps.close();
				}
				if(taskps!=null){
					taskps.close();
				}
				if(itemps!=null){
					itemps.close();
				}
				if(updateMaxTask!=null){
					updateMaxTask.close();
				}
				if(updatePerson!=null){
					updatePerson.close();
				}
				if(updateAreaps!=null){
					updateAreaps.close();
				}
				if(updateDj_line!=null){
					updateDj_line.close();
				}
				if(updateItem_1!=null){
					updateItem_1.close();
				}
				if(updateItem_2!=null){
					updateItem_2.close();
				}
				if(updateItem_3!=null){
					updateItem_3.close();
				}
				if(updateinterfaceps!=null){
					updateinterfaceps.close();
				}
				if(connection!=null){
					connection.close();
				}
				if(connection2!=null){
					connection2.close();//与数据库的连接已断开出现在这里
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//finally结束
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
		
		return result;
	}
	
	public String getRate(double ret) {
		    ret *= 100.0D;
		    BigDecimal bg = new BigDecimal(ret);
		    double result = bg.setScale(2, 4).doubleValue();
		    return String.valueOf(result);
	}

}
