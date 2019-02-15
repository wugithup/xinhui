package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.fmh.FmhjlmxAppBean 华东大区 阜阳项目 副产品销售明细页面类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月7日 下午2:45:03
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FmhjlmxAppBean extends CAppBean {
	private static Connection conn = null;

	public int SYNJLDATA() throws MXException, RemoteException {
		try {
			Connection con = MXServer.getMXServer().getDBManager()
					.getConnection(clientSession.getUserInfo().getConnectionKey());
			CallableStatement cs = con.prepareCall("{call fcpjlmx}");
			cs.execute();
			Statement state1 = con.createStatement();
			// 2015-12-05 必须关联最后一个已批准的副产品变动单,
			MboSetRemote FMHJGB = getMboOrZombie().getMboSet("FMHJGB");
			for (int i = 0; i < FMHJGB.count(); ++i) {
				String bgstartdate = FMHJGB.getMbo(i).getString("bddate");
				// String bgenddate=FMHJGB.getMbo(i).getString("bdenddate");
				double xjg = FMHJGB.getMbo(i).getDouble("xjg");
				String type = FMHJGB.getMbo(i).getString("type");
				String sql = "update fmhjlmx set unitcost=" + xjg + ",linecost=jzjhk*" + xjg
						+ " where to_char(ZCCZSJ,'yyyy-mm-dd')>='" + bgstartdate + "' and unitcost is null and rlpz ='"
						+ type + "'";
				SqlFormat sf = new SqlFormat(sql);
				state1.executeUpdate(sf.format());
				con.commit();
			}
			clientSession.showMessageBox(creatingEvent, "提示", "已成功更新计量数据及价格！", 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 同步数据
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void TBSJ() throws MXException, RemoteException {
		conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
		String sql = "   insert into FMHJLMX value  " + "  (FMHJLMXID,CNUM,CREATEBY,CREATEDATE,JZ, KCCZSJ, "
				+ "MZ,ORGID,SITEID, ZCCZSJ,  HASLD,   " + "   PZ, RLJLMXWHID,GYS,RLPZ, JZJHK,UNITCOST,   "
				+ "  LINECOST)  select FMHJLMXID,CNUM," + "    CREATEBY,CREATEDATE,JZ,KCCZSJ,MZ, ORGID,   "
				+ "   SITEID, ZCCZSJ, HASLD,PZ, RLJLMXWHID, GYS,   " + "   RLPZ,JZJHK, UNITCOST,"
				+ "  LINECOST  from FMHJLMX@fuyang   "
				+ "  where  to_char(createdate,'yyyy-mm-dd')=    to_char(sysdate,'yyyy-mm-dd') "
				+ "   and  RLJLMXWHID not in (select RLJLMXWHID from FMHJLMX) ";
		// System.out.println("执行插入：\n " + sql);
		PreparedStatement ps = null;// 插入
		try {
			ps = conn.prepareStatement(sql);
			ps.addBatch();
			ps.executeBatch();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
