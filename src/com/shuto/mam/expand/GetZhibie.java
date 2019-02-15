package com.shuto.mam.expand;

import com.shuto.mam.expand.oracle.OracleSqlDetabese;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetZhibie {
	public OracleSqlDetabese osd = null;
	public Date startDate = null;
	public Date endDate = null;

	public GetZhibie(Connection con) throws SQLException {
		this.osd = new OracleSqlDetabese(con);
		this.osd.setLevel(7);

		this.osd.setTableRoot("INDICATOR_DBB");
		this.osd.setOrderBy("JIEZHITIME");
		this.osd.format();
		ResultSet rs = this.osd.executeQuery();
		if (rs.next())
			this.startDate = rs.getTimestamp("JIEZHITIME");

		rs.last();
		this.endDate = rs.getTimestamp("JIEZHITIME");
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public int getZhibie(Date date) throws SQLException {//2017-08-30 04:00:00  夜班
		long zqday = 1L;
		Calendar cS = Calendar.getInstance();
		cS.setTime(this.startDate);
		cS.set(11, 0);
		cS.set(12, 0);
		cS.set(14, 0);
		Calendar cE = Calendar.getInstance();
		cE.setTime(this.endDate);
		cE.add(5, 1);
		cE.set(11, 0);
		cE.set(12, 0);
		cE.set(14, 0);
		long nd = 86400000L;


		long diff = cE.getTimeInMillis() - cS.getTimeInMillis();
		long day = diff / nd;
		zqday = day;


		Calendar cD = Calendar.getInstance();
		cD.setTime(date);
		cD.add(5, 1);
		cD.set(11, 0);
		cD.set(12, 0);
		cD.set(14, 0);
		diff = cD.getTimeInMillis() - cS.getTimeInMillis();
		day = diff / nd;
		int addDay = (int) (0L - day / zqday * 10L);
		cD.setTime(date);
		cD.add(5, addDay);

		int zhibie = 0;

//		OracleSqlDetabese osd = new OracleSqlDetabese(this.osd);
		osd.setTableRoot("INDICATOR_DBB");
		osd.setWhere("jiezhitime>=to_date('" + new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(cD.getTime())
				+ "','yyyy-mm-dd hh24:mi')");
		osd.setOrderBy("jiezhitime");
		osd.format();
		ResultSet rs = osd.executeQuery();
		if (rs.next())
			zhibie = rs.getInt("ZHIBIE");

		osd.close();
		return zhibie;
	}
}