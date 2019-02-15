package com.shuto.mam.webclient.beans.tz.hbsj;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class MzsjzAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    Calendar localCalendar = Calendar.getInstance();
    int j = localCalendar.get(1);
    int k = localCalendar.get(2) + 1;
    String str1 = getMbo().getUserInfo().getPersonId();

    MboRemote localMboRemote1 = getMbo();
    localMboRemote1.setValue("createby", str1, 2L);
    localMboRemote1.setValue("createdate", new Date(), 2L);
    localMboRemote1.setValue("year", j, 2L);
    localMboRemote1.setValue("month", k, 2L);
    String str2 = j + "年" + k + "月入炉煤煤质数据台帐";
    localMboRemote1.setValue("description", str2, 2L);

    int l = localCalendar.getActualMaximum(5);

    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("RLMTZCHILD");

    long l1 = localMboRemote1.getUniqueIDValue();

    for (int i1 = 0; i1 < l; ++i1) {
      localCalendar.set(5, i1 + 1);
      MboRemote localMboRemote2 = localMboSetRemote.addAtEnd();

      localMboRemote2.setValue("riqi", localCalendar.getTime(), 11L);

      localMboRemote2.setValue("parent", l1, 11L);
    }

    refreshTable();

    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    MboRemote localMboRemote1 = getMbo();
    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("RLMTZCHILD");
    MXServer localMXServer = MXServer.getMXServer();

    String str1 = localMXServer.getProperty("mxe.db.url");
    String str2 = localMXServer.getProperty("mxe.db.user");
    String str3 = localMXServer.getProperty("mxe.db.password");
    Connection localConnection = null;
    Statement localStatement = null;
    try {
      localConnection = DriverManager.getConnection(str1, str2, str3);
      localStatement = localConnection.createStatement();

      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      for (int i = 0; i < localMboSetRemote.count(); ++i) {
        MboRemote localMboRemote2 = localMboSetRemote.getMbo(i);
        Date localDate = localMboRemote2.getDate("riqi");
        String str4 = localSimpleDateFormat.format(localDate);
        String str5 = "select sum(SL *DWRZH1 ) /  sum(sl) as pjrz from rlmtzview where  substr(RLNUM,3,8) ='" + str4 + "'";

        String str6 = "select round(((100 - 全水分) / (100 - 水分)) * 全硫, 3) pjrz1 from (select round(F9 / D9, 3) 全水分,       round(H9 / D9, 3) 水分,  round(P9 / D9, 3) 全硫  from (select sum(上煤量) D9,            sum(上煤量 * 全水分) F9,   sum(上煤量 * 水分) H9,   sum(上煤量 * 全硫) P9         from (SELECT RLCYD.CREATEDATE as 上煤时间,    RLCYD.RLNUM      as 采样编号,           RLCYD.YSFS       as 煤样来源,     RLCYD.SL         as 上煤量,         RLHYD.QSHF       as 全水分,   RLHYD.KGJSF      as 水分,  RLHYD.HF2        as 灰分,            RLHYD.HFF2       as 挥发分,  RLHYD.GDT        as 固定碳,          RLHYD.LF2        as 全硫,   RLHYD.DWRZH1     as 热值mj,           RLHYD.DWRZH2     as 热值大卡,     RLHYD.HFF3       as 干燥无灰基挥发分        FROM RLCYD, RLZHYD, RLHYD    WHERE RLCYD.CYLB = '入炉煤'       AND RLHYD.STATUS = '已关闭'  and RLHYD.rlzhydnum = RLZHYD.rlzhydnum      and RLCYD.rlcydnum = RLZHYD.rlcydnum      and substr(RLCYD.RLNUM, 3, 8) = '" + str4 + "')))   ";

        ResultSet localResultSet1 = localStatement.executeQuery(str5);
        ResultSet localResultSet2 = localStatement.executeQuery(str6);

        double d1 = 0.0D;
        if (localResultSet1.next()) {
          d1 = localResultSet1.getDouble("pjrz");
        }
        localMboRemote2.setValue("AVGVALUE2", d1);
        localResultSet1.close();

        double d2 = 0.0D;
        if (localResultSet2.next()) {
          d2 = localResultSet2.getDouble("pjrz1");
        }
        localMboRemote2.setValue("AVGVALUE1", d2);
        localResultSet2.close();
      }
    }
    catch (Exception localException) {
    }
    finally {
      if (localStatement != null) {
        try {
          localStatement.close();
        } catch (SQLException localSQLException3) {
          localSQLException3.printStackTrace();
        }
      }
      if (localConnection != null) {
        try {
          localConnection.close();
        } catch (SQLException localSQLException4) {
          localSQLException4.printStackTrace();
        }
      }
    }

    return super.SAVE();
  }
}