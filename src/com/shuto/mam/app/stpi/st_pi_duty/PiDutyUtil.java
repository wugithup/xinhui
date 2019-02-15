package com.shuto.mam.app.stpi.st_pi_duty;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psdi.server.MXServer;
/**
 * 班值表应用工具类
 * @author xianwei
 *
 */
public class PiDutyUtil {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
/**
 * 获得巡检生成任务时的值别班次
 * @param begintime	任务开始时间
 * @param endtime	任务结束时间
 * @param siteid	站点
 * @param prof		专业
 * @return	map	shift--值别		shifttype---班次
 * @throws SQLException
 * @throws ParseException
 */
	public static Map<String, String> getShiftMap(String begintime,String endtime,String siteid,String prof,Connection conn)throws SQLException,ParseException{
		//根据站点及专业过滤班值表
		String sql = "select num,startdate,cycledays from dutyinfo where num =  (select  "
					+" ( case when "
					+" (select count(num) from dutyinfo where SITEID = '"+siteid+"' and PROFESSIONAL = '"+prof+"' and active = 1)>0  "
					+" then "
					+" (select num from dutyinfo where SITEID = '"+siteid+"' and PROFESSIONAL = '"+prof+"' and active = 1) "
					+" else "
					+" (select num from dutyinfo where SITEID = '"+siteid+"' and PROFESSIONAL is null and active = 1) "
					+" end "
					+" ) dutynum  "
					+" from dual)";
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		//返回 map
		Map<String, String> returnMap = new HashMap<String, String>();
		if(rs.next()){
			String num = rs.getString("num");//指标表编号
			Date startdate = rs.getDate("startdate");//值班开始时间
			int cycledays = rs.getInt("cycledays");//周期
			long days = getDays(dateFormat.format(startdate), begintime);//时间间隔天数
			//任务开始时间	与 班值表中对应
			Calendar c = Calendar.getInstance();
			c.setTime(startdate);
			c.add(Calendar.DAY_OF_MONTH, (int) (days%cycledays-1));
			Date beginC = sdf.parse(begintime);
			c.set(Calendar.HOUR_OF_DAY, beginC.getHours());
			c.set(Calendar.MINUTE, beginC.getMinutes());
			c.set(Calendar.SECOND, 0);
			//根据 num及班值开始时间查询  班次 和值别
			Statement shiftSta = conn.createStatement();
			String shiftSql = "select shift,shifttype from dutyconfig where parent = '"+num+"' and to_char(starttime,'yyyy-MM-dd hh24:mi:ss') = '"+sdf.format(c.getTime())+"' ";
			ResultSet shiftRs = shiftSta.executeQuery(shiftSql);
			if(shiftRs.next()){
				returnMap.put("shift", shiftRs.getString("shift"));
				returnMap.put("shifttype", shiftRs.getString("shifttype"));
			}
		}
		rs.close();
		stat.close();
		conn.close();
		return returnMap;
	}
/**
 * 获得启动任务是的时间分割的集合
 * @param beginTime	开始时间
 * @param endTime	结束时间
 * @param siteid	站点
 * @param zq		周期
 * @param prof 		专业
 * @return
 */
	public static List<Map<String, String>> getPeriodDateList(Date beginTime,Date endTime,String siteid,int zq,String prof,Connection conn) throws SQLException,ParseException{
		//查询出 根据		站点、专业	过滤出的班值信息
		String sql = "select SHIFTTYPE,to_char(STARTTIME ,'hh24:mi:ss') startt ,to_char(ENDTIME ,'hh24:mi:ss') endt  from DUTYCONFIG  "
					+" where parent = (select "
					+" ( case when  "
					+" (select count(num) from dutyinfo where SITEID = '"+siteid+"' and PROFESSIONAL = '"+prof+"' and active = 1)>0  "
					+" then "
					+" (select num from dutyinfo where SITEID = '"+siteid+"' and PROFESSIONAL = '"+prof+"' and active = 1) "
					+" else "
					+" (select num from dutyinfo where SITEID = '"+siteid+"' and PROFESSIONAL is null and active = 1) "
					+" end "
					+" ) dutynum  "
					+" from dual) "
					+" group by SHIFTTYPE,to_char(STARTTIME ,'hh24:mi:ss'),to_char(ENDTIME ,'hh24:mi:ss') "
					+" order by to_char(STARTTIME ,'hh24:mi:ss')  ";
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		//存放 班值开始结束时间信息
		List<Map<String, String>> tList = new ArrayList<Map<String, String>>();
		while(rs.next()){
			Map<String, String> map = new HashMap<String,String>();
			map.put("starttime", rs.getString("startt"));
			map.put("endtime", rs.getString("endt"));
			tList.add(map);
		}
		rs.close();
		stat.close();
//---------------------------------------------------------------------------------------------------------------
		if(tList.size()>0){
			Calendar beginC = Calendar.getInstance();//开始时间
			Calendar finalEndC = Calendar.getInstance();//结束时间
			beginC.setTime(beginTime);
			finalEndC.setTime(endTime);
			//返回值list  包含	map 开始 结束时间信息 
			List<Map<String, String>> returnList = new ArrayList<Map<String,String>>();
			//开始时间在结束时间之后
			while(beginC.before(finalEndC)){
				// 遍历 查询出的 集合
				for (int j = 0;j < tList.size();j++) {
					Map<String, String> m = tList.get(j);
					Date starttime = timeFormat.parse((String) m.get("starttime"));	//班值 开始时间点
					Date endtime = timeFormat.parse((String) m.get("endtime"));	//班值结束时间点
					beginC.set(Calendar.HOUR_OF_DAY, starttime.getHours());
					beginC.set(Calendar.MINUTE, starttime.getMinutes());
					//当前任务结束时间
					Calendar endC = Calendar.getInstance();
					endC.set(beginC.get(Calendar.YEAR), beginC.get(Calendar.MONTH), beginC.get(Calendar.DAY_OF_MONTH), endtime.getHours(), endtime.getMinutes());
					endC.set(Calendar.SECOND, 0);
					//若为集合中的最后一个  在表示结束在下一天
					if(j==tList.size()-1){
						endC.add(Calendar.DAY_OF_MONTH, 1);
					}
					//根据周期遍历
					for (int i = 0; i < zq; i++) {
						Map<String, String> dateMap = new HashMap<String,String>();
						dateMap.put("startDate", sdf.format(beginC.getTime()));
						dateMap.put("endDate", sdf.format(endC.getTime()));
						returnList.add(dateMap);
					}
				}
				beginC.add(Calendar.DAY_OF_MONTH, 1);
			}
			return returnList;
		}
		conn.close();
		return null;
	}
	/**
	 * 获取两个时间间隔
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static  long getDays(String startDate, String endDate) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(startDate);
			java.util.Date mydate = myFormatter.parse(endDate);
			day = (long)( mydate.getTime() -date.getTime()) / (24 * 60 * 60 * 1000) +1 ;
		} catch (Exception e) {
			return 0;
		}
		return day ;
	} 
	
	
	
	public static void main(String[] args) {
		try {
			Map<String, String> shiftMap = getShiftMap("2012-08-16 08:00:00", "2012-08-15 08:00:00", "XDJTEXT1", "asd",getOracleConn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		String begintime = "2013-01-20";
		Date startdate = new Date(113,0,10);
		System.out.println(startdate);
		long days = getDays( dateFormat.format(startdate),begintime);
		System.out.println(days);
		Calendar c = Calendar.getInstance();
		int cycledays = 7;
		c.setTime(startdate);
		System.out.println(dateFormat.format(c.getTime()));
		c.add(Calendar.DAY_OF_MONTH, (int) (days%cycledays-1));
		System.out.println(days%cycledays-1);
		System.out.println(dateFormat.format(c.getTime()));*/
/*		try {
			Date sDate =  new Date(23,1,1);
			Date eDate = new Date(23,1,4);
			List<Map<String, Date>> list = getPeriodDateList(sDate, eDate, "XDJTEXT1", 2, "阿化学");
			for (Map<String, Date> map : list) {
				System.out.println("-------------------------------------");
				System.out.println("start--"+sdf.format(map.get("startDate")) );
				System.out.println("end--"+sdf.format(map.get("endDate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 获得本地数据库连接		测试用
	 * @return
	 */
	public static Connection getOracleConn(){
		Connection conn=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","xdj","xdj");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 获得MAXIMO数据库连接
	 * @return
	 */
	public static Connection getMaximoConn() {
		Connection conn=null;
		try {
			conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

