package com.shuto.mam.expand.oracle;

import com.shuto.mam.expand.logger.PrintLogs;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import oracle.jdbc.OracleDriver;
import oracle.sql.CLOB;

public class OracleSqlDetabese implements SqlFormat {
	private PreparedStatement stmt = null;
	private PrintLogs logger = new PrintLogs();
	private Connection con = null;
	private ResultSetMetaData rsmd;
	private ResultSet r;
	private SqlFormat sqlFormat;
	private String sql;
	private String tableRoot;
	private String where;
	private Statement st;
	private int rowCount;
	private int columnCount;
	private HashMap<String, String> columnMap;
	private HashMap<String, String> defuorutoFinalMap;
	private String orderBy;

	public OracleSqlDetabese(Connection con) {
		this.sqlFormat = this;
		this.sql = null;
		this.tableRoot = null;
		this.where = null;

		this.rowCount = 0;
		this.columnCount = 0;
		this.columnMap = new HashMap();

		this.defuorutoFinalMap = new HashMap();
		this.orderBy = null;

		this.con = con;
	}

	public OracleSqlDetabese(OracleSqlDetabese osd) {
		this.sqlFormat = this;
		this.sql = null;
		this.tableRoot = null;
		this.where = null;

		this.rowCount = 0;
		this.columnCount = 0;
		this.columnMap = new HashMap();

		this.defuorutoFinalMap = new HashMap();
		this.orderBy = null;

		this.con = osd.getConnection();
	}

	public OracleSqlDetabese(String url, String user, String password, String driver) {
		this.sqlFormat = this;
		this.sql = null;
		this.tableRoot = null;
		this.where = null;

		this.rowCount = 0;
		this.columnCount = 0;
		this.columnMap = new HashMap();

		this.defuorutoFinalMap = new HashMap();
		this.orderBy = null;
		try {
			this.logger.debug("正在试图加载驱动程序 " + driver);
			Class.forName(driver);
			this.logger.debug("驱动程序已加载");

			this.logger.debug("url=" + url);
			this.logger.debug("user=" + user);
			this.logger.debug("password=" + password);
			this.logger.debug("正在试图连接数据库--------");

			DriverManager.registerDriver(new OracleDriver());
			this.con = DriverManager.getConnection(url, user, password);

			this.con.setAutoCommit(false);
			this.logger.debug("成功連接至數據庫!");
		} catch (ClassNotFoundException e) {
			this.logger.error("Error 加載驅動" + driver + "失敗...", e);
		} catch (SQLException e) {
			this.logger.error("Error 連接數據庫失敗....:", e);
		}
	}

	public OracleSqlDetabese(String url, String user, String password, String driver, String program)
			throws ClassNotFoundException, UnknownHostException {
		this.sqlFormat = this;
		this.sql = null;
		this.tableRoot = null;
		this.where = null;

		this.rowCount = 0;
		this.columnCount = 0;
		this.columnMap = new HashMap();

		this.defuorutoFinalMap = new HashMap();
		this.orderBy = null;
		try {
			this.logger.debug("正在试图加载驱动程序 " + driver);
			Class.forName(driver);
			this.logger.debug("驱动程序已加载");

			this.logger.debug("url=" + url);
			this.logger.debug("user=" + user);
			this.logger.debug("password=" + password);
			this.logger.debug("正在试图连接数据库--------");
			Properties props = new Properties();
			props.setProperty("password", password);
			props.setProperty("user", user);
			props.put("v$session.osuser", System.getProperty("user.name").toString());
			try {
				String hostName = InetAddress.getLocalHost().getCanonicalHostName();
				props.put("v$session.machine", hostName);
			} catch (UnknownHostException hostName) {
			}
			props.put("v$session.program", program);
			DriverManager.registerDriver(new OracleDriver());
			this.con = DriverManager.getConnection(url, props);
			this.con.setAutoCommit(false);

			this.con.setAutoCommit(false);
			this.logger.debug("成功連接至數據庫!");
		} catch (ClassNotFoundException e) {
			this.logger.error("Error 加載驅動" + driver + "失敗...", e);
		} catch (SQLException e) {
			this.logger.error("Error 連接數據庫失敗....:", e);
		}
	}

	public PreparedStatement prepareStatement() throws SQLException {
		try {
			this.stmt = this.con.prepareStatement(this.sql);
		} catch (SQLException e) {
			this.logger.error("con.prepareStatement Error!", e);
			throw new SQLException(e);
		}
		return this.stmt;
	}

	public ResultSet executeQueryPreparedStatement() throws SQLException {
		try {
			this.r = this.stmt.executeQuery();
		} catch (SQLException e) {
			this.logger.error("con.executeQueryPreparedStatement Error!", e);
			throw new SQLException(e);
		}
		return this.r;
	}

	public ResultSet executeQueryUpdate() throws SQLException {
		String[] name = getColumnName();
		format(name);
		close();

		this.st = this.con.createStatement(1005, 1008);
		this.r = this.st.executeQuery(this.sql);

		this.rsmd = this.r.getMetaData();
		this.columnCount = this.rsmd.getColumnCount();

		return this.r;
	}

	public ResultSet executeQuery(int kasoru, int kyoka) throws SQLException {
		close();

		this.st = this.con.createStatement(kasoru, kyoka);
		this.r = this.st.executeQuery(this.sql);

		this.rsmd = this.r.getMetaData();
		this.columnCount = this.rsmd.getColumnCount();
		return this.r;
	}

	public ResultSet executeQuery() throws SQLException {
		try {
			close();

			this.st = this.con.createStatement();

			this.st = this.con.createStatement(1005, 1007);
			this.r = this.st.executeQuery(this.sql);
			this.r.last();
			this.rowCount = this.r.getRow();

			this.r.beforeFirst();
			this.rsmd = this.r.getMetaData();
			this.columnCount = this.rsmd.getColumnCount();
		} catch (SQLException e) {
			this.logger.error("executeQuery.sql=" + this.sql, e);
			throw new SQLException(e);
		}
		return this.r;
	}

	public String[] getColumnName() throws SQLException {
		String[] columnName = new String[this.columnCount];
		for (int i = 0; i < this.columnCount; ++i) {
			columnName[i] = this.rsmd.getColumnName(i + 1);
			this.columnMap.put(columnName[i].toUpperCase(), columnName[i].toUpperCase());
		}
		return columnName;
	}

	public Set<String> getColumnYuukou() {
		return this.columnMap.keySet();
	}

	public ResultSet getResultSet() {
		return this.r;
	}

	public int getColumnCount() {
		return this.columnCount;
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public SqlFormat setTableRoot(String tableRoot) {
		this.tableRoot = tableRoot;
		return this.sqlFormat;
	}

	public void setTableRootInsert(String tableRoot) {
		try {
			this.tableRoot = tableRoot;
			setWhere("rownum=0");
			format();
			close();
			executeQuery();
			getColumnName();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
			this.logger.error("獲取列名失敗!", e);
		}
	}

	public void setDoutouWhere(String column, String atai) {
		this.where = column + " ='" + atai + "' ";
	}

	public void addDoutouWhere(String column, String atai) {
		if (this.where == null)
			this.where = column + " ='" + atai + "' ";
	}

	public void format(String[] column) {
		String[] arrayOfString;
		String columns = "";
		int j = (arrayOfString = column).length;
		for (int i = 0; i < j; ++i) {
			String string = arrayOfString[i];
			if (!(columns.isEmpty()))
				columns = columns + ",";

			columns = columns + string;
		}
		this.sql = "select " + columns + " from \"" + this.tableRoot + "\"";
		if (this.where != null) {
			OracleSqlDetabese tmp128_127 = this;
			tmp128_127.sql = tmp128_127.sql + " where " + this.where;
		}
		if (this.orderBy != null) {
			OracleSqlDetabese tmp170_169 = this;
			tmp170_169.sql = tmp170_169.sql + " order by " + this.orderBy;
		}
	}

	public void format() {
		this.sql = "select * from \"" + this.tableRoot + "\"";
		if (this.where != null) {
			OracleSqlDetabese tmp38_37 = this;
			tmp38_37.sql = tmp38_37.sql + " where " + this.where;
		}
		if (this.orderBy != null) {
			OracleSqlDetabese tmp80_79 = this;
			tmp80_79.sql = tmp80_79.sql + " order by " + this.orderBy;
		}
	}

	public void formatInsert() {
		Set columns = this.columnMap.keySet();
		String fields = "";
		String gimonnfu = "";
		int size = columns.size();
		int i = 0;
		for (Iterator localIterator = columns.iterator(); localIterator.hasNext();) {
			String column = (String) localIterator.next();
			fields = fields + column;
			gimonnfu = gimonnfu + "?";
			if (++i == size)
//				break label143;
			fields = fields + ",";
			label143: gimonnfu = gimonnfu + ",";
		}

		columns = this.defuorutoFinalMap.keySet();
		this.logger.debug(" size=" + this.defuorutoFinalMap.size());
		for (Iterator localIterator = columns.iterator(); localIterator.hasNext();) {
			String key = (String) localIterator.next();
			fields = fields + "," + key;
			gimonnfu = gimonnfu + "," + ((String) this.defuorutoFinalMap.get(key));
			this.logger.debug(key + "====" + ((String) this.defuorutoFinalMap.get(key)));
		}
		this.sql = "insert into " + this.tableRoot + " (" + fields + ") values(" + gimonnfu + ")";
		this.logger.debug(this.sql);
	}

	public void formatInsert(String[] musi) {
		String[] arrayOfString;
		int j = (arrayOfString = musi).length;
		for (int i = 0; i < j; ++i) {
			String string = arrayOfString[i];
			this.columnMap.remove(string.toUpperCase());
		}
		formatInsert();
	}

	public void setInsertDefuorudoFinalColumn(String field, String value) {
		this.defuorutoFinalMap.put(field, value);
		this.columnMap.remove(field.toUpperCase());
	}

	public int getInsertColumnIndex(String field) {
		Set columns = this.columnMap.keySet();
		int i = 0;
		for (Iterator localIterator = columns.iterator(); localIterator.hasNext();) {
			String column = (String) localIterator.next();
			++i;
			if (!(column.equalsIgnoreCase(field)))
//				break label47;
			label47: return i;
		}

		return -1;
	}

	public Connection getConnection() {
		return this.con;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void closeAllRollBack() {
		try {
			if (this.r != null)
				this.r.close();
			if (this.st != null)
				this.st.close();
			if (this.stmt != null)
				this.stmt.close();
			if (this.con == null)
				return;
			this.con.rollback();
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() throws SQLException {
		if (this.r != null)
			this.r.close();
		if (this.st != null)
			this.st.close();
		if (this.stmt != null)
			this.stmt.close();
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public boolean update(Connection con, String sql) {
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.execute();
			pst.close();
		} catch (Exception e) {
			this.logger.error("update Error sql=" + sql);
			this.logger.error("update Error", e);
			return false;
		}
		return true;
	}

	public boolean update(String sql) {
		PreparedStatement pst;
		try {
			pst = this.con.prepareStatement(sql);
			pst.execute();
			pst.close();
		} catch (Exception e) {
			this.logger.error("update Error sql=" + sql);
			this.logger.error("update Error", e);
			return false;
		}
		return true;
	}

	public String getClobToString(String columnLabel) throws IOException, SQLException {
		Clob clob = this.r.getClob(columnLabel);

		return clob.getSubString(1L, (int) clob.length());
	}

	public String clobToString(CLOB clob) throws IOException, SQLException {
		Reader reader;
		try {
			reader = clob.getCharacterStream();
			CharArrayWriter writer = new CharArrayWriter();
			int i = -1;

			while ((i = reader.read()) != -1) {
				writer.write(i);
			}

			return new String(writer.toCharArray().toString().getBytes(), "UTF-8");
		} catch (Exception ex) {
			this.logger.error("SimpleMessageListener.onMessage(): got exception: ", ex);
			throw new RuntimeException(ex);
		}
	}

	public String ClobToString(CLOB clob) throws SQLException, IOException {
		Reader reader = clob.getCharacterStream();
		CharArrayWriter writer = new CharArrayWriter();
		int i = -1;

		while ((i = reader.read()) != -1)
			writer.write(i);

		return new String(writer.toCharArray());
	}

	public String formatSqlDate(Date date, String pattern) {
		String pat = pattern;
		if (pattern == null)
			pat = "yyyy-MM-dd HH:mm:ss";
		String dateTime = MessageFormat.format("{0,date," + pat + "}", new Object[] { date });
		return dateTime;
	}

	public boolean next() throws SQLException {
		return this.r.next();
	}

	public long getSequenceNext(String seqName) {
		long seq = 0L;
		String sql = "select " + seqName + ".NEXTVAL from dual";
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = this.con.createStatement();
			rs = statement.executeQuery(sql);
			rs.next();
			seq = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();

			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			if (statement == null)
//				break label222;
			try {
				statement.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}

		label222: return seq;
	}

	public void testReadData(int level) {
		Statement st;
		try {
			st = this.con.createStatement();
			st = this.con.createStatement(1005, 1007);
			ResultSet r = st.executeQuery(
					"SELECT ROWNUM,UTL_INADDR.get_host_name() ,sysdate,user,dbms_random.random,SYS_CONTEXT('USERENV','NLS_DATE_FORMAT') nls_date_format,SYS_CONTEXT('USERENV','NLS_DATE_LANGUAGE') nls_date_language, SYS_CONTEXT('USERENV','NLS_SORT') nls_sort, SYS_CONTEXT('USERENV','NLS_TERRITORY') nls_territory, SYS_CONTEXT('USERENV','NLS_CURRENCY') nls_currency, SYS_CONTEXT('USERENV','NLS_CALENDAR') nls_calendar  FROM dual CONNECT BY LEVEL <= 1");

			r.last();
			this.logger.debug("行数" + r.getRow());
			r.beforeFirst();
			printResultSet(r, level);
			r = st.executeQuery(
					"SELECT SCHEMANAME,username,sid,program FROM V$SESSION WHERE AUDSID = USERENV('SESSIONID') ");
			printResultSet(r, level);
			st.close();
			this.logger.debug("查询结束!");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.logger.error("ReadDate Error ", ex);
		}
	}

	public void setLevel(int level) {
		this.logger.setLevel(level);
	}

	public void printResultSet(ResultSet r, int level) throws SQLException {
		ResultSetMetaData rsmd = r.getMetaData();
		int column = rsmd.getColumnCount();
		this.logger.debug("列数为" + column, level);
		String[] strs = new String[column];
		for (int i = 0; i < rsmd.getColumnCount(); ++i) {
			strs[i] = rsmd.getColumnName(i + 1);
		}
		while (r.next())
			for (int i = 0; i < column; ++i) {
				String field = rsmd.getColumnName(i + 1);
				if (rsmd.getColumnType(i + 1) == 91) {
					Timestamp time = r.getTimestamp(i + 1);
					this.logger.debug(field + "(TimeStamp)= " + time, level);
					Date date = r.getDate(i + 1);
					this.logger.debug(field + "(Date)= " + date, level);

					String value = r.getString(i + 1);
					this.logger.debug(field + "(String)= " + value, level);
				} else {
					String value = r.getString(i + 1);

					this.logger.debug(field + " = " + value, level);
				}
			}

		r.close();
	}
}