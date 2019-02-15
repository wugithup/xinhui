package com.shuto.mam.app.stpi.impdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import psdi.server.MXServer;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

public class ImpDataUtil {
	private static MXLogger log = MXLoggerFactory.getLogger("maximo.stpi.importdata");

	public static Connection getMaximoDBConnection() {
		Connection connection = null;
		try {
			MXServer mxServer = MXServer.getMXServer();
			String dbdriver = mxServer.getProperty("mxe.db.driver");
			String dburl = mxServer.getProperty("mxe.db.url");
			String dbusername = mxServer.getProperty("mxe.db.user");
			String dbpassword = mxServer.getProperty("mxe.db.password");
			log.info("连接数据库：" + dburl + " " + dbusername);
			// 连接数据库
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			connection = DriverManager.getConnection("jdbc:oracle:thin:@10.59.3.75:1521/ycmaxdb", "maximo","maximo");
			Class.forName(dbdriver);
			connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
			connection.setAutoCommit(false);
			log.info("获取Maximo数据库连接成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * @use 获得表的autokey
	 * @param tableName
	 * @param attr
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static String getNextNumber(Connection conn, String autokeyname) throws SQLException {
		Statement pr = conn.createStatement();
		String sql = "select seed from autokey where autokeyname ='" + autokeyname + "'";
		ResultSet rs = pr.executeQuery(sql);
		int num = 0;

		if (rs.next()) {
			num = rs.getInt(1) + 1;
		}
		rs.close();
		String sql2 = "update autokey set seed=" + num + " where autokeyname ='" + autokeyname + "'";
		pr.execute(sql2);
		pr.close();
		return String.valueOf(num);
	}

	/**
	 * 得到索引值
	 * 
	 * @param sequenName
	 * @return
	 */
	public static long getNextSequence(Connection conn, String sequenName) {
		long seqValue = 0;
		String getSql = "select " + sequenName + ".nextval from dual";
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
}
