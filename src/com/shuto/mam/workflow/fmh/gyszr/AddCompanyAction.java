package com.shuto.mam.workflow.fmh.gyszr;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 供应商准入后插入到副产品供应商中 FCPGYS006 FCPGYS_ADD
 * com.shuto.mam.app.workflow.fmh.gyszr.AddCompanyAction 北京数途科技有限公司 2015-12-12
 * 
 * @author 张建强
 *
 */
public class AddCompanyAction implements ActionCustomClass {

	@Override
	public void applyCustomAction(MboRemote mbo, Object[] arg1) throws MXException, RemoteException {
		// type ='H'
		String FCMGYSZRNUM = mbo.getString("FCMGYSZRNUM");// 编号
		String name = mbo.getString("DESCRIPTION");// 得到供应商名称
		String REMITADDRESS1 = mbo.getString("GSDZ");// 得到供应商地址
		String FRDB = mbo.getString("FDDBR"); // 法人代表
		String REMITCONTACT = mbo.getString("YWLXR"); // 联系人
		String CELLPHONE = mbo.getString("LXRDH");// 联系电话
		String JSDW = mbo.getString("JSDW");// 结算单位
		String SDM = mbo.getString("SDM");// 税代码
		String SKF = mbo.getString("SKR");// 收款方
		String BANKACCOUNT = mbo.getString("SKRKHH");// 开户行
		String BANKNUM = mbo.getString("SKRZH");// 银行账号
		String GYNL = mbo.getString("GYNL");// 供应能力
		String JXNL = mbo.getString("YSNL");// 接卸能力
		String ZYGD = mbo.getString("GSGD");// 主要股东
		MXServer mxServer = MXServer.getMXServer();

		try {

			MboSetRemote compmasterSet = mxServer.getMboSet("COMPMASTER", mxServer.getSystemUserInfo());
			MboRemote compmasterMbo = compmasterSet.add();

			String company = getNextNumber(mxServer);

			compmasterMbo.setValue("NAME", name);
			compmasterMbo.setValue("company", company);

			MboSetRemote compaySet = compmasterMbo.getMboSet("COMPANIES");
			MboRemote companyMbo = compaySet.add();
			companyMbo.setValue("FCMGYSZRNUM", FCMGYSZRNUM, 11L);
			companyMbo.setValue("company", company);
			companyMbo.setValue("type", "H", 11L);
			companyMbo.setValue("DATEZR", new Date(), 11L);
			companyMbo.setValue("name", name, 11L);
			companyMbo.setValue("REMITADDRESS1", REMITADDRESS1, 11L);

			companyMbo.setValue("FRDB", FRDB, 11L);
			companyMbo.setValue("REMITCONTACT", REMITCONTACT, 11L);
			companyMbo.setValue("CELLPHONE", CELLPHONE, 11L);
			companyMbo.setValue("JSDW", JSDW, 11L);
			companyMbo.setValue("SDM", SDM, 11L);
			companyMbo.setValue("SKF", SKF, 11L);
			companyMbo.setValue("BANKACCOUNT", BANKACCOUNT, 11L);
			companyMbo.setValue("BANKNUM", BANKNUM, 11L);
			companyMbo.setValue("GYNL", GYNL, 11L);
			companyMbo.setValue("JXNL", JXNL, 11L);
			companyMbo.setValue("ZYGD", ZYGD, 11L);

			compmasterSet.save();

			compaySet.save();

			compmasterSet.close();
			compaySet.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new MXApplicationException(e.getMessage(), "");
		}
	}

	public static synchronized String getNextNumber(MXServer mxServer) throws Exception {

		String url = mxServer.getProperty("mxe.db.url");
		String username = mxServer.getProperty("mxe.db.user");
		String password = mxServer.getProperty("mxe.db.password");

		Connection conn = DriverManager.getConnection(url, username, password);

		Statement pr = conn.createStatement();
		String sql = "select seed from autokey where autokeyname = 'COMPMASTER' and setid='COMPSET' ";
		ResultSet rs = pr.executeQuery(sql);
		int num = 0;
		if (rs.next()) {
			num = rs.getInt(1) + 1;
		}
		rs.close();
		String sql2 = "update autokey set seed=" + num + " where autokeyname =  'COMPMASTER' and setid='COMPSET'";
		pr.executeUpdate(sql2);

		pr.close();
		conn.close();
		return String.valueOf(num);
	}
}
