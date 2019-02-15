package com.shuto.mam.webclient.beans.rl.rllog;

/**  
 com.shuto.mam.webclient.beans.rl.rllog.RlLogAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年9月14日 下午5:28:17
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

public class RlLogAppBean extends AppBean {
	public int SHIFT() throws RemoteException, MXException, ParseException {
		// System.out.println("222222222222222222222222222222222");
		try {
			if (getMbo().getString("STATUS").equals("关闭"))
				throw new MXApplicationException("RLKOG", "SHIFT");
		} catch (RemoteException e) {
			e.printStackTrace();

			Object[] obj = { "交班后将不能修改日志信息" };
			WebClientEvent event = this.clientSession.getCurrentEvent();
			int msgRet = event.getMessageReturn();
			if (msgRet < 0) {
				throw new MXApplicationYesNoCancelException(
						"classaddClassification", "RLLOG", "JB", obj);
			}
			if (msgRet == 8) {
				MboRemote thisMbo = this.app.getAppBean().getMbo();
				thisMbo.setValue("DBR", thisMbo.getUserInfo().getPersonId());
				thisMbo.setValue("STATUS", "关闭");

				MboSetRemote newMboSet = this.app.getAppBean().getMboSet();
				MboRemote nuwMbo = newMboSet.add();
				DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				Date d1 = null;
				d1 = date.parse(thisMbo.getString("DBDATE"));
				d1.setDate(d1.getDate() + 1);
				System.out.println(date.format(d1));
				nuwMbo.setValue("DBDATE", date.format(d1));
				nuwMbo.setValue("STATUS", "当班");

				nuwMbo.setValue("DBZY", getString("XBZY"));

				MboSetRemote newSet = nuwMbo.getMboSet("RLLOG_SBJC");
				MboSetRemote oldSet = thisMbo.getMboSet("RLLOG_SBJC");
				if (oldSet != null) {
					for (int i = 0; i < oldSet.count(); i++) {
						MboRemote tempMbo = newSet.add();
						tempMbo.setValue("RLLOGID", nuwMbo.getString("RLLOGID"));
						tempMbo.setValue("SBTYPE",
								oldSet.getMbo(i).getString("SBTYPE"));
						tempMbo.setValue("TYPE",
								oldSet.getMbo(i).getString("TYPE"));
						tempMbo.setValue("BZ1",
								oldSet.getMbo(i).getBoolean("BZ1"));
						tempMbo.setValue("BZ2",
								oldSet.getMbo(i).getBoolean("BZ2"));
						tempMbo.setValue("BZ3",
								oldSet.getMbo(i).getBoolean("BZ3"));
						tempMbo.setValue("YOU",
								oldSet.getMbo(i).getBoolean("YOU"));
						tempMbo.setValue("LIANG",
								oldSet.getMbo(i).getBoolean("LIANG"));
						tempMbo.setValue("CHA",
								oldSet.getMbo(i).getBoolean("CHA"));
					}
					oldSet.save();
					oldSet.close();
				}
				newMboSet.save();
				newMboSet.close();
				Object[] jb = { "交班成功已经生成新的日志，编号为："
						+ nuwMbo.getString("RLLOGID") };
				throw new MXApplicationYesNoCancelException(
						"classaddClassification", "RLLOG", "JBCG", jb);
			}
		}
		return 1;
	}

	public int SAVE() throws MXException, RemoteException {
		MboRemote thisMbo = this.app.getAppBean().getMbo();
		String personid = thisMbo.getUserInfo().getPersonId();

		if ((getMbo().getString("STATUS").equals("关闭"))
				&& (!personid.equals("MAXADMIN"))) {
			throw new MXApplicationException("RLKOG", "SAVE");
		}

		int A1Q = 0;
		int A2Q = 0;
		int A3Q = 0;
		int A4Q = 0;
		int B1Q = 0;
		int B2Q = 0;
		int B3Q = 0;
		int B4Q = 0;
		int B5Q = 0;
		int B6Q = 0;
		int B7Q = 0;
		int B8Q = 0;
		int B9Q = 0;
		int C1Q = 0;
		int C2Q = 0;
		int C3Q = 0;
		int C4Q = 0;
		int C5Q = 0;
		int C6Q = 0;
		int C7Q = 0;
		int C8Q = 0;
		int QJZYM = 0;
		int DQ = 0;
		int GLMQ = 0;
		int HPQ = 0;
		A1Q = thisMbo.getInt("A1Q");
		A2Q = thisMbo.getInt("A2Q");
		A3Q = thisMbo.getInt("A3Q");
		A4Q = thisMbo.getInt("A4Q");
		B1Q = thisMbo.getInt("B1Q");
		B2Q = thisMbo.getInt("B2Q");
		B3Q = thisMbo.getInt("B3Q");
		B4Q = thisMbo.getInt("B4Q");
		B5Q = thisMbo.getInt("B5Q");
		B6Q = thisMbo.getInt("B6Q");
		B7Q = thisMbo.getInt("B7Q");
		B8Q = thisMbo.getInt("B8Q");
		B9Q = thisMbo.getInt("B9Q");
		C1Q = thisMbo.getInt("C1Q");
		C2Q = thisMbo.getInt("C2Q");
		C3Q = thisMbo.getInt("C3Q");
		C4Q = thisMbo.getInt("C4Q");
		C5Q = thisMbo.getInt("C5Q");
		C6Q = thisMbo.getInt("C6Q");
		C7Q = thisMbo.getInt("C7Q");
		C8Q = thisMbo.getInt("C8Q");
		QJZYM = thisMbo.getInt("QJZYM");
		DQ = thisMbo.getInt("DQ");
		GLMQ = thisMbo.getInt("GLMQ");
		HPQ = thisMbo.getInt("HPQ");
		thisMbo.setValue("RCZCS", A1Q + A2Q + A3Q + A4Q + B1Q + B2Q + B3Q + B4Q
				+ B5Q + B6Q + B7Q + B8Q + B9Q + C1Q + C2Q + C3Q + C4Q + C5Q
				+ C6Q + C7Q + C8Q + QJZYM + DQ + GLMQ + HPQ, 11L);
		return super.SAVE();
	}

	
}
