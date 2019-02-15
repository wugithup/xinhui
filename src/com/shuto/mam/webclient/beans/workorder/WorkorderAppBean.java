package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.ism.content.mriu.StringUtil;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXAccessException;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

import com.ibm.icu.text.DecimalFormat;
import com.shuto.mam.app.expand.AutoDateSiteNum;

/**
 * com.shuto.mam.webclient.beans.workorder.WorkorderAppBean 控股工作票
 * 
 * @author shanbh shanbh@shuoto.cn
 * @version V1.2
 * @date 2017年6月29日 上午10:37:35
 * @copyright 1.1
 */
public class WorkorderAppBean extends AppBean {

	private Connection con = null;
	private PreparedStatement stmt = null;
	private ConnectionKey key = null;
	private String OWNERTABLE = "";
	private long OWNERID;

	@Override
	public synchronized void save() throws MXException {
		try {
			scbh();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		super.save();
	}

	/**
	 * 工作票生成编号规则
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void scbh() throws MXException, RemoteException {
		MboRemote mainmbo = this.app.getAppBean().getMbo();
		MboSetRemote thisMboSet = mainmbo.getThisMboSet();
		String orgid = mainmbo.getString("orgid");
		String siteid = mainmbo.getString("siteid");
		// 取得票种简拼
		String abbreviation = mainmbo.getString("S_ORDERTYPE.MAXVALUE");
		String S_ORDERTYPE = mainmbo.getString("S_ORDERTYPE");
		// 工作票编号
		String S_WOTKNUM = mainmbo.getString("S_WOTKNUM");
		// 应用程序名
		String appName = mainmbo.getThisMboSet().getApp();
		// 获取系统时间
		String date = new SimpleDateFormat("yyMM").format(new Date());
		// 获取许可时间
		String xkDate = mainmbo.getString("S_ZZPZDATE");
		if (!xkDate.isEmpty()) {
			if (!S_ORDERTYPE.isEmpty()) {
				if (S_WOTKNUM.isEmpty()) {
					// 实例化自动编号方法类
					AutoDateSiteNum adsn = new AutoDateSiteNum(thisMboSet);
					// 获取自动编号
					int num = adsn.getNextAutoDateSiteNum(orgid, siteid,
							appName, null, abbreviation);
					mainmbo.setValue("S_WOTKNUM", abbreviation + date
							+ new DecimalFormat("000").format(num), 11L);
				}
			}
		}

	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		scbh();
		return super.SAVE();
	}

	/**
	 * 安措附页自动勾选
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void autoCheck() throws MXException, RemoteException{
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String orderType = mainMbo.getString("S_ORDERTYPE");

		MboSetRemote wosafetyMboSet=mainMbo.getMboSet("WOSAFETYLINK");
		MboSetRemote yxWosafetyMboSet=mainMbo.getMboSet("WOSAFETYLINK001");
		System.out.println("-------------------------------------"+wosafetyMboSet.count()+"-------------------------------------------");
		System.out.println("-------------------------------------"+yxWosafetyMboSet.count()+"-------------------------------------------");

		System.out.println("-------------------------------------"+orderType+"-------------------------------------------------------------");
		if("热力机械工作票".equals(orderType)&&wosafetyMboSet.count()>14){
			mainMbo.setValue("XKZGCZY", 1);
		}else if("电气第二种工作票".equals(orderType)&&wosafetyMboSet.count()>14){
			mainMbo.setValue("XKZGCZY", 1);
		}else if("电气第一种工作票".equals(orderType)&&wosafetyMboSet.count()>8){
			mainMbo.setValue("XKZGCZY", 1);
		}else if("热控第一种工作票".equals(orderType)&&yxWosafetyMboSet.count()>4){
			mainMbo.setValue("XKZGCZY", 1);
		}else if("热控第二种工作票".equals(orderType)&&yxWosafetyMboSet.count()>4){
			mainMbo.setValue("XKZGCZY", 1);
		}else if("继电保护工作票".equals(orderType)&&yxWosafetyMboSet.count()>6){
			mainMbo.setValue("XKZGCZY", 1);
		}
	}
	
	public void CREBZWO() throws RemoteException, MXException {
		try { 
			MboRemote mainMbo = this.app.getAppBean().getMbo();
			mainMbo.setFlag(MboConstants.READONLY, false);
			String status = mainMbo.getString("status");
			String BZGZP = mainMbo.getString("BZWONUM");
			String location = mainMbo.getString("location");
			String wonum = mainMbo.getString("wonum");
			String S_WOTKNUM = mainMbo.getString("S_WOTKNUM");
			MboSetRemote bzwoset = mainMbo.getMboSet("$sonworkorder", "workorder",
					"worktype='标准工作票' and woeq1='" + wonum + "'");
			if ((bzwoset.count() > 0) && (BZGZP.isEmpty())) {
				Utility.showMessageBox(this.clientSession.getCurrentEvent(),
						"waring", "该工作票已经创建过标准工作票，不可以再次创建！", 1);
				return;
			}
			MboRemote newMbo = mainMbo.copy();
			newMbo.setValueNull("S_WOTKNUM",11L);
			newMbo.setValueNull("S_ZZPZDATE",11L);
			newMbo.setValueNull("S_XKDATE",11L);
			newMbo.setValueNull("C_GHSJ",11L);
			newMbo.setValueNull("S_PZGZJSDATE",11L);
			newMbo.setValueNull("C_REGAINSTART",11L);
			newMbo.setValueNull("OPLOG_ZXDATE",11L);
			crebzwoimp(newMbo);
			long a = System.currentTimeMillis();
			ConnectionKey key = MXServer.getMXServer().getDBManager()
					.getSystemConnectionKey();
			Connection connection = MXServer.getMXServer().getDBManager()
					.getConnection(key);
			//获取工作票编号
			String woNum = mainMbo.getString("wonum");
			// 获取标准工作票编号
			String gzpNum = newMbo.getString("wonum");
			// 隔离方法sql
			String aqcs = "select hazardid,tagoutid,applyseq,tagmethod,tagoutdescription2,siteid,orgid from WOSAFETYLINK where wonum='"+woNum+"'";
			connection.setAutoCommit(false);
			Statement gzpLinestm = connection.createStatement();
			ResultSet gzpLiners = gzpLinestm.executeQuery(aqcs);

			String insertSql = "INSERT INTO WOSAFETYLINK(wosafetylinkid,wonum,hazardid,tagoutid,applyseq,"
					+ "wosafetydatasource,orgid,siteid,"
					+ "s_iszx,iskg,isdx,isbsp,s_ishf,isglct,tagmethod,langcode,c_iszx,c_jxzl,hasld,tagoutdescription2)"
					+ "VALUES (wosafetylinkseq.nextval,?,?,?,?,'WO',?,?,0,0,0,0,0,0,?,'ZH',0,0,0,?)";
			PreparedStatement gzpLinesm = connection.prepareStatement(
					insertSql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			while (gzpLiners.next()) {
				gzpLinesm.setString(1, gzpNum);
				gzpLinesm.setString(2, gzpLiners.getString("hazardid"));
				gzpLinesm.setString(3, gzpLiners.getString("tagoutid"));
				gzpLinesm.setInt(4, gzpLiners.getInt("applyseq"));
				gzpLinesm.setString(5, gzpLiners.getString("orgid"));
				gzpLinesm.setString(6, gzpLiners.getString("siteid"));
				gzpLinesm.setString(7, gzpLiners.getString("tagmethod"));
				gzpLinesm.setString(8,
						gzpLiners.getString("tagoutdescription2"));
				gzpLinesm.addBatch();

			}
			System.out.println(insertSql);
			gzpLinesm.executeBatch();
			gzpLiners.close();
			gzpLinestm.close();
			gzpLinesm.close();

			connection.commit();
			connection.close();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "",
					"操作成功\r<br>新的标准票编号为：" + newMbo.getString("WONUM"),1);

			mainMbo.setFlag(MboConstants.READONLY, true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		

	}

	public void crebzwoimp(MboRemote bzwo) throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String WONUM = mainMbo.getString("WONUM");
		bzwo.setValue("worktype", "标准工作票", 11L);
		bzwo.setValue("STATUS", "等待批准",11L);
		bzwo.setValue("parent", "", 11L);
		bzwo.setValue("SCHEDFINISH", "", 11L);
		bzwo.setValue("SCHEDSTART", "", 11L);
		bzwo.setValue("BOXCODE", "", 11L);
		bzwo.setValue("WOEQ1", WONUM, 11L);
		bzwo.setValue("LEAD", "", 11L);
		bzwo.setValue("S_WORKCY", "", 11L);
		bzwo.setValue("S_WORKCYQTY", "", 11L);

		super.SAVE();
	}

	public boolean isSysuser() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		String personid = mbo.getUserInfo().getPersonId();
		MboSetRemote users = mbo.getMboSet("$tmp_maxuser", "maxuser",
				"sysuser =1 and personid='" + personid + "'");
		boolean isEmpty = !users.isEmpty();
		users.close();
		return isEmpty;
	}

	public boolean hasAuth() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		if (mbo == null) {
			return true;
		}
		String personid = mbo.getUserInfo().getPersonId();

		if (isSysuser()) {
			return true;
		}
		this.OWNERTABLE = getMbo().getName();
		this.OWNERID = getMbo().getUniqueIDValue();

		MboSetRemote wfinstance = mbo.getMboSet("instance", "WFINSTANCE",
				"ownertable='" + this.OWNERTABLE + "' and ownerid='"
						+ this.OWNERID + "' and active = 1");
		boolean noInstance = wfinstance.isEmpty();
		wfinstance.close();

		if (noInstance) {
			return true;
		}
		String sql = "ownerid='" + this.OWNERID + "'" + " and ownertable='"
				+ this.OWNERTABLE + "'" + " and assignstatus='活动'"
				+ " and assigncode='" + personid + "'";
		MboSetRemote mbosetremote = mbo.getMboSet("$assigncode",
				"WFASSIGNMENT", sql);
		boolean hasAssigncode = mbosetremote.isEmpty();
		mbosetremote.close();
		return !hasAssigncode;
	}

	@Override
	public int DELETE() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		return super.DELETE();
	}

	/**
	 * 删除等待批准的工作票
	 */
	public int DELWO() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String wonum = mainMbo.getString("wonum");
		String status = mainMbo.getString("status");
		if (status.equals("等待批准") || status.equals("新建")) {
			if (!hasAuth()) {
				throw new MXApplicationException("system", "noauth");
			}
			try {
				WebClientEvent event = clientSession.getCurrentEvent();
				int msgRet = event.getMessageReturn();
				if (msgRet < 0) {
					// 弹出提示窗口问是否继续
					throw new MXApplicationYesNoCancelException(
							"deletecontinueid", "system", "deletecontinue");
				}
				if (msgRet == 8) {
					// 删除工作票关联
					String sql = "delete from relatedrecord where relatedreckey='"
							+ wonum + "' or RECORDKEY='" + wonum + "'";
					// 删除工单
					String sql2 = "delete from workorder where wonum='" + wonum
							+ "'";
					this.key = MXServer.getMXServer().getDBManager()
							.getSystemConnectionKey();
					this.con = MXServer.getMXServer().getDBManager()
							.getConnection(this.key);
					con.setAutoCommit(false);
					con.prepareStatement(sql).execute();
					con.prepareStatement(sql2).execute();
					this.con.commit();
					this.app.getAppBean().reloadTable();
					// 删除安全措施
					MboSetRemote hazards = mainMbo.getMboSet("WOSLTAGENABLED");
					if (!hazards.isEmpty()) {
						for (int i = 0; i < hazards.count(); i++) {
							hazards.getMbo(i).getMboSet("WOSAFETYLINKTAG")
									.deleteAndRemoveAll();
						}
						hazards.deleteAndRemoveAll();
					}
					MboSetRemote acfl = mainMbo.getMboSet("WOSAFETYLINK");
					if (!acfl.isEmpty()) {
						MboSetRemote fl = mainMbo.getMboSet("WOHAZARD");
						MboSetRemote WOTAGOUT = mainMbo.getMboSet("WOTAGOUT");
						fl.deleteAll();
						fl.commit();
						fl.save();
						WOTAGOUT.deleteAll();
						WOTAGOUT.commit();
						WOTAGOUT.save();
						acfl.deleteAll();
						acfl.commit();
						acfl.save();
					}
					clientSession.showMessageBox(
							clientSession.getCurrentEvent(), "system",
							"deleterecord", (Object[]) null);
					gotoTab(clientSession, "list");
				}
			} catch (SQLException e) {
				try {
					this.con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					if (this.stmt != null) {
						this.stmt.close();
					}
					if (this.con != null) {
						this.con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else if ("已批准".equals(status)) {
			throw new MXApplicationException("error", "apprnodelete");
		}
		return 1;
	}

	@Override
	protected void setCurrentRecordData(MboRemote mbo) throws MXException,
			RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String wotype = mainMbo.getString("S_ORDERTYPE");
		String status = mainMbo.getString("status");
		String[] attr = { "LOCATION", "OPLOG_DELAYCAUSE", "DESCRIPTION",
				"WORKSITE" };
		mainMbo.setFieldFlag(attr, 128L, true);
		String s_wotknum = mainMbo.getString("S_WOTKNUM");
		if (!s_wotknum.isEmpty()) {
			mainMbo.setFieldFlag("S_ORDERTYPE", 7L, true);
		}
		if ("已批准".equals(status)) {
			mbo.setFlag(MboConstants.READONLY, true);
		}
		if ("已作废".equals(status)) {
			mbo.setFlag(MboConstants.READONLY, true);
		}
		super.setCurrentRecordData(mbo);
		super.setCurrentRecordData(mbo);
	}

	public void APPR() throws RemoteException, MXException {
		super.SAVE();
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		WebClientEvent event = clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet < 0) {
			// 弹出提示窗口问是否继续
			throw new MXApplicationYesNoCancelException("savecontinueid",
					"woappr", "woappr");
		}
		if (msgRet == 8) {
			mainMbo.setValue("status", "已批准", 11L);
			this.app.getAppBean().refreshTable();
			this.app.getAppBean().reloadTable();
			this.app.getAppBean().save();
		}

	}

	/**
	 * 确认创建动火工作票提示
	 * 
	 * @return
	 * @throws MXException
	 */
	public void DHGZPCheck() throws MXException, RemoteException {
		String msg = "是否创建动火工作票？";
		WebClientEvent event = this.clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet < 0) {
			throw new MXApplicationYesNoCancelException("savecontinueid",
					"system", "taskxf", new String[] { msg });
		}
		// 是
		if (msgRet == 8) {
			MboRemote mainMbo = this.app.getAppBean().getMbo();
			mainMbo.setValue("SFGLDH", 1);
			this.CREATEDH();
		}

	}

	public void CREATEDH() throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		WebClientEvent event = this.sessionContext.getCurrentEvent();
		DataBean appBean = Utility.getDataSource(this.sessionContext,
				this.app.getAppHandler());
		String message = " 是否创建动火票？ ";
		try {
			MboSetRemote mboSet = mainMbo.getMboSet("wonum");
			if (mboSet.isEmpty()) {
				appBean.save();
				MboRemote wo = mboSet.add();
				wo.setValue("S_REPWONUM", mainMbo.getString("wonum"), 11L);
				wo.setValue("worktype", "工作票", 11L);
				wo.setValue("C_FJCZBWHRY", mainMbo.getString("C_FJCZBWHRY"));
				wo.setValue("isgls", false, 11L);
				wo.setValue("WOEQ10", "DHP", 11L);
				wo.setValue("LOCATION", mainMbo.getString("location"));
				wo.setValue("OPLOG_DELAYCAUSE", mainMbo.getString("OPLOG_DELAYCAUSE"));
				wo.setValue("DESCRIPTION", mainMbo.getString("DESCRIPTION"));
				wo.setValue("WORKSITE", mainMbo.getString("WORKSITE"));

				copyAQCS(wo);
				String woticketId = wo.getUniqueIDValue() + "";
				mboSet.save();
				appBean.save();
				appBean.reset();
				appBean.next();
				this.sessionContext.queueRefreshEvent();
				this.sessionContext.queueRefreshEvent();
				WebClientSession wcs = event.getWebClientSession();
				String additionalEvent = event.getAdditionalEvent();
				String additionalEventValue = event.getAdditionalEventValue();

				String queryString = "?event=gotoapp&value=dhwoticket";
				if (!WebClientRuntime.isNull(additionalEvent)) {
					queryString = queryString + "&additionalevent="
							+ additionalEvent;
					if (!WebClientRuntime.isNull(additionalEventValue)) {
						queryString = queryString + "&additionaleventvalue="
								+ additionalEventValue;
					}
				}
				queryString = queryString + "&uniqueid=" + woticketId;
				wcs.getCurrentApp().put("forcereload", "true");
				wcs.gotoApplink(queryString);
				event.cancelRender();
			} else {
				String msg = "该工作票已关联动火票（"
						+ mboSet.getMbo(0).getString("wonum") + "），不能再次关联";
				throw new MXApplicationException("workorder", "dhp",
						new String[] { msg });
			}

		} catch (MXException mxe) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
		}
	}

	@Override
	public int DUPLICATE() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		MboRemote gzpMbo = this.app.getAppBean().getMbo();
		MboRemote newMbo = getMboSet().add();
		// 工作票类型
		newMbo.setValue("S_ORDERTYPE", gzpMbo.getString("S_ORDERTYPE"));
		// 设备编码
		newMbo.setValue("LOCATION", gzpMbo.getString("LOCATION"));
		// 设备名称
		newMbo.setValue("OPLOG_DELAYCAUSE",
				gzpMbo.getString("OPLOG_DELAYCAUSE"));
		// 工作内容
		newMbo.setValue("DESCRIPTION", gzpMbo.getString("DESCRIPTION"));
		// 工作地点
		newMbo.setValue("WORKSITE", gzpMbo.getString("WORKSITE"));
		// 钥匙箱编号
		newMbo.setValue("BOXCODE", gzpMbo.getString("BOXCODE"));
		newMbo.setValue("WORKTYPE", gzpMbo.getString("WORKTYPE"));
		// 复制后的工作票编号为空
		newMbo.setValueNull("S_WOTKNUM", 11L);
		copyGzp(gzpMbo, newMbo);
		this.app.getAppBean().refreshTable();
		this.app.getAppBean().reloadTable();
		return 1;
	}

	/**
	 * 复制工作票安全措施
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void copyGzp(MboRemote gzpMbo, MboRemote newmbo)
			throws MXException, RemoteException {
		try {
			long a = System.currentTimeMillis();
			ConnectionKey key = MXServer.getMXServer().getDBManager()
					.getSystemConnectionKey();
			Connection connection = MXServer.getMXServer().getDBManager()
					.getConnection(key);
			// 获取copy后的工作票编号
			String gzpNum = newmbo.getString("wonum");
			// 安全措施sql
			String aqcs = "select hazardid,tagoutid,applyseq,tagmethod,tagoutdescription2,siteid,orgid from WOSAFETYLINK "
					+ "where wonum='" + gzpMbo.getString("wonum") + "'";
			connection.setAutoCommit(false);
			Statement gzpLinestm = connection.createStatement();
			ResultSet gzpLiners = gzpLinestm.executeQuery(aqcs);

			String insertSql = "INSERT INTO WOSAFETYLINK(wosafetylinkid,wonum,hazardid,tagoutid,applyseq,"
					+ "wosafetydatasource,orgid,siteid,"
					+ "s_iszx,iskg,isdx,isbsp,s_ishf,isglct,tagmethod,langcode,c_iszx,c_jxzl,hasld,tagoutdescription2)"
					+ "VALUES (wosafetylinkseq.nextval,?,?,?,?,'WO',?,?,0,0,0,0,0,0,?,'ZH',0,0,0,?)";
			PreparedStatement gzpLinesm = connection.prepareStatement(
					insertSql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			while (gzpLiners.next()) {
				gzpLinesm.setString(1, gzpNum);
				gzpLinesm.setString(2, gzpLiners.getString("hazardid"));
				gzpLinesm.setString(3, gzpLiners.getString("tagoutid"));
				gzpLinesm.setInt(4, gzpLiners.getInt("applyseq"));
				gzpLinesm.setString(5, gzpLiners.getString("orgid"));
				gzpLinesm.setString(6, gzpLiners.getString("siteid"));
				gzpLinesm.setString(7, gzpLiners.getString("tagmethod"));
				gzpLinesm.setString(8,
						gzpLiners.getString("tagoutdescription2"));
				gzpLinesm.addBatch();

			}
			System.out.println(insertSql);
			gzpLinesm.executeBatch();
			gzpLiners.close();
			gzpLinestm.close();
			gzpLinesm.close();

			connection.commit();
			connection.close();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "",
					"操作成功\r<br>新的工作票流水号为：" + gzpNum + "\r<br>执行耗时："
							+ (float) (System.currentTimeMillis() - a)
							/ 1000.0F + " 秒 ", 1);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "",
					"复制失败，请联系管理员", 1);
		}
	}

	public void CANCELLED() throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String status = mainMbo.getString("status");
		WebClientEvent event = this.clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet < 0) {
			throw new MXApplicationYesNoCancelException("savecontinueid",
					"system", "taskxf", new String[] { "是否确认作废此条记录？" });
		}
		// 是
		if (msgRet == 8) {
			mainMbo.setValue("STATUS", "已作废", 11L);
			super.SAVE();
		}
	}

	public void copyAQCS(MboRemote newmbo) throws RemoteException, MXException {

		try {
			long a = System.currentTimeMillis();
			ConnectionKey key = MXServer.getMXServer().getDBManager()
					.getSystemConnectionKey();
			Connection connection = MXServer.getMXServer().getDBManager()
					.getConnection(key);
			// 获取动火工作票编号
			String gzpNum = newmbo.getString("wonum");
			// 隔离方法sql
			String aqcs = "select hazardid,tagoutid,applyseq,tagmethod,tagoutdescription2,siteid,orgid from WOSAFETYLINK where wonum='XH1637'";
			connection.setAutoCommit(false);
			Statement gzpLinestm = connection.createStatement();
			ResultSet gzpLiners = gzpLinestm.executeQuery(aqcs);

			String insertSql = "INSERT INTO WOSAFETYLINK(wosafetylinkid,wonum,hazardid,tagoutid,applyseq,"
					+ "wosafetydatasource,orgid,siteid,"
					+ "s_iszx,iskg,isdx,isbsp,s_ishf,isglct,tagmethod,langcode,c_iszx,c_jxzl,hasld,tagoutdescription2)"
					+ "VALUES (wosafetylinkseq.nextval,?,?,?,?,'WO',?,?,0,0,0,0,0,0,?,'ZH',0,0,0,?)";
			PreparedStatement gzpLinesm = connection.prepareStatement(
					insertSql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			while (gzpLiners.next()) {
				gzpLinesm.setString(1, gzpNum);
				gzpLinesm.setString(2, gzpLiners.getString("hazardid"));
				gzpLinesm.setString(3, gzpLiners.getString("tagoutid"));
				gzpLinesm.setInt(4, gzpLiners.getInt("applyseq"));
				gzpLinesm.setString(5, gzpLiners.getString("orgid"));
				gzpLinesm.setString(6, gzpLiners.getString("siteid"));
				gzpLinesm.setString(7, gzpLiners.getString("tagmethod"));
				gzpLinesm.setString(8,
						gzpLiners.getString("tagoutdescription2"));
				gzpLinesm.addBatch();

			}
			System.out.println(insertSql);
			gzpLinesm.executeBatch();
			gzpLiners.close();
			gzpLinestm.close();
			gzpLinesm.close();

			connection.commit();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

    /**
     * 一键签发操作
     */
    public void ONEKEYQF() throws RemoteException, MXException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String username = mainMbo.getUserName();
        String siteid = mainMbo.getString("SITEID");
        String boxcode = mainMbo.getString("BOXCODE");

        if (StringUtil.isEmpty(boxcode))
        {
            errorInfo("请输入钥匙箱编号!!!");
        }

        MboSetRemote personMboSet = mainMbo.getMboSet("$CANCEL_PERSON","PERSON","PERSONID='"+username+"' AND LOCATIONSITE='"+siteid+"'");
        if (personMboSet.getMbo(0)!=null)
        {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            String displayName = personMboSet.getMbo(0).getString("DISPLAYNAME");
            //运行签发人
            mainMbo.setValue("CANCEL_PERSON",displayName);
            mainMbo.setValue("C_GHSJ",sdf.format(date));
            mainMbo.setValue("STATUS","已签发",11L);
            mainMbo.getThisMboSet().save();

        }


    }

    /**
     * 一键许可
     */
    public int ONEKEYXK() throws RemoteException, MXException, ParseException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String username = mainMbo.getUserName();
        String siteid = mainMbo.getString("SITEID");
        String cancel_person = mainMbo.getString("CANCEL_PERSON");
        Date s_xkdate = mainMbo.getDate("S_XKDATE");
        Date s_pzgzjsdate = mainMbo.getDate("S_PZGZJSDATE");
        String note;

        //动火类型为DHP
        String woeq10 = mainMbo.getString("WOEQ10");
        final String woeqTyep = "DHP";

        MboSetRemote personMboSet = mainMbo.getMboSet("$CANCEL_PERSON","PERSON","PERSONID='"+username+"' AND LOCATIONSITE='"+siteid+"'");

        //如果是动火票就给予相应的提示并赋值
        if (woeqTyep.equals(woeq10)){

            String teamnum = mainMbo.getString("TEAMNUM");
            String c_gzfez = mainMbo.getString("C_GZFEZ");
            String dhfs = mainMbo.getString("DHFS");
            if (StringUtil.isEmpty(teamnum))
            {
                note = "请输入动火部门!!!";
                errorInfo(note);
            }if (StringUtil.isEmpty(c_gzfez)){
                note = "请输入动火工作负责人!!!";
                errorInfo(note);
            }if (StringUtil.isEmpty(dhfs)){
                note = "请输入动火方式!!!";
                errorInfo(note);
            }else if (s_xkdate == null)
            {
                note = "请输入批准动火开始时间!!!";
                errorInfo(note);
            }else if (s_pzgzjsdate == null){
                note = "请输入批准动火结束时间!!!";
                errorInfo(note);
            }
            if (personMboSet.getMbo(0)!=null)
            {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
                if (s_xkdate.after(date))
                {
                    note = "批准动火开始时间:"+s_xkdate+", 必须小于当前许可时间";
                    errorInfo(note);
                }
                String displayName = personMboSet.getMbo(0).getString("DISPLAYNAME");
                //工作许可人
                mainMbo.setValue("S_XKPERSON",displayName);
                mainMbo.setValue("S_ZZPZDATE",sdf.format(date));
                mainMbo.setValue("STATUS","已许可",11L);
                mainMbo.getThisMboSet().save();

            }


            return 1;


        }

        if (s_xkdate == null)
        {
            note = "请输入许可开始工作时间!!!";
            errorInfo(note);
        }else if (s_pzgzjsdate == null){
            note = "请输入许可结束工作时间!!!";
            errorInfo(note);
        }

        if (!StringUtil.isEmpty(cancel_person))
        {
            if (personMboSet.getMbo(0)!=null)
            {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
                if (s_xkdate.after(date))
                {

                    note = "许可开始工作时间:"+s_xkdate+", 必须小于当前许可时间";
                    errorInfo(note);
                }
                String displayName = personMboSet.getMbo(0).getString("DISPLAYNAME");
                //工作许可人
                mainMbo.setValue("S_XKPERSON",displayName);
                mainMbo.setValue("S_ZZPZDATE",sdf.format(date));
                mainMbo.setValue("STATUS","已许可",11L);
                mainMbo.getThisMboSet().save();

            }
        }else{
            note = "请先进行签发操作!!!";
            errorInfo(note);
        }

        return 1;

    }

    /**
     * 一键结束
     */
    public void ONEKEYJS() throws RemoteException, MXException, ParseException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String username = mainMbo.getUserName();
        String siteid = mainMbo.getString("SITEID");
        String s_xkperson = mainMbo.getString("S_XKPERSON");
        Date s_pzgzjsdate = mainMbo.getDate("S_PZGZJSDATE");
        String wonum = mainMbo.getString("WONUM");

        //动火类型为DHP
        String woeq10 = mainMbo.getString("WOEQ10");
        final String woeqTyep = "DHP";

        //判断工作票对应的工作票是否已经关闭
        if ("PTP".equals(woeq10))
        {
            //获得对应的动火票集合
            MboSetRemote dhWoTicketSet = mainMbo.getMboSet("$S_REPWONUM","WORKORDER","S_REPWONUM='"+wonum+"'");

            if (dhWoTicketSet.getMbo(0)!=null)
            {
                String status = dhWoTicketSet.getMbo(0).getString("STATUS");
                final String status2 = "已结束";
                if (!status2.equals(status))
                {
                    final String note = "请先结束关联的动火工作票!!!";
                    errorInfo(note);
                }

            }
        }




        MboSetRemote personMboSet = mainMbo.getMboSet("$C_JSXKR","PERSON","PERSONID='"+username+"' AND LOCATIONSITE='"+siteid+"'");

        if (!StringUtil.isEmpty(s_xkperson))
        {
            if (personMboSet.getMbo(0)!=null)
            {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
                if (date.after(s_pzgzjsdate))
                {
                    if (woeqTyep.equals(woeq10)){
                        final String note = "批准动火结束时间:"+s_pzgzjsdate+", 必须大于当前结束时间";
                        errorInfo(note);
                    }else{
                        final String note = "许可结束工作时间:"+s_pzgzjsdate+", 必须大于当前结束时间";
                        errorInfo(note);
                    }

                }
                String displayName = personMboSet.getMbo(0).getString("DISPLAYNAME");
                //工作结束许可人
                mainMbo.setValue("C_JSXKR",displayName);
                mainMbo.setValue("C_REGAINSTART",sdf.format(date));
                mainMbo.setValue("STATUS","已结束",11L);
                mainMbo.getThisMboSet().save();

            }
        }else{
            final String note = "请先进行许可操作!!!";
            errorInfo(note);
        }


    }

    /**
     * 一键终结
     */
    public void ONEKEYZJ() throws RemoteException, MXException{
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String username = mainMbo.getUserName();
        String siteid = mainMbo.getString("SITEID");
        String c_jsxkr = mainMbo.getString("C_JSXKR");

        MboSetRemote personMboSet = mainMbo.getMboSet("$CANCEL_PERSON","PERSON","PERSONID='"+username+"' AND LOCATIONSITE='"+siteid+"'");

        if (!StringUtil.isEmpty(c_jsxkr))
        {
            if (personMboSet.getMbo(0)!=null)
            {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
                String displayName = personMboSet.getMbo(0).getString("DISPLAYNAME");
                //工作结束许可人
                mainMbo.setValue("C_ZJXKR",displayName);
                mainMbo.setValue("OPLOG_ZXDATE",sdf.format(date));
                mainMbo.setValue("STATUS","已终结",11L);
                mainMbo.getThisMboSet().save();

            }
        }else{
            final String note = "请先进行结束操作!!!";
            errorInfo(note);
        }

    }

    /**
     * 一键作废
     */
    public void ONEKEYZF() throws RemoteException, MXException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String zfyy = mainMbo.getString("ZFYY");
        if (StringUtil.isEmpty(zfyy))
        {
            final String note = "作废原因不能为空!!!";
            errorInfo(note);
        }else{
            mainMbo.setValue("STATUS","已作废",11L);
            mainMbo.getThisMboSet().save();
        }
    }

    /**
     * 操作错误提示
     * @param info:提示内容
     * @throws RemoteException
     * @throws MXException
     */
    private void errorInfo(String info) throws MXAccessException {
        throw new MXAccessException("WORKORDER", "errortips",new String[]{info});
    }



}