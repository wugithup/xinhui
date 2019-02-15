/**
 * 
 */
package com.shuto.mam.webclient.beans.statistics;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Administrator
 *
 */
public class TargetSelecDataBean extends DataBean {
	private String dialogid;
	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		String appname = this.app.getApp();
		MboSetRemote targetSet = null;
		//生产日报/月报
		if ("zhtjscrb".equalsIgnoreCase(appname) || "zhtjscyb".equalsIgnoreCase(appname) || "zhtjfbsj".equalsIgnoreCase(appname)) {
			targetSet = getReportTargets();
		//综合台账
		} else if ("zhtjzhtz".equals(appname)) {
			targetSet = getLedgerTargets();
		}
		return targetSet;
	}
	
	/**
	 * 生产日报\月报中获取指标
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private MboSetRemote getReportTargets() throws RemoteException, MXException {
		dialogid = this.getId();
		String orgid = this.app.getAppBean().getMbo().getString("orgid");
		String reportnum = this.app.getAppBean().getMbo().getString("reportnum");
		String num = this.app.getAppBean().getMbo().getString("num");
		String wherestr = "orgid = '"+orgid+"' and reportnum = '"+reportnum+"' and num not in (select reporttargetnum from dailyreporttargetdata where reportdatanum='"+num+"')";
		MboSetRemote targetSet = super.getMboSetRemote();
		if ("targetselect_lrz".equalsIgnoreCase(dialogid)) {
			wherestr = wherestr + " and type='录入值'";
		} else if ("targetselect_sis".equalsIgnoreCase(dialogid)) {
			wherestr = wherestr + " and type='取SIS值'";
		} else if ("targetselect_gdz".equalsIgnoreCase(dialogid)) {
			wherestr = wherestr + " and type='固定值'";
		} else if ("targetselect_jjs".equalsIgnoreCase(dialogid)) {
			wherestr = wherestr + " and type='仅计算'";
		} else if ("targetselect_ljz".equalsIgnoreCase(dialogid)) {
			wherestr = wherestr + " and type='累计值'";
		} else if ("targetselect_dy".equalsIgnoreCase(dialogid)) {
			wherestr = wherestr + " and targettype='当月'";
		} else if ("targetselect_nl".equalsIgnoreCase(dialogid)) {
			wherestr = wherestr + " and targettype='年累'";
		}
		targetSet.setWhere(wherestr);
		targetSet.setOrderBy("ordernum");
		return targetSet;
	}
	
	/**
	 * 综合台账获取指标
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private MboSetRemote getLedgerTargets() throws RemoteException, MXException {
		String orgid = this.app.getAppBean().getMbo().getString("orgid");
		String dailyledgernum = this.app.getAppBean().getMbo().getString("dailyledgernum");
		String ledgertype = this.app.getAppBean().getMbo().getString("ledgertype");
		String reporttype = null;
		if ("日台账".equalsIgnoreCase(ledgertype)) {
			reporttype = "生产日报";
		} else if ("月台账".equalsIgnoreCase(ledgertype)) {
			reporttype = "生产月报";
		}
		String wherestr = "orgid = '"+orgid+"' and reportnum in(select num from dailyreport where orgid='"+orgid+"' and reporttype='"+reporttype+"') " +
				"and num not in(select targetnum from dailyledgertarget where orgid='"+orgid+"' and dailyledgernum='"+dailyledgernum+"')";
		MboSetRemote targetSet = super.getMboSetRemote();
		targetSet.setWhere(wherestr);
		targetSet.setOrderBy("ordernum");
		return targetSet;
	}
	
	public int execute() throws MXException, RemoteException {
		String appname = this.app.getApp();
		//生产日报/月报
		if ("zhtjscrb".equalsIgnoreCase(appname) || "zhtjscyb".equalsIgnoreCase(appname)) {
			insertReportTargets();
		//综合台账
		} else if ("zhtjzhtz".equals(appname)) {
			insertLedgerTargets();
		}
		return super.execute();
	}
	
	/**
	 * 保存生产日报\月报中指标
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void insertReportTargets() throws MXException, RemoteException {
		MboSetRemote selectLines = getMboSet();
		selectLines.resetWithSelection();
		String num = this.app.getAppBean().getMbo().getString("num");
		MboSetRemote targetdatas = this.app.getAppBean().getMbo().getMboSet("DAILYREPORTTARGETDATA");
		MboRemote targetdata = null;
		for (int i = 0; i < selectLines.count(); i++) {
			targetdata = targetdatas.add();
			targetdata.setValue("description", selectLines.getMbo(i).getString("name"));
			targetdata.setValue("reportdatanum", num);
			targetdata.setValue("reporttargetnum", selectLines.getMbo(i).getString("num"));
			targetdata.setValue("unit", selectLines.getMbo(i).getString("unit"));
			targetdata.setValue("ordernum", selectLines.getMbo(i).getString("ordernum"));
			targetdata.setValue("type", selectLines.getMbo(i).getString("type"));
			targetdata.setValue("targettype", selectLines.getMbo(i).getString("targettype"));
		}
		app.getAppBean().save();
	}
	
	/**
	 * 保存综合台账指标
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void insertLedgerTargets() throws MXException, RemoteException {
		MboSetRemote selectLines = getMboSet();
		selectLines.resetWithSelection();
		String dailyledgernum = this.app.getAppBean().getMbo().getString("dailyledgernum");
		MboSetRemote targetdatas = this.app.getAppBean().getMbo().getMboSet("DAILYLEDGERTARGET");
		MboRemote targetdata = null;
		for (int i = 0; i < selectLines.count(); i++) {
			targetdata = targetdatas.add();
			targetdata.setValue("targetnum", selectLines.getMbo(i).getString("num"));
			targetdata.setValue("description", selectLines.getMbo(i).getString("name"));
			targetdata.setValue("dailyledgernum", dailyledgernum);
			targetdata.setValue("ordernum", selectLines.getMbo(i).getString("ordernum"));
		}
		app.getAppBean().save();
	}
}
