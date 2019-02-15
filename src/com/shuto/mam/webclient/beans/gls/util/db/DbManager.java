package com.shuto.mam.webclient.beans.gls.util.db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psdi.server.MXServer;

public class DbManager {
	private Connection con;

	/**
	 * 查询SQl遍历结果集
	 * 
	 * @param sql
	 *            执行的Sql
	 * @return list 循环查询出的结果
	 * @throws RemoteException 
	 */
	public List<Map<String, Object>> queryBySql(String sql) throws RemoteException {
		con = MXServer.getMXServer().getDBManager().getSequenceConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Statement pr=null;
		ResultSet rs=null;
		try {
			 pr = con.createStatement();
			 rs = pr.executeQuery(sql);
			// 获得列集
			ResultSetMetaData rsm = rs.getMetaData();
			int countCol = rsm.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= countCol; i++) {
					// 如果查询的结果为null的时候直接替换成空值
					map.put(rsm.getColumnName(i).toUpperCase(), rs
							.getObject(rsm.getColumnName(i)) == null ? "" : rs
							.getObject(rsm.getColumnName(i)));
				}
				list.add(map);
			}
			rs.close();
			pr.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null ){
				try {
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pr!=null){
			try {
					pr.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			}
		}
		return list;
	}
/**
 * 执行增删改操作
 * @param list_ExecuteBySql
 * @return 执行是否成功
 */
	public boolean ExecuteBySql(List<String> list_ExecuteBySql) {
		try {
			con = MXServer.getMXServer().getDBManager().getSequenceConnection();
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		Statement st = null;
		try {
			st = con.createStatement();
			// 保存当前自动提交模式
			boolean autoCommit = con.getAutoCommit();
			// 设置提交模式为手动提交
			con.setAutoCommit(false);
			for (int i = 0; i < list_ExecuteBySql.size(); i++) {
//				st.executeUpdate(list_ExecuteBySql.get(i));
				st.addBatch(list_ExecuteBySql.get(i));
			}
			st.executeBatch();
			con.commit();
			// 将提交模式设置成默认的提交模式
			con.setAutoCommit(autoCommit);

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}finally{
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	/**
	 * 查询SQl遍历结果集,查询的值全部为String类型
	 * 
	 * @param sql
	 *            执行的Sql
	 * @return list 循环查询出的结果
	 * @throws RemoteException 
	 */
	public List<Map<String, String>> queryBySqlStr(String sql) throws RemoteException {
		con = MXServer.getMXServer().getDBManager().getSequenceConnection();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Statement pr = null;
		ResultSet rs = null;
		try {
			 pr = con.createStatement();
			 rs = pr.executeQuery(sql);
			// 获得列集
			ResultSetMetaData rsm = rs.getMetaData();
			int countCol = rsm.getColumnCount();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= countCol; i++) {
					// 如果查询的结果为null的时候直接替换成空值
					map.put(rsm.getColumnName(i).toUpperCase(), rs
							.getString(rsm.getColumnName(i)) == null ? "" : rs
							.getString(rsm.getColumnName(i)));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null ){
				try {
						rs.close();
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pr!=null){
			try {
					pr.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			}
		}
		return list;
	}
	
	/**
	 * 增删改执行方法
	 * @param sqls sql集合
	 * @return
	 * @throws SQLException
	 * @throws RemoteException 
	 */
	public int update(List<String> sqls) throws SQLException, RemoteException{
		int result=0;
		con =MXServer.getMXServer().getDBManager().getSequenceConnection();
		Statement sta=con.createStatement();
		for(int i=0;i<sqls.size();i++){
			String sql=sqls.get(i);
			sta.addBatch(sql);
		}
		sta.executeBatch();
		con.commit();
		result=1;
		return result;
	} 
}
