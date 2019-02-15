package com.shuto.mam.servlet.selecttemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

import com.shuto.mam.servlet.SelectTemplate;

public class SelectSqlList {

	private Connection conn = null;
	private ResultSet rs = null;
	private Statement st = null;
	private String sql = null;

	public SelectSqlList(String sql) {
		// TODO Auto-generated constructor stub
		this.sql = sql;
	}

	public String getString(String columnLabel) {
		String string = null;
		try {
			string = rs.getString(columnLabel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string == null ? "" : string;
	}

	public boolean next() {
		try {
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean executeQuery() {
		conn = SelectTemplate.getOraclConn();
		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			close();
			return false;
		}
		return true;
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}
	}

}
