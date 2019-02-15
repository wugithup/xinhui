package com.shuto.mam.crontask.stpi.download;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Test {
	public static Connection getConnection() { // 连接数据库方法
		Connection conn = null;
		try {
			String dbdriver = "oracle.jdbc.driver.OracleDriver";
			String dburl = "jdbc:oracle:thin:@localhost:1521:maximo";
			String dbusername = "maximo";
			String dbpassword = "maximo";
			// 连接数据库
			Class.forName(dbdriver);
			System.out.println("数据库连接成功！");
			conn = DriverManager.getConnection(dburl, dbusername, dbpassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn; // 返回Connection对象
	}

	/*
	 * 获取某个序列的下一个值
	 */
	public static long getTableSeqValue(Connection conn, String seqName) {
		long seqValue = 0;
		String getSql = "select " + seqName + ".nextval from dual";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(getSql);
			if (rs.next()) {
				seqValue = rs.getLong(1);
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

		return seqValue;
	}

	/**
	 * 
	 * @param taskCount
	 *            插入任务表数据条数
	 */
	public static void process1(int taskCount) {
		//
		Connection conn = getConnection();
		PreparedStatement taskSqlPS = null;
		PreparedStatement areaSqlPS = null;
		PreparedStatement itemSqlPS = null;
		PreparedStatement userSqlPS = null;

		try {
			// 关闭自动提交
			conn.setAutoCommit(false);
			// 执行资源初始化
			/*
			 * 参数 2 ：任务名称 '任务测试01'
			 */
			String taskSql = "insert into st_pi_task (ST_PI_TASKID, DESCRIPTION, ORGID, SITEID, HASLD, NO, TASK_NAME, BEGIN_TIME, END_TIME, POST_NAME, PROFESSIONAL, IN_PLACE_RATE, NORM_AREA, REAL_AREA, NORM_POINT, REAL_POINT, PI_RATE, MISSED_POINT, MISSED_RATE, TYPE, STATUS, IS_ASSESSMENT, REASON, ST_PI_TASKNUM, TASK_UP_TIME, IGNORE_POINT, SHIFTVALUE, ABNORMAL_POINT, ST_PI_TASKCFGID, SHIFT, ISREPLACE, REPLACEPER, DOWNLOADER, MISSREASON, ST_PI_TASKDATETIMEID, TASK_DOWN_TIME) "
					+ "values (?, null, 'SNPDP', 'NPP', 0, 'RW01', ?, to_date('01-11-2016 06:15:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-11-2016 22:45:00', 'dd-mm-yyyy hh24:mi:ss'), '电气岗位01', '电气', null, null, null, null, null, null, null, null, '点检', '未下载任务', 1, null, '1828', null, null, null, null, 10, null, 0, null, null, null, 101, null)";
			taskSqlPS = conn.prepareStatement(taskSql);
			/*
			 * 参数 1：区域ID 
			 * 参数 2：区域名称 #1浆液循环泵房"+a+"(每班_1) 
			 * 参数 3：区域编号 'QY0"+a+"' 
			 * 参数 4：任务表关联字段 参数 5：射频卡编号 '1000000"+a+"'
			 */
			String areaSql = "insert into st_pi_task_area (ST_PI_TASK_AREAID, DESCRIPTION, ORGID, SITEID, HASLD, NO, PROFESSIONAL, ST_PI_TASKID, SEQ, RFID_CODE, RFID_NO, ST_PI_TASK_AREANUM, TYPE, SCANTIME, SCANENDTIME, STATUS)"
					+ " values (?, ?, 'SNPDP', 'NPP', 0, ?, '电气', ?, 1,? , null, '6880', '点检', null, null, '未到位')";
			areaSqlPS = conn.prepareStatement(areaSql);
			/*
			 * 参数 1 ：项目编号 'XM0"+b+"' 
			 * 参数 2 ：设备编码 'SB0"+b+"' 
			 * 参数 3 ：设备名称 '#1汽轮机本体"+b+"' 
			 * 参数 4 ：任务表关联字段 
			 * 参数 5 ：区域关联ID
			 */
			String itemSql = "insert into st_pi_task_item (ST_PI_TASK_ITEMID, DESCRIPTION, ORGID, SITEID, HASLD, NO, DEVICE_NO, DEVICE_NAME, LOCATION, DEVICE_PART_NAME, POINT_TYPE, POINT_UNIT, HIGHER_LIMIT, LOWER_LIMIT, ON_OFF_POTIN, CHECK_METHOD, SHAKE_TYPE, SEQ, POINT_NORM, PI_TASK_ITEM_RESULT, IS_EXCESSIVE, PI_ITEM_DESCRIPTION, TYPE, ST_PI_TASK_ITEMNUM, ST_PI_TASKID, DOUBLERET, CHECK_ON_OFF, REMARK, ISPCHECK, ST_PI_TASK_AREAID, PI_TASK_ITEM_USER, ISNORMAL, NOTE, PI_AMEND_USER, PI_TASK_ITEM_LJREASON,  CHECK_TIME, ISCHECK, TICKETID, WONUM)"
					+ " values (st_pi_task_itemidseq.nextval, null, 'SNPDP', 'NPP', 0, ?, ?, ?, null, '轴承', 'CZ', 'mm', 0.050, 0.000, '运行', '测振', 'V', null, '小于0.05mm', null, 0, null, '点检', '63130', ?, null, null, null, 0,?, null, 1, null, null, null,  null, 0, null, null)";
			itemSqlPS = conn.prepareStatement(itemSql);
			/*
			 * 参数 1 ：任务关联ID
			 */
			String userSql = "insert into st_pi_task_user (ST_PI_TASK_USERID, DESCRIPTION, ORGID, SITEID, HASLD, PERSONID, ISDOWNLOAD_USER, ST_PI_TASKID)"
					+ " values (St_Pi_Task_Useridseq.Nextval, null, 'SNPDP', 'NPP', 0, 'CHENGYONG', 0, ?)";
			userSqlPS = conn.prepareStatement(userSql);

			// 执行数据插入
			long nextTaskID = 0;
			long nextAreaID = 0;
			for (int i = 0; i < taskCount; i++) {
				System.out.println("----------------------开始插入第" + i + "条任务！------------------------");
				// 获取任务ID
				nextTaskID = getTableSeqValue(conn, "ST_PI_TASKIDSEQ");
				// 设置任务ID参数
				taskSqlPS.setLong(1, nextTaskID);
				taskSqlPS.setString(2, "任务测试"+i);
				taskSqlPS.addBatch();
				// 插入区域 每个任务下有10个区域
				for (int j = 1; j <= 10; j++) {
					nextAreaID = getTableSeqValue(conn, "ST_PI_TASK_AREAIDSEQ");
					areaSqlPS.setLong(1, nextAreaID);
					areaSqlPS.setString(2, "#1浆液循环泵房" + j + "(每班_1)");
					areaSqlPS.setString(3, "QY0" + j + "");
					areaSqlPS.setLong(4, nextTaskID);
					areaSqlPS.setString(5, "1000000" + j + "");
					areaSqlPS.addBatch();
					// 插入区域对应的项目 每个区域下有 10个项目
					for (int k = 1; k <= 10; k++) {
						itemSqlPS.setString(1, "XM0" + k + "");
						itemSqlPS.setString(2, "SB0" + k + "");
						itemSqlPS.setString(3, "#1汽轮机本体" + k);
						itemSqlPS.setLong(4, nextTaskID);
						itemSqlPS.setLong(5, nextAreaID);
						itemSqlPS.addBatch();
					}

				}
				// 插入任务人员
				for (int j = 0; j < 10; j++) {
					userSqlPS.setLong(1, nextTaskID);
					userSqlPS.addBatch();
				}
				// 判断任务条数 每 10000 条执行一次插入处理
				if (i % 1000 == 0) {
					taskSqlPS.executeBatch();
					areaSqlPS.executeBatch();
					itemSqlPS.executeBatch();
					userSqlPS.executeBatch();
					conn.commit();
				}
			}
			// 插入最后不足一万条的记录
			taskSqlPS.executeBatch();
			areaSqlPS.executeBatch();
			itemSqlPS.executeBatch();
			userSqlPS.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (userSqlPS != null)
					userSqlPS.close();
				if (itemSqlPS != null)
					itemSqlPS.close();
				if (areaSqlPS != null)
					areaSqlPS.close();
				if (taskSqlPS != null)
					taskSqlPS.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * 计算时间差
	 * @return
	 */
	public static String timeCount(Date startDate,Date endDate){
		   long l=endDate.getTime()-startDate.getTime();
		   long day=l/(24*60*60*1000);
		   long hour=(l/(60*60*1000)-day*24);
		   long min=((l/(60*1000))-day*24*60-hour*60);
		   long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		   return ""+day+"天"+hour+"小时"+min+"分"+s+"秒";
	}
	public static void main(String[] args) {
		
		Date startDate = new Date();
		Test.process1(50000);
		Date endDate = new Date();
		
		System.out.println("任务执行结束，总计耗时---"+timeCount(startDate,endDate)+"--------------------------------------");
	}

}