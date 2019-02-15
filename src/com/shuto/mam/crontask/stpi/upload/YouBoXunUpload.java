package com.shuto.mam.crontask.stpi.upload;

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

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;

import com.shuto.mam.crontask.stpi.dbutils.JdbcUtils;
import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.crontask.stpi.interfacelog.Interfacelog;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年6月9日
 * @since:中间库上传巡点检任务到maximo端(已经可使用)
 */
public class YouBoXunUpload extends PiUpload {
	
	@Override
	public int[] upload(MboRemote dbMbo) {//dbMbo是已经得到任务id的
		int[] result = new int[2];  
		JdbcUtils jdbcUtils = new JdbcUtils();//连接中间数据库
		MaximoUtils maximoUtils = new MaximoUtils();// 连接maximo数据库
		Connection connection = jdbcUtils.getConnection(dbMbo);//中间库的连接
		Connection connection2 = maximoUtils.getMaximoConn();//maximo的连接
		PreparedStatement updateMaxTask=null;//更新max任务表
		PreparedStatement updatePerson=null;//更新max人员表
		PreparedStatement updateAreaps=null;//更新max区域表
		PreparedStatement updateM_pi_task=null;
		PreparedStatement m_task=null;//查询中间库任务表状态是已上传结果的数据
		PreparedStatement m_taskDetailps=null;//查询中间库项目表，看那些点未检查
		PreparedStatement selcetArea=null;//查询中间库已到位区域
		PreparedStatement selcetPerson=null;//查询person表
		PreparedStatement getDownloader=null;//新加的      看中间库谁是下载人
		PreparedStatement updateDownloader=null;//新加的   跟新到maximo端任务表的downloader
		PreparedStatement itempsDetailps=null;//查询中间库项目表，看具体结果
		PreparedStatement updateItem_1=null;//更新maximo项目表
		PreparedStatement updateItem_2=null;//更新maximo项目表
		
		
		ResultSet detailSet = null;//任务表详情
		ResultSet areaSet = null;
		ResultSet personSet = null;
		ResultSet getdownloaderSet = null;
		ResultSet itemSet =null;
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
			
			//查询maximo端person表用于封装新旧personid
			Map<String,String> personidMap = new HashMap<String,String>();
			String personsql = "select personid,oldpersonid from person where oldpersonid is not null";//已上传中间库，未同步到max
			selcetPerson = connection2.prepareStatement(personsql);
			personSet = selcetPerson.executeQuery();
			while(personSet.next()){
				personidMap.put(personSet.getString("oldpersonid"), personSet.getString("personid"));
			}
			
			st_pi_dbsourceid = dbMbo.getUniqueIDValue();
			orgid = dbMbo.getString("orgid");
			siteid = dbMbo.getString("siteid");
			description = dbMbo.getString("DESCRIPTION");
			Set<Integer> m_taskids=new HashSet<Integer>();//创建任务集合
			String itemsql="select pi_taskid from m_pi_task where status='已上传结果'";//已上传中间库，未同步到max
		    m_task=connection.prepareStatement(itemsql);
			ResultSet rs=m_task.executeQuery();
			while(rs.next()){
				m_taskids.add(rs.getInt("pi_taskid"));//得到的任务id放到集合里
			}
			counts=m_taskids.size();//记录日志用的
			result[0] =m_taskids.size();//
			result[1] =0;
			rs.close();
			
			//更新max任务表
			String updateMTask = "update st_pi_task set norm_area=?,real_area=?,in_place_rate=?,norm_point=?,"
	          		+ "real_point=?,pi_rate=?,missed_point=?,missed_rate=?,ignore_point=?,ABNORMAL_POINT=?,"
	          		+ "status=? where st_pi_taskid=? ";
			updateMaxTask = connection2.prepareStatement(updateMTask);
			//更新max人员表
			String updateUpLoadUser = "update st_pi_task_user set isdownload_user =1 where st_pi_taskid = ? and personid =? ";
	        updatePerson = connection2.prepareStatement(updateUpLoadUser);//更新maximo人员表谁是下载人
	        //更新max区域表
	        String updateArea = "update st_pi_task_area set status='已到位' where st_pi_taskid=? and no=?";
			updateAreaps = connection2.prepareStatement(updateArea);
			//更新m_pi_task，已经同步的的多个状态‘已同步’
	        String updatem_pi_task = "update m_pi_task set status='已同步结果' where status='已上传结果' and pi_taskid=?";
	        updateM_pi_task = connection.prepareStatement(updatem_pi_task);
			
			//查细节，以便更新max任务表 ：中间表norm_area=应到区域  real_area=实到区域 in_place_rate=到位率 norm_point=应检点数 real_point=实检点数，pi_rate=点检率
			String detailsql = "select pi_taskid, norm_area ,real_area ,in_place_rate,norm_point,"
			        + "real_point,pi_rate ,missed_point,missed_rate,ignore_point,ycd,task_downloader from"
			        + " m_pi_task where status='已上传结果'";
			m_taskDetailps = connection.prepareStatement(detailsql);
			 //更新maximo项目表
			String updateitemSql_1 = "update ST_PI_TASK_ITEM set PI_TASK_ITEM_RESULT  = ?,CHECK_ON_OFF  =?, ISCHECK=? where ST_PI_TASK_ITEMID =? and st_pi_taskid=?";
			String updateitemSql_2 = "update ST_PI_TASK_ITEM set  PI_TASK_ITEM_RESULT  = ?,CHECK_ON_OFF  =?,DOUBLERET  =?,ISCHECK=? where ST_PI_TASK_ITEMID =? and st_pi_taskid=?";
	        updateItem_1 = connection2.prepareStatement(updateitemSql_1);
			updateItem_2 = connection2.prepareStatement(updateitemSql_2);
			//查询中间库区域表
	        String m_area = "select num from m_pi_task_area where pi_task_no=(select num from m_pi_task where pi_taskid = ?) and  status='已到位' ";
	        selcetArea = connection.prepareStatement(m_area);
	        
	        //看项目表细节，为了更细maximo项目表,11月20加的
	        String itemdetailsql="select * from m_pi_task_item where pi_task_no=(select num from m_pi_task where pi_taskid=?) and pi_task_time is not null";
	        itempsDetailps=connection.prepareStatement(itemdetailsql);
	       
	        
	        //查询中间库谁是下载人            新加的
	        String findDownloader="select * from m_pi_task ";
	        getDownloader=connection.prepareStatement(findDownloader);
	        
	        //更新max谁是下载人        新加的
	        String upDownloader="update st_pi_task set DOWNLOADER=(select displayname from person where personid = "
	        		+ "(select tu.personid from st_pi_task_user tu,person p where tu.personid=p.personid(+) and tu.st_pi_taskid=? and p.personid=?)) where st_pi_taskid= ?";
	        updateDownloader=connection2.prepareStatement(upDownloader);
	        getdownloaderSet=getDownloader.executeQuery();
	        while(getdownloaderSet.next()){
	        	int id=getdownloaderSet.getInt("pi_taskid");
	        	String name=getdownloaderSet.getString("task_downloader");
	        	updateDownloader.setInt(1, id);
	            updateDownloader.setString(2, name);
	        	updateDownloader.setInt(3, id);
	        	updateDownloader.execute();
	        }
	      
	     
	        
			detailSet = m_taskDetailps.executeQuery();
			MboSetRemote piTasks = null;
			while(detailSet.next()){
				int taskid=0;
				try {
			    	 taskid=detailSet.getInt("pi_taskid");//得到任务id
			    	 
				     //查询系统是否有生成的任务（防止A/B厂上传C厂数据，C厂上传A/B厂数据）
					 piTasks = dbMbo.getMboSet("$st_pi_task"+taskid,"ST_PI_TASK", "siteid='"+siteid+"' and st_pi_taskid="+taskid);
					 //如果没有数据则跳过这条任务
					 if(piTasks.isEmpty()){
					     continue;
					 }
			    	 
					 //更新max任务表
			    	 int norm_area=detailSet.getInt("norm_area");
			    	 int real_area= detailSet.getInt("real_area");
			         Double in_place_rate=detailSet.getDouble("in_place_rate");//到位率
			         int norm_point=detailSet.getInt("norm_point");
			         int real_point=detailSet.getInt("real_point");
			         Double pi_rate=detailSet.getDouble("pi_rate");//点检率
			         int missed_point=detailSet.getInt("missed_point");
			         Double missed_rate=detailSet.getDouble("missed_rate");//漏检率
			         int ignore_point=detailSet.getInt("ignore_point");//过滤点
			         int ycd=detailSet.getInt("ycd");
			         String task_downloader = detailSet.getString("task_downloader");//检查人
			         //如果存在新旧personid映射数据，则根据老perosnid获取新personid
			         if (personidMap.containsKey(task_downloader)) {
			        	 task_downloader = personidMap.get(task_downloader);
			         }
			         updateMaxTask.setInt(1, norm_area);
			         updateMaxTask.setInt(2, real_area);
			         updateMaxTask.setDouble(3, in_place_rate);
			         updateMaxTask.setInt(4, norm_point);
			         updateMaxTask.setInt(5, real_point);
			         updateMaxTask.setDouble(6, pi_rate);
			         updateMaxTask.setInt(7, missed_point);
			         updateMaxTask.setDouble(8, missed_rate);
			         updateMaxTask.setInt(9, ignore_point);
			         updateMaxTask.setInt(10, ycd);
			         updateMaxTask.setString(11, "已上传结果");
			         updateMaxTask.setInt(12, taskid);
			         updateMaxTask.addBatch();
			         
			         //更新maximo人员表
			         updatePerson.setInt(1, taskid);
			         updatePerson.setString(2, task_downloader);
			         updatePerson.addBatch();
			        
			         
			         
			         //更新maximo区域表
			         selcetArea.setInt(1, taskid);
			         areaSet = selcetArea.executeQuery();
			         while(areaSet.next()){
			        	  String num=areaSet.getString("num");
						  updateAreaps.setInt(1, taskid);
						  updateAreaps.setString(2, num);
						  updateAreaps.addBatch();
			         }
			         
			         //更新项目表
			         itempsDetailps.setInt(1, taskid);
			         itemSet=itempsDetailps.executeQuery();
			         while(itemSet.next()){
			        	 int itemid=itemSet.getInt("pi_task_itemid");
			        	 String on_off_potin=itemSet.getString("on_off_potin");//启停点
			        	 String check_method=itemSet.getString("check_method");//方法
			        	 String pi_task_item_result=itemSet.getString("pi_task_item_result");//结果
			        	 Double doubleret=itemSet.getDouble("doubleret");//数值
			        	 //String together=""+doubleret;
			        	 if(check_method.equals("观察")){
			        		 updateItem_1.setString(1, pi_task_item_result);
			        		 updateItem_1.setString(2,on_off_potin);
			        		 updateItem_1.setInt(3, 1);
			        		 updateItem_1.setInt(4, itemid);
			        		 updateItem_1.setInt(5, taskid);
			        		 updateItem_1.addBatch();
			        	 }else{
			        		 updateItem_2.setString(1, pi_task_item_result);
			        		 updateItem_2.setString(2,on_off_potin);
			        		 updateItem_2.setDouble(3, doubleret);
			        		 updateItem_2.setInt(4, 1);
			        		 updateItem_2.setInt(5, itemid);
			        		 updateItem_2.setInt(6, taskid);
			        		 updateItem_2.addBatch();
			        	 }
			         }
			         updateItem_1.executeBatch();
					 updateItem_2.executeBatch();
			         
			         //更新m_pi_task，已经同步的的多个状态‘已同步’
			         updateM_pi_task.setInt(1, taskid);
			         updateM_pi_task.addBatch();
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
					logdetailMap.put("OWNERID", String.valueOf(taskid));
					logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
					logdetailMap.put("DATA", sqlLog);
					logdetailMap.put("ERRORMASSAGE", e.getMessage());
					logList.add(logdetailMap);
					e.printStackTrace();
				} finally {
					if (piTasks != null) {
						piTasks.close();
					}
					if(areaSet!=null){
			        	areaSet.close();
			        }
				}
				
				//按任务分批提交
				updateMaxTask.executeBatch();//关闭的连接出现在这里
				updatePerson.executeBatch();
				updateAreaps.executeBatch();
				updateM_pi_task.executeBatch();
				connection.commit();
				connection2.commit();	
				connection.setAutoCommit(false);
				connection2.setAutoCommit(false);
			}//while结束
		} catch (Exception e) {
			Map<String, String> logdetailMap = new HashMap<String, String>();
			logdetailMap.put("OWNERTABLE", "ST_PI_DBSOURCE");
			logdetailMap.put("OWNERID", String.valueOf(st_pi_dbsourceid));
			logdetailMap.put("CREATETIME", dateFormat.format(new Date()));
			logdetailMap.put("DATA", jdbcUtils.getSqlLog());
			logdetailMap.put("ERRORMASSAGE", e.getMessage());
			logList.add(logdetailMap);
			e.printStackTrace();
		} finally {
			try {
				if (detailSet != null) {
					detailSet.close();
				}
				if(getdownloaderSet!=null){
					getdownloaderSet.close();
				}
				if(selcetArea!=null){
					selcetArea.close();
				}
				if(updateItem_1!=null){
					updateItem_1.close();
				}
				if(updateItem_2!=null){
					updateItem_2.close();
				}
				if(selcetPerson!=null){
					selcetPerson.close();
				}
				if(personSet!=null){
					personSet.close();
				}
				if(m_taskDetailps!=null){
					m_taskDetailps.close();
				}	
				if(getDownloader!=null){
					getDownloader.close();
				}
				if(updateDownloader!=null){
					updateDownloader.close();
				}
				if(m_task!=null){
					m_task.close();
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
				if(updateM_pi_task!=null){
					updateM_pi_task.close();
				}
				if(connection!=null){
					connection.close();
				}
				if(connection2!=null){
					connection2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		
		return result;
	}

}
