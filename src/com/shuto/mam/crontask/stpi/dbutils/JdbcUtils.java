package com.shuto.mam.crontask.stpi.dbutils;


import java.lang.reflect.Field;
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

import psdi.mbo.MboRemote;

public class JdbcUtils {
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	private StringBuffer sqlLog = new StringBuffer();
	public JdbcUtils() {
		
	}
	
	/**
	 * 获得数据库连接(中间库)
	 * @param dbMbo
	 * @return
	 */
	public Connection getConnection(MboRemote dbMbo){
		try {
			//得到数据库配置信息
			String dbdriver = dbMbo.getString("DBDRIVER");
			String dburl = dbMbo.getString("DBURL");
			String dbusername = dbMbo.getString("DBUSERNAME");
			String dbpassword = dbMbo.getString("DBPASSWORD");
			
//			String dbdriver = "oracle.jdbc.driver.OracleDriver";
//			String dburl = "jdbc:oracle:thin:@localhost:1521:orcl";
//			String dbusername = "maximo";
//			String dbpassword = "maximo";
//			System.out.println(dburl);
//			System.out.println(dbusername);
//			System.out.println(dbpassword);
			//连接数据库
			Class.forName(dbdriver);
//			System.out.println("数据库连接成功！");
			connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
			connection.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
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
		pstmt = new LoggableStatement(connection,sql);
		int index = 1;
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		sqlLog.append(((LoggableStatement)pstmt).getQueryString()+";");
//		System.out.println(((LoggableStatement)pstmt).getQueryString());
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		sqlLog.append(flag+"\n");
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
//		System.out.println(((LoggableStatement)pstmt).getQueryString());
		pstmt.addBatch();
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
		pstmt = new LoggableStatement(connection,sql);
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		sqlLog.append(((LoggableStatement)pstmt).getQueryString()+";\n");
		resultSet = pstmt.executeQuery();//返回查询结果
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
		pstmt = new LoggableStatement(connection,sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		sqlLog.append(((LoggableStatement)pstmt).getQueryString()+";\n");
		resultSet = pstmt.executeQuery();
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

		return list;
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
	
	/**通过反射机制查询单条记录
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls )throws Exception{
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData  = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while(resultSet.next()){
			//通过反射机制创建一个实例
			resultObject = cls.newInstance();
			for(int i = 0; i<cols_len; i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); //打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;

	}

	/**通过反射机制查询多条记录
	 * @param sql 
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMoreRefResult(String sql, List<Object> params,
			Class<T> cls )throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData  = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while(resultSet.next()){
			//通过反射机制创建一个实例
			T resultObject = cls.newInstance();
			for(int i = 0; i<cols_len; i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); //打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;
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
	 * 提交时会将日志清空，如需日志请提前获得
	 */
	public void commit(){
		try{
			if (connection != null) {
				 connection.commit();
			 }
			sqlLog.setLength(0);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量执行
	 * @throws SQLException 
	 */
	public int [] executeBatch() throws SQLException{
		return pstmt.executeBatch();
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
	 * @use 获得表的autokey
	 * @param tableName
	 * @param attr
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public String getNextNumber(String autokeyname) throws SQLException {
		Statement pr = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
		    pr.executeUpdate(sql2);
		    pr.close();
		    return String.valueOf(num);
		  }
	/** 
     * 关闭所有资源 
     */  
    public void closeAll() { 
    	 // 关闭Connection 对象  

        // 关闭结果集对象  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
  
        // 关闭PreparedStatement对象  
        if (pstmt != null) {  
            try {
            	pstmt.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
        
        if (connection != null) {  
            try {
            	connection.setAutoCommit(true);
            	connection.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }
 //       System.out.println("数据库连接已关闭！");  
    }  
	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection(null);

		/*******************增*********************/
//		String sql = "insert into xdj_line_user (line_user_id,line_id, user_id) values (1,?, ?)";
//		List<Object> params = new ArrayList<Object>();
//		params.add("1");
//		params.add("LIUHUI5");
//		try {
//			boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
//			System.out.println(flag);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		/*******************删*********************/
//		String sql = "delete from xdj_line_user where line_id = ?";
//		List<Object> params = new ArrayList<Object>();
//		params.add("2");
//		boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);

		/*******************改*********************/
//		String sql = "update xdj_line_user set user_id = ? where line_id = ? ";
//		List<Object> params = new ArrayList<Object>();
//		params.add("XIERENYAN");
//		params.add("1");
//		boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
//		System.out.println(flag);
//		
//		String sql2 = "update xdj_line_user set user_id = ? where line_id = ? ";
//		List<Object> params2 = new ArrayList<Object>();
//		params2.add("XIERENYAN2");
//		params2.add("2");
//		boolean flag2 = jdbcUtils.updateByPreparedStatement(sql2, params2);
//		System.out.println(flag2);
//
//		/*******************查*********************/
//		//不利用反射查询多个记录
//		String sql3 = "select * from xdj_line_user where user_id = ? ";
//		List<Object> params3 = new ArrayList<Object>();
//		params3.add("LIUHUI5");
//		List<Map<String, Object>> list = jdbcUtils.findModeResult(sql3, params3);
//		System.out.println(list);
//		
//		String sql4 = "select * from xdj_line_user where user_id = ? ";
//		List<Object> params4 = new ArrayList<Object>();
//		params4.add("LIUHUI5");
//		List<Map<String, Object>> list2 = jdbcUtils.findModeResult(sql4, params4);

		
		String sql = "update person set displayname2 = 'LIUHUI3' where personid = ?";
		PreparedStatement pstmt = jdbcUtils.LoggableStatement(sql);
		List<Object> params = new ArrayList<Object>();
		params.add("LIUHUI5");
		jdbcUtils.updateBatchByPreparedStatement(pstmt, params);
		int[] i =pstmt.executeBatch();
//		System.out.println(i.toString());
		jdbcUtils.commit();
		jdbcUtils.closeAll();
		
		
		//利用反射查询 单条记录
//		String sql = "select * from maxuser where personid = ? ";
//		List<Object> params = new ArrayList<Object>();
//		params.add("LIUHUI5");
//		UserInfo userInfo;
//		try {
//			userInfo = jdbcUtils.findSimpleRefResult(sql, params, UserInfo.class);
//			System.out.print(userInfo);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		System.out.println(jdbcUtils.getSqlLog());
	}

}