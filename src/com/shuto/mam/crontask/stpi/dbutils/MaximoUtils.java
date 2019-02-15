package com.shuto.mam.crontask.stpi.dbutils;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psdi.server.MXServer;

public class MaximoUtils {
	private Connection connection;
	//用于获取公共库序列
	private Connection pubconn;
	private Statement pubst;
	private ResultSet pubrs;

	private StringBuffer sqlLog = new StringBuffer();
	/**
	 * 获得maximo数据库连接
	 * @return
	 */
	public Connection getMaximoConn() {
	    try {
	    	connection = MXServer.getMXServer().getDBManager().getSequenceConnection();
	    	connection.setAutoCommit(false);
	    } catch (RemoteException e) {
	    	e.printStackTrace();
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	    return connection;
	}
	
	/**
	 * 释放公共数据库连接
	 */
	public void closePub() {
		try {
			if (pubst != null) {
				pubst.close();
			}
			if (pubconn != null) {
				pubconn.close();
			}
			System.out.println("Close public connection....");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭连接
	 * @param ps
	 */
	public void closePs() {
//		try {
//			if(pstmt!=null&&!pstmt.isClosed()){
//				pstmt.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	  }
	/**
	 * 设置自动提交
	 * @param flag
	 */
	public void setAutoCommit(boolean flag) {
		try {
			if (connection != null) {
				connection.setAutoCommit(flag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到SQL日志
	 */
	public String getSqlLog(){
		return sqlLog.toString();
	}
	
	
	/**
	 * 回滚
	 */
	public void rollback(){
		try{
			 if (connection != null) {
				 connection.rollback();
			 }
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * 提交
	 */
	public void commit(){
		try{
			if (connection != null) {
				 connection.commit();
			 }
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * 增加、删除、改
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<Object>params)throws SQLException{
		boolean flag = false;
		int result = -1;
//		pstmt = connection.prepareStatement(sql);
		LoggableStatement pstmt = new LoggableStatement(connection,sql);
		int index = 1;
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		sqlLog.append(((LoggableStatement)pstmt).getQueryString()+";");
		result = pstmt.executeUpdate();
		pstmt.close();
		flag = result > 0 ? true : false;
		return flag;
	}
	
	/**
	 * 批量增加、删除、改
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	
	/**
	 * 加载SQL
	 */
	public PreparedStatement LoggableStatement(String sql) throws SQLException {
//		pstmt = connection.prepareStatement(sql);
		return new LoggableStatement(connection,sql);
	}
	/**
	 * 加载参数
	 * @param params
	 * @throws SQLException
	 */
	public void updateBatchByPreparedStatement(PreparedStatement pstmt ,List<Object>params)throws SQLException{
		int index = 1;
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		sqlLog.append(((LoggableStatement)pstmt).getQueryString()+";\n");
		pstmt.addBatch();
	}
	/**
	 * 关闭PreparedStatement对象 
	 * @param pstmt
	 */
	public void closePstmt(PreparedStatement pstmt) {
        if (pstmt != null) {  
            try {
            	pstmt.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }
	}
	
	/**
	 * 查询单条记录
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
		Map<String, Object> map = new HashMap<String, Object>();
		int index  = 1;
//		pstmt = connection.prepareStatement(sql);
		PreparedStatement pstmt = new LoggableStatement(connection,sql);
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		sqlLog.append(((LoggableStatement)pstmt).getQueryString()+";\n");
		ResultSet resultSet = pstmt.executeQuery();//返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while(resultSet.next()){
			for(int i=0; i<col_len; i++ ){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		resultSet.close();
		pstmt.close();
		return map;
	}
	
	/**查询多条记录
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
//		pstmt = connection.prepareStatement(sql);
		PreparedStatement pstmt = new LoggableStatement(connection,sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		sqlLog.append(((LoggableStatement)pstmt).getQueryString()+";\n");
		ResultSet resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while(resultSet.next()){
			Map<String, Object> map = new HashMap<String, Object>();
			for(int i=0; i<cols_len; i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		resultSet.close();
		pstmt.close();
		return list;
	}
	/**
	 * @use 获得表的autokey
	 * @param tableName
	 * @param attr
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public String getNextNumber(String autokeyname) throws SQLException {
//		Statement pr = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		Statement pr = connection.createStatement();
		String sql = "select seed from autokey where autokeyname ='" + autokeyname + "'";
		    ResultSet rs = pr.executeQuery(sql);
		    int num = 0;

		    if (rs.next()) {
		      num = rs.getInt(1) + 1;
		    }
		    rs.close();
		    String sql2 = "update autokey set seed=" + 
		      num + 
		      " where autokeyname ='" + autokeyname + "'";
		    pr.execute(sql2);
		    pr.close();
		    return String.valueOf(num);
		  }
	/**
	 * 得到sql是否有记录
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public boolean isHasRecord(String sql) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(sql);
		boolean flag = false;
		int rowCount = 0; 
		while(rs.next())
		{
			rowCount++;
		  }
	    if (rowCount>0) {
	    	flag = true;
	    }
	    rs.close();
	    st.close();
	    return flag;
	}
	
	/**
	 * 调用索引的暂停和重建 ，提高任务的插入速度
	 * @param isstop
	 * @param conn
	 */
	public void rebuidIndex(int isstop){
		  CallableStatement proc = null;
	      try {
			proc = connection.prepareCall("{ call indexbuild(?) }");
			proc.setInt(1, isstop);
			proc.execute() ; 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(proc!=null){
				try {
					proc.close() ;
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	/**
	 * 得到索引值
	 * @param sequenName
	 * @return
	 */
	public long getNextSequence(String sequenName){
		long seqValue = 0;
		String getSql = "select " + sequenName + ".nextval from dual";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
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
	 * 从公共库里获取序列值
	 * @param sequenName
	 * @return
	 */
	public long getPubNextSeq(String sequenName){
		long seqValue = 0;
		if (sequenName != null && !"".equals(sequenName)) {
			try {
				if (pubconn == null) {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					pubconn = DriverManager.getConnection("jdbc:oracle:thin:@10.61.16.54:1521/hneamdb", "maxpub", "maxpub");
					pubst = pubconn.createStatement();
					System.out.println("Create public connection....");
				}
				pubrs = pubst.executeQuery("select " + sequenName + ".nextval from dual");
				if (pubrs.next()) {
					seqValue = pubrs.getLong(1);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pubrs != null) {
						pubrs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return seqValue;
	}
	
	/**
	 * 得到站点名称
	 * @param siteid
	 * @return
	 */
	public String getSite(String siteid){
		String site = "";
		String getSql = "select description from site where siteid = '"+siteid+"'";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(getSql);
			if (rs.next()) {
				site = rs.getString(1);
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
		return site;
	}
}
