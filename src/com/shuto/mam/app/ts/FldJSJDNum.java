package com.shuto.mam.app.ts;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Calendar;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXException;

/**  
* com.shuto.mam.app.ts.FldJSJDNum 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-13 下午3:49:44
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldJSJDNum extends MboValueAdapter
{
  public FldJSJDNum(MboValue mbv)
    throws MXException
  {
    super(mbv);
  }

  public void initValue() throws MXException, RemoteException
  {
    super.initValue();
    if (!getMboValue().getMbo().toBeAdded()) {
      return;
    }

    int nextNum = 1;

    DecimalFormat mFormat = new DecimalFormat("00");
    Calendar cal = Calendar.getInstance();
    String year = mFormat.format(Double.valueOf(cal.get(1)));
    String month = mFormat.format(Double.valueOf(cal.get(2) + 1));
    String day = mFormat.format(Double.valueOf(cal.get(5)));

    String prefix = "Q/CRPFY-JSJD-" + year + "-" + month + day + "-";
    System.out.print("+++++++++++++++++++++++prefix:" + prefix);

    ConnectionKey conKey = null;
    Connection con = null;
    Statement stmt = null;
    String sql = "";
    ResultSet rs = null;
    try {
      conKey = new ConnectionKey(MXServer.getMXServer().getSystemUserInfo());
      con = MXServer.getMXServer().getDBManager().getConnection(conKey);
      stmt = con.createStatement();
      stmt.setQueryTimeout(10);
      sql = "select max(substr(jsjdnum,24,4)) as curmaxnum from TECHSUPERVISION where jsjdnum like '" + prefix + 
        "%'";
      rs = stmt.executeQuery(sql);
      while (rs.next())
        if (Integer.valueOf(rs.getInt("curmaxnum")) != null)
          nextNum = rs.getInt("curmaxnum") + 1;
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }
        if (con != null) {
          con.close();
          con = null;
        }
        if (rs != null) {
          rs.close();
          rs = null;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

      getMboValue().setValue(prefix + nextNum, 11L);
    }
  }
}