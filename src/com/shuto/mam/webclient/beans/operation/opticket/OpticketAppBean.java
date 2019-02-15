package com.shuto.mam.webclient.beans.operation.opticket;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;

import com.shuto.mam.webclient.beans.base.CAppBean;

/**
 * @author
 */
@SuppressWarnings("deprecation")
public class OpticketAppBean extends CAppBean {
	StringBuffer sBuffer = new StringBuffer();

	public int COPYTOBZ() throws RemoteException, MXException {

		long a = System.currentTimeMillis();
		System.out
				.println("------------------------------------------------------");
		MboRemote opticketMbo = this.app.getAppBean().getMbo();
		opticketMbo.setFlag(MboConstants.READONLY, false);
		String status = opticketMbo.getString("status");
		if (!"已批准".equals(status) && !"已关闭".equals(status)) {
			throw new MXApplicationException("opticket", "scbzopticket");
		}
		MboRemote newMbo = opticketMbo.copy();
		newMbo.setValue("isopticketbz", "1", 11L);
		newMbo.setValue("STATUS", "已批准", 11L);
		newMbo.setValue("SLPERSON", newMbo.getString("SLPERSON"), 11l);
		newMbo.setValue("OPTICKETJHPERSON",
				newMbo.getString("OPTICKETJHPERSON"), 11l);

		// 复制后需要清空的字段
		newMbo.setValueNull("OPTICKETAPPRNUM", 11l);
		newMbo.setValueNull("BZOPTICKETNUM", 11l);
		newMbo.setValueNull("WONUM", 11l);
		newMbo.setValueNull("OPTICKETAPPRNUM", 11l);
		newMbo.setValueNull("FZPERSON", 11l);
		newMbo.setValueNull("ZZPERSON", 11l);
		newMbo.setValueNull("STARTDATE", 11l);
		newMbo.setValueNull("ENDDATE", 11l);

		MboSetRemote opticketLineS = opticketMbo
				.getMboSet("OPTICKETNUM_OPTICKETLINE");
		MboRemote newopticketLine = null;
		if (!opticketLineS.isEmpty()) {
			System.out.println("=====");
			int sum = opticketLineS.count();
			for (int i = 0; i < sum; ++i) {
				System.out.println("操作步骤  = "
						+ opticketLineS.getMbo(i).getString("OPTICKETPROJECT"));
				newopticketLine = opticketLineS.getMbo(i).copy();
				newopticketLine.setValue("opticketnum",
						newMbo.getString("OPTICKETNUM"), 11L);
				newopticketLine.getThisMboSet().save();
			}
			newopticketLine.getThisMboSet().save();
		}

		MboSetRemote opticketPoitS = opticketMbo
				.getMboSet("OPTICKETNUM_OPTICKETDPOINT");
		MboRemote newopticketPoit = null;
		if (!opticketPoitS.isEmpty()) {
			System.out.println("=====");
			int sum = opticketPoitS.count();
			for (int i = 0; i < sum; ++i) {
				System.out.println("危险 : = "
						+ opticketPoitS.getMbo(i).getString("DANGEROUSPOINT"));
				newopticketPoit = opticketPoitS.getMbo(i).copy();
				newopticketPoit.setValue("opticketnum",
						newMbo.getString("OPTICKETNUM"), 11L);
				newopticketPoit.getThisMboSet().save();
			}
			newopticketPoit.getThisMboSet().save();
		}
		newMbo.getThisMboSet().save();
		Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "",
				"操作成功\r<br>新的标准票编号为：" + newMbo.getString("OPTICKETNUM")
						+ "\r<br>执行耗时："
						+ (float) (System.currentTimeMillis() - a) / 1000.0F
						+ " 秒 ", 1);
		opticketMbo.setFlag(MboConstants.READONLY, true);
		return 0;
	}

	@Override
	public int DUPLICATE() throws RemoteException, MXException {
		MboRemote opticketMbo = this.app.getAppBean().getMbo();
		MboRemote newmbo = getMboSet().add();
		// 运行值别
		newmbo.setValue("YXZB", opticketMbo.getString("YXZB"), 11L);
		// 操作任务
		newmbo.setValue("MISSION", opticketMbo.getString("MISSION"), 11L);
		// 业务类型
		newmbo.setValue("TYPE1", opticketMbo.getString("TYPE1"), 11L);
		// 操作票类型
		newmbo.setValue("H_TYPE", opticketMbo.getString("H_TYPE"), 11L);
		// 专业
		newmbo.setValue("PROFESSION", opticketMbo.getString("PROFESSION"), 11L);
		// 危险区域
		newmbo.setValue("WXQUYU", opticketMbo.getString("WXQUYU"), 11L);
		// 状态
		newmbo.setValue("STATUS", "新建", 11L);
		// 设置为标准操作票
		newmbo.setValue("ISOPTICKETBZ", 0, 11L);
		// 备注
		newmbo.setValue("REMARK", opticketMbo.getString("REMARK"), 11L);

		newmbo.setValue("YN1", opticketMbo.getString("YN1"), 11L);
		newmbo.setValue("YN2", opticketMbo.getString("YN2"), 11L);
		newmbo.setValue("YN3", opticketMbo.getString("YN3"), 11L);
		newmbo.setValue("YN4", opticketMbo.getString("YN4"), 11L);
		newmbo.setValue("YN5", opticketMbo.getString("YN5"), 11L);
		newmbo.setValue("YN6", opticketMbo.getString("YN6"), 11L);
		newmbo.setValue("YN7", opticketMbo.getString("YN7"), 11L);
		newmbo.setValue("YN8", opticketMbo.getString("YN8"), 11L);
		newmbo.setValue("YN9", opticketMbo.getString("YN9"), 11L);
		newmbo.setValue("YN10", opticketMbo.getString("YN10"), 11L);
		newmbo.setValue("YN11", opticketMbo.getString("YN11"), 11L);
		newmbo.setValue("YN12", opticketMbo.getString("YN12"), 11L);
		newmbo.setValue("YN13", opticketMbo.getString("YN13"), 11L);
		newmbo.setValue("YN14", opticketMbo.getString("YN14"), 11L);
		newmbo.setValue("YN15", opticketMbo.getString("YN15"), 11L);
		newmbo.setValue("YN16", opticketMbo.getString("YN16"), 11L);
		newmbo.setValue("YN17", opticketMbo.getString("YN17"), 11L);
		newmbo.setValue("YN18", opticketMbo.getString("YN18"), 11L);
		newmbo.setValue("YN19", opticketMbo.getString("YN19"), 11L);
		newmbo.setValue("YN20", opticketMbo.getString("YN20"), 11L);

		copybzopt(opticketMbo, newmbo);
		setCurrentRecordData(newmbo);
		return 1;
	}

	/**
	 * 复制标准操作票步骤和危险点
	 * 
	 * @param opticketMbo
	 * @param newmbo
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void copybzopt(MboRemote opticketMbo, MboRemote newmbo)
			throws MXException, RemoteException {
		try {
			long a = System.currentTimeMillis();
			ConnectionKey key = MXServer.getMXServer().getDBManager()
					.getSystemConnectionKey();
			Connection connection = MXServer.getMXServer().getDBManager()
					.getConnection(key);
			// 获取copy后的操作票编号
			String opticketnum = newmbo.getString("opticketnum");
			// 操作步骤sql
			String optLine = "select ORDERNUM,OPTICKETPROJECT,YUKONG,siteid,orgid from OPTICKETLINE "
					+ "where opticketnum='"
					+ opticketMbo.getString("opticketnum") + "'";
			// 危险预防措施sql
			String dpLine = "select ORDERNUM,DANGEROUSPOINT,DPFCMS,FXTYPES,FXTYPEH,FXTYPEE,siteid,orgid from OPTICKETDPOINT "
					+ "where  opticketnum='"
					+ opticketMbo.getString("opticketnum") + "'";
			connection.setAutoCommit(false);
			Statement optLinestm = connection.createStatement();
			ResultSet optLiners = optLinestm.executeQuery(optLine);

			String insertoptlinesql = "INSERT INTO OPTICKETLINE(ORDERNUM,OPTICKETPROJECT,YUKONG,siteid,orgid,hasld,createperson,opticketnum,OPTICKETLINEID,isop)"
					+ "VALUES (?,?,?,?,?,0,?,?,OPTICKETLINEIDSEQ.NEXTVAL,0)";
			PreparedStatement optLinesm = connection.prepareStatement(
					insertoptlinesql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			while (optLiners.next()) {
				optLinesm.setInt(1, optLiners.getInt("ordernum"));
				optLinesm.setString(2, optLiners.getString("opticketproject"));
				optLinesm.setString(3, optLiners.getString("yukong"));
				optLinesm.setString(4, optLiners.getString("siteid"));
				optLinesm.setString(5, optLiners.getString("orgid"));
				optLinesm.setString(6, opticketMbo.getUserName());
				optLinesm.setString(7, opticketnum);
				optLinesm.addBatch();

			}
			optLinesm.executeBatch();
			optLiners.close();
			optLinestm.close();
			optLinesm.close();

			// -----------------------------------可爱的分割线------------------------------------------------------------//
			Statement dpLinestm = connection.createStatement();
			ResultSet dpLiners = dpLinestm.executeQuery(dpLine);
			// 添加危险点的sql
			String insertdplinesql = "INSERT INTO OPTICKETDPOINT(ORDERNUM,DANGEROUSPOINT,DPFCMS,FXTYPES,FXTYPEH,FXTYPEE,SITEID,ORGID,HASLD,OPTICKETNUM,OPTICKETDPOINTID)"
					+ "VALUES (?,?,?,?,?,?,?,?,0,?,OPTICKETDPOINTIDSEQ.NEXTVAL)";
			PreparedStatement dpLinesm = connection.prepareStatement(
					insertdplinesql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			System.out.println(insertdplinesql);
			while (dpLiners.next()) {
				dpLinesm.setInt(1, dpLiners.getInt("ordernum"));
				dpLinesm.setString(2, dpLiners.getString("DANGEROUSPOINT"));
				dpLinesm.setString(3, dpLiners.getString("DPFCMS"));
				dpLinesm.setString(4, dpLiners.getString("FXTYPES"));
				dpLinesm.setString(5, dpLiners.getString("FXTYPEH"));
				dpLinesm.setString(6, dpLiners.getString("FXTYPEE"));
				dpLinesm.setString(7, dpLiners.getString("siteid"));
				dpLinesm.setString(8, dpLiners.getString("orgid"));
				dpLinesm.setString(9, opticketnum);
				dpLinesm.addBatch();
			}
			dpLinesm.executeBatch();
			dpLiners.close();
			dpLinestm.close();
			dpLinesm.close();
			connection.commit();
			connection.close();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "",
					"操作成功\r<br>新的操作票编号为：" + opticketnum + "\r<br>执行耗时："
							+ (float) (System.currentTimeMillis() - a)
							/ 1000.0F + " 秒 ", 1);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "",
					"复制失败，请联系管理员", 1);
		}
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		MboRemote mbo = this.app.getAppBean().getMbo();

		String personid = mbo.getUserInfo().getPersonId();

		String createperson = mbo.getString("createperson");

		String opticketjhperson = mbo.getString("opticketjhperson");

		String slperson = mbo.getString("slperson");
		if (("WAPPR".equals(getString("status")))
				&& (!createperson.equals(personid))
				&& (!"MAXADMIN".equals(personid))) {
			throw new MXApplicationException("oplog", "opticketsavefail");
		}

		if ((!"".equalsIgnoreCase(opticketjhperson))
				&& (!"".equalsIgnoreCase(slperson))
				&& (slperson.equalsIgnoreCase(opticketjhperson))) {
			throw new MXApplicationException("oplog", "opticketjhopperson");
		}

		super.SAVE();

		// DataBean czbz = this.app.getDataBean("1351564567058");
		//
		// czbz.clearBean();
		//
		// czbz.setOrderBy("ordernum");
		// for (int i = 0; i < czbz.count(); ++i) {
		// czbz.getMbo(i).setValue("ORDERNUM", i + 1);
		// }
		//
		// DataBean ajhbz = this.app.getDataBean("1351564399407");
		//
		// ajhbz.clearBean();
		//
		// ajhbz.setOrderBy("ordernum");
		// for (int i = 0; i < ajhbz.count(); ++i) {
		// ajhbz.getMbo(i).setValue("ORDERNUM", i + 1);
		// }
		// String tca = getString("TCA");
		// String tcb = getString("TCB");
		// // 循环操作步骤
		// for (int o = 0; o < czbz.count(); o++) {
		// // 获取操作项目
		// String opticketproject = czbz.getMbo(o)
		// .getString("OPTICKETPROJECT");
		// // 替换tca
		// String reptca = opticketproject.replace("TCA", tca);
		// // 替换tcb
		// String reptcb = reptca.replace("TCB", tcb);
		// // 把替换后的信息添加到操作项目中
		// czbz.getMbo(o).setValue("OPTICKETPROJECT", reptcb);
		// // 保存操作步骤
		// czbz.save();
		// System.out.println("替换后的信息："
		// + czbz.getMbo(o).getString("OPTICKETPROJECT"));
		// }

		return super.SAVE();
	}

	@Override
	public int DELETE() throws MXException, RemoteException {
		MboRemote mbo = this.app.getAppBean().getMbo();

		String personid = mbo.getUserInfo().getPersonId();

		String createby = mbo.getString("createperson");
		if (("WAPPR".equals(getString("status")))
				&& (!createby.equals(personid))
				&& (!"MAXADMIN".equals(personid))) {
			throw new MXApplicationException("oplog", "opticketdelete");
		}

		return super.DELETE();
	}

	@Override
	public void initializeApp() throws MXException, RemoteException {
		if (!this.app.inAppLinkMode()) {
			String str = "!=已关闭";
			DataBean resultsBean = this.app.getResultsBean();
			resultsBean.setQbe("status", str);
			resultsBean.reset();
		}
		super.initializeApp();
	}

	@Override
	public int RUNAREPORT() throws MXException, RemoteException {
		WorkflowDirector director = this.clientSession.getWorkflowDirector();
		director.preventAutoInit();
		SAVE();
		director.allowAutoInit();
		director.setProcessName("OPTICKET");
		director.startInput(this.clientSession.getCurrentApp().getId(),
				getMbo(), DirectorInput.workflow);
		getWorkflowDirections(director);
		return super.RUNAREPORT();
	}
}