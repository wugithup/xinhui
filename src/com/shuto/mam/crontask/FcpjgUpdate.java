package com.shuto.mam.crontask;
/**  
com.shuto.mam.crontask.FcpjgUpdate 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年9月18日 下午8:52:49
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

public class FcpjgUpdate extends SimpleCronTask
{
  public static final String ACTIVE = "活动";
  public static final String FIXEDDAY = "固定日期";
  public static final String WEEKDAY = "固定星期";
  public static final String SHIFT = "班次";
  public static final String HOURS = "小时";
  SimpleDateFormat dtfmt = new SimpleDateFormat("yyyy年MM月dd日");
  private MXServer mxserver;
  private UserInfo userInfo;
  Connection con = null;

  public void init() throws MXException {
    super.init();
    try {
      this.mxserver = MXServer.getMXServer();
      this.userInfo = getRunasUserInfo();
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public void cronAction()
  {
    try
    {
      this.con = this.mxserver.getDBManager().getConnection(
    	MXServer.getMXServer().getDBManager().getSystemConnectionKey());
      Statement state1 = this.con.createStatement();
      CallableStatement cs = this.con.prepareCall("{call fcpjlmx0}");
      cs.execute();
      MboSetRemote FMHJGB = this.mxserver.getMboSet("FMHJGB", this.userInfo);
      FMHJGB.setWhere("status='已批准' and FMHJGBid in ( select max(FMHJGBid)  from  FMHJGB group by TYPE )");

      FMHJGB.reset();
      for (int i = 0; i < FMHJGB.count(); i++)
      {
        String bgstartdate = FMHJGB.getMbo(i).getString("bddate");

        double xjg = FMHJGB.getMbo(i).getDouble("xjg");
        double yjg = FMHJGB.getMbo(i).getDouble("yjg");
        String type = FMHJGB.getMbo(i).getString("type");
        String sql = null;
        String sql1 = null;
        sql = "update fmhjlmx set unitcost=" + yjg + ",linecost=jzjhk*" + yjg + " where to_char(ZCCZSJ,'yyyy-mm-dd')<'" + bgstartdate + "' and unitcost is null and rlpz ='" + type + "'";
        sql1 = "update fmhjlmx set unitcost=" + xjg + ",linecost=jzjhk*" + xjg + " where to_char(ZCCZSJ,'yyyy-mm-dd')>='" + bgstartdate + "' and unitcost is null and rlpz ='" + type + "'";
        SqlFormat sf = new SqlFormat(sql);
        SqlFormat sf1 = new SqlFormat(sql1);
        state1.executeUpdate(sf.format());
        System.out.println(sql + sql1);
        state1.executeUpdate(sf1.format());
        this.con.commit();
        if(state1!= null){
        	state1.close();
        }
      }
      if(state1!= null){
      	state1.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
    	try {
			if (this.con != null) {
				this.con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
  }
}