package com.shuto.mam.webclient.beans.wo.wotrack;

import com.ibm.ism.content.mriu.StringUtil;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.beans.workorder.WOAppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

/**
 * 工单
 *
 * @author 作者 E-mail: xiamy@shuto.cn
 * @version 1.2
 * @date 创建时间：2017年5月19日 上午9:46:00
 * @Copyright: 2017 Shuto版权所有
 * @since
 */
public class WorkorderAppBean extends WOAppBean {
	protected String OWNERTABLE = "";
	protected long OWNERID;

	private Connection con = null;
	private PreparedStatement stmt = null;
	private ConnectionKey key = null;

	@Override
	public int DUPLICATE() throws MXException, RemoteException {
		MboRemote ymbo = this.app.getAppBean().getMbo();
		super.DUPLICATE();
		MboSetRemote LICENCEMAIN = ymbo.getMboSet("$WOMURITEM", "WOMURITEM", "wonum='" + ymbo.getString("wonum") + "'");
		MboRemote mbo = getMbo();// 新mbo
		mbo.setValue("STATUS", "新建", 11L);
		mbo.setValue("S_ZYJHPERSON", "", 11L);
		mbo.setValue("S_JSBFZPERSON", "", 11L);
		mbo.setValue("S_GSLEADPERSON", "", 11L);
		mbo.setValue("S_ZYJHDATE", "", 11L);
		mbo.setValue("S_JSBFZDATE", "", 11L);
		mbo.setValue("S_GSLEADQZDATE", "", 11L);

		if (LICENCEMAIN.count() > 0) {
			MboSetRemote supMsr = mbo.getMboSet("SHOWWOMURITEM");
			for (int i = 0; i < LICENCEMAIN.count(); i++) {
				MboRemote suppleMbo = supMsr.add();
				MboRemote suppleMainMbo = LICENCEMAIN.getMbo(i);
				suppleMbo.setValue("wonum", mbo.getString("wonum"), 11l);
				suppleMbo.setValue("orgid", suppleMainMbo.getString("orgid"), 11l);
				suppleMbo.setValue("siteid", suppleMainMbo.getString("siteid"), 11l);
				suppleMbo.setValue("itemnum", suppleMainMbo.getString("itemnum"), 11l);
				suppleMbo.setValue("erpid", suppleMainMbo.getString("erpid"), 11l);
				suppleMbo.setValue("remark", suppleMainMbo.getString("remark"), 11l);
				suppleMbo.setValue("orgtype", suppleMainMbo.getString("orgtype"), 11l);
				suppleMbo.setValue("ITEMQTY", suppleMainMbo.getString("ITEMQTY"), 11l);
				suppleMbo.setValue("UNITCOST", suppleMainMbo.getString("UNITCOST"), 11l);
				suppleMbo.setValue("LINECOST", suppleMainMbo.getString("LINECOST"), 11l);
				suppleMbo.setValue("REQUIREDATE", suppleMainMbo.getString("REQUIREDATE"), 11l);

			}
		}
		LICENCEMAIN.close();

		this.app.getAppBean().refreshTable();
		this.app.getAppBean().reloadTable();
		return 1;
	}

	public int DELWO() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String wonum = mainMbo.getString("wonum");
		String status = mainMbo.getString("status");
		if (status.equals("等待批准") || status.equals("新建")) {
			if (!hasAuth()) {
				throw new MXApplicationException("system", "noauth");
			}
			try {
				String sql = "delete from relatedrecord where relatedreckey='" + wonum + "' or RECORDKEY='" + wonum + "'";

				String sql2 = "delete from workorder where wonum='" + wonum + "'";
				this.key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
				this.con = MXServer.getMXServer().getDBManager().getConnection(this.key);
				con.setAutoCommit(false);

				this.stmt = con.prepareStatement(sql);
				stmt.execute();
				this.stmt = con.prepareStatement(sql2);
				stmt.execute();
				this.con.commit();
				// this.app.getAppBean().reset();
				this.app.getAppBean().reloadTable();
				// throw new MXApplicationException("system", "已成功刪除工单");

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

		}
		return super.DELETE();
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		if (!hasAuth()) {
			throw new MXApplicationException("system", "noauth");
		}
		// 领料总金额
		mbo.setValue("totalcost", getTotalCost(mbo));
		return super.SAVE();
	}

	@Override
	public int ROUTEWF() throws MXException, RemoteException {
		if (!hasAuth()) {
			throw new MXApplicationException("system", "noauth");
		}
		return super.ROUTEWF();
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

		MboSetRemote wfinstance = mbo
				.getMboSet("instance", "WFINSTANCE", "ownertable='" + this.OWNERTABLE + "' and ownerid='" + this.OWNERID + "' and active = 1");
		boolean noInstance = wfinstance.isEmpty();
		wfinstance.close();


		/*if (noInstance) {//取消只有当前创建用户才有修改的权限
			String createperson = mbo.getString("createperson");
			return personid.equals(createperson);
		}*/

		String sql = "ownerid='" + this.OWNERID + "'" + " and ownertable='" + this.OWNERTABLE + "'" + " and assignstatus='活动'" + " and assigncode='"
				+ personid + "'";
		MboSetRemote mbosetremote = mbo.getMboSet("$assigncode", "WFASSIGNMENT", sql);
		boolean hasAssigncode = mbosetremote.isEmpty();
		mbosetremote.close();
		return true;
	}

	public boolean isSysuser() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		String personid = mbo.getUserInfo().getPersonId();
		MboSetRemote users = mbo.getMboSet("$tmp_maxuser", "maxuser", "sysuser =1 and personid='" + personid + "'");
		boolean isEmpty = (users.getMbo(0) != null) && (users.getMbo(1) == null);
		users.close();
		return isEmpty;
	}

	@Override
	public boolean saveYesNoCheck() throws MXException {
		try {
			if (!hasAuth()) {
				reset();
				this.resetRemote = true;
				return true;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return true;
		}
		return super.saveYesNoCheck();
	}

	@Override
	protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
		// long startTime = System.currentTimeMillis(); // 获取开始时间

		String status = mbo.getString("status");
		mbo.setFlag(MboConstants.READONLY, false);

		long id = mbo.getUniqueIDValue();
		String tableName = mbo.getName().toUpperCase();
		String personid = mbo.getUserInfo().getPersonId();

		MboSetRemote mbosetremote = mbo
				.getMboSet("$instance", "WFINSTANCE", "ownertable='" + tableName + "' and ownerid='" + id + "' and active = 1");
		if (!mbosetremote.isEmpty()) {
			String sql = "ownerid='" + id + "' and ownertable='" + tableName + "' and assignstatus='活动' and assigncode='" + personid + "'";
			MboSetRemote wfassignmentSet = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
			if (!wfassignmentSet.isEmpty()) {
				String processname = wfassignmentSet.getMbo(0).getString("processname");
				String processrev = wfassignmentSet.getMbo(0).getString("processrev");
				String nodeid = wfassignmentSet.getMbo(0).getString("nodeid");
				String[] readonlyStr = WfUtil(processname, processrev, nodeid, "READONLY");
				String[] requiredStr = WfUtil(processname, processrev, nodeid, "REQUIRED");
				if ((readonlyStr != null) && (readonlyStr.length > 0)) {
					mbo.setFieldFlag(readonlyStr, MboConstants.READONLY, true);
				}
				if ((requiredStr != null) && (requiredStr.length > 0)) {
					mbo.setFieldFlag(requiredStr, MboConstants.REQUIRED, true);
				}
			} else {
				mbo.setFlag(7L, true);
			}
			wfassignmentSet.close();
		}
		mbosetremote.close();

		if (("已关闭".equals(status)) || ("已作废".equals(status)) || ("已取消".equals(status))) {
			mbo.setFlag(MboConstants.READONLY, true);
		}
		super.setCurrentRecordData(mbo);
		// long endTime = System.currentTimeMillis(); // 获取结束时间
		// System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
	}

	private String[] WfUtil(String processname, String processrev, String nodeid, String str) throws RemoteException, MXException {
		MboSetRemote accessControlSet = getMbo().getMboSet("$ACCESSCONTROL", "ACCESSCONTROL",
				"PROCESSNAME = '" + processname + "'AND PROCESSREV = " + processrev + "  AND NODEID = " + nodeid + " and ISBTORZD = '" + str + "'");

		String[] rdStr = null;
		if (!accessControlSet.isEmpty()) {
			rdStr = accessControlSet.getMbo(0).getString("description").split(",");
		}
		accessControlSet.close();
		return rdStr;
	}


	public int OPENREPORT() {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote reportSet = null;
			if (mbo == null) {
				String mboname = this.app.getAppBean().getMboName();
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname, MXServer.getMXServer().getSystemUserInfo());
				mbo = mboSet.getMbo(0);
			}
			reportSet = mbo.getMboSet("RQREPORT");
			if (reportSet.getMbo(0) != null && reportSet.getMbo(1) == null) {
				MboRemote reportMbo = reportSet.getMbo(0);
				String dialogID = reportMbo.getString("DIALOGID");
				String rqreportname = reportMbo.getString("RQREPORTNAME");// 报表名
				String rqreportnum = reportMbo.getString("rqreportnum");// 报表编号
				// String remark = reportMbo.getString("REMARK"); //备注
				if (!"".equals(dialogID)) {
					this.clientSession.setAttribute("rqreportname", rqreportname);
					this.clientSession.setAttribute("rqreportnum", rqreportnum);
					// this.clientSession.setAttribute("remark", remark);
					this.clientSession.loadDialog(dialogID);
					return 0;
				}
				StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
				url.append("/" + rqreportname.toLowerCase() + ".rpx");
				MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM", "rqreportnum = '" + rqreportnum + "'");
				if (!rqreportset.isEmpty()) {
					for (int i = 0; i < rqreportset.count(); i++) {
						url.append("&");
						url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
						url.append("=");
						url.append(mbo.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
					}

				}
				rqreportset.close();
				this.app.openURL(url.toString(), true);
			} else if (reportSet.count() > 1) {
				this.clientSession.loadDialog("RQREPORT1");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 创建工作票
	 *
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 */
	public int CREATETICK() throws MXException, RemoteException {

        String gzfzr = getString("GZFZR");
		canCreateWoTicket(getString("STATUS"), "创建工作票");
		//String startDate = getDate("SCHEDSTART").toString();
		//String endDate = getDate("SCHEDFINISH").toString();
		String orderType = getString("S_ORDERTYPE");
		String teamNum = getString("TEAMNUM");
		String workCy = getString("S_WORKCY");
		if(gzfzr.isEmpty()||orderType.isEmpty()||orderType.isEmpty()||teamNum.isEmpty()||workCy.isEmpty()){
			throw new MXApplicationException("系统提示", "请完整填写开票信息");
		}else{
			MboRemote mainMbo = this.app.getAppBean().getMbo();
			MboSetRemote gzpSet = mainMbo.getMboSet("RELATEDWT");
			if(gzpSet.count()>0){
				WebClientEvent event = clientSession.getCurrentEvent();
				int msgRet = event.getMessageReturn();
				String message = " 该工单已有工作票，是否继续申请开票";

				if (msgRet < 0) {
					// 弹出提示窗口问是否继续
					throw new MXApplicationYesNoCancelException("BMXAA9896E", "system",
							"taskxf", new String[] { message });
				}
				if (msgRet == 8) {
					CREATEWOTICKET();
				}
			}else{
				CREATEWOTICKET();
			}
			
			
		}
		
		return 1;
	}

	private int getInt(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 是否可以创建工作票
	 *
	 * @throws RemoteException
	 * @throws MXException
	 */
	private void canCreateWoTicket(String status, String m1) throws RemoteException, MXException {
		MboRemote mbo = this.app.getAppBean().getMbo();
		String personid = mbo.getUserInfo().getPersonId();
		this.OWNERTABLE = getMbo().getName();
		this.OWNERID = getMbo().getUniqueIDValue();
		if ("已关闭".equalsIgnoreCase(status)) {
			throw new MXApplicationException("system", "gzjxz");
		}
//		MboSetRemote personSet = mbo.getMboSet("$WFASSIGNMENT", "WFASSIGNMENT",
//				"ownertable = '" + OWNERTABLE + "' And ownerid = '" + OWNERID + "' And assignstatus='活动'and assigncode='" + personid + "'");
//
//		boolean istrue = personSet.isEmpty();
//		personSet.close();
//		if (istrue) {
//			throw new MXApplicationException("system", "gzfzrcj");
//		}
	}

	/**
	 * @throws MXException
	 * @throws RemoteException
	 */
    private void CREATEWOTICKET() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String siteId = mainMbo.getString("SITEID");
        //项目负责人编码
        String lead = mainMbo.getString("LEAD");
        MboRemote prfessionMbo = mainMbo.getMboSet("PROFESSION").getMbo(0);
		String zy = prfessionMbo.getString("description");
        //工作负责人编码
        String gzfzr = getString("GZFZR");

        //获取人员信息数据
        MboSetRemote personMboSet = null;

		WebClientEvent event = this.sessionContext.getCurrentEvent();
		DataBean appBean = Utility.getDataSource(this.sessionContext, this.app.getAppHandler());
		String siteid = mainMbo.getString("siteid");
		try {
			appBean.save();
			MboRemote wo = null;
			String woticketId = "";
			MboSetRemote mboSet = mainMbo.getMboSet("showorder");
			String wtdescription = "由工单" + mainMbo.getString("WONUM") + "创建的工作票";
			
			wo = mboSet.add();
			wo.setValue("worktype", "工作票");
			wo.setValue("C_FJCZBWHRY", zy);
			wo.setValue("description", getString("description"), 2L);
			wo.setValue("location", getString("location"), 2L);
			if (!StringUtil.isEmpty(lead))
            {
                personMboSet = mainMbo.getMboSet("$LEAD","PERSON","PERSONID = '"+lead+"' AND LOCATIONSITE = '"+siteId+"'");
                if (!personMboSet.isEmpty())
                {
                    wo.setValue("C_QFR", personMboSet.getMbo(0).getString("DISPLAYNAME"), 11L);
                }
            }
            personMboSet = mainMbo.getMboSet("$LEAD","PERSON","PERSONID = '"+gzfzr+"' AND LOCATIONSITE = '"+siteId+"'");
            if (!personMboSet.isEmpty())
            {
                wo.setValue("C_GZFEZ", personMboSet.getMbo(0).getString("DISPLAYNAME"), 11L);
            }
			wo.setValue("assetnum", getString("assetnum"),2L);
			wo.setValue("TEAMNAME", getString("DEPARTMENT"), 11L);
			wo.setValue("parentchgsstatus", false);
			wo.setValue("s_profession", getString("profession"), 11L);
			wo.setValue("parent", getString("wonum"), 11L);
			wo.setValue("isgls", false, 11L);
			wo.setValue("WOJO1", getString("WOJO1"), 11L);// 专业描述
            wo.setValue("WOEQ10", "PTP", 11L);
            MboRemote locMbo = wo.getMboSet("LOCATION").getMbo(0);
            String locDesc = locMbo.getString("description");
            wo.setValue("OPLOG_DELAYCAUSE", locDesc,11L);
            wo.setValue("S_ORDERTYPE", getString("S_ORDERTYPE"));
            wo.setValue("TEAMNUM", getString("TEAMNUM"));
            wo.setValue("S_WORKCY", getString("S_WORKCY"));
            wo.setValue("S_WORKCYQTY", getString("S_WORKCYQTY"));
            wo.setValue("S_XKDATE", getDate("SCHEDSTART"));
            wo.setValue("S_PZGZJSDATE", getDate("SCHEDFINISH"));
            //MboRemote assetMbo = wo.getMboSet("ASSET").getMbo(0);
            //System.out.println("---------------------------"+assetMbo.getName());
            //String assetDesc = assetMbo.getString("description");
            //wo.setValue("WORKSITE", assetDesc,11L);

			MboSetRemote msr = mainMbo.getMboSet("RELATEDWT"); // 查询关联工单
			msr.reset();
			MboRemote mr1 = msr.add();
			mr1.setValue("RECORDKEY", mainMbo.getString("WONUM"), 11L);
			mr1.setValue("CLASS", "工单", 11L);
			mr1.setValue("RELATEDRECKEY", wo.getString("WONUM"), 11L);
			mr1.setValue("RELATEDRECCLASS", "工单", 11L);
			mr1.setValue("RELATETYPE", "相关", 11L);
			mr1.setValue("SITEID", mainMbo.getString("SITEID"), 11L);
			mr1.setValue("ORGID", mainMbo.getString("ORGID"), 11L);
			mr1.setValue("RELATEDRECSITEID", mainMbo.getString("SITEID"), 11L);
			mr1.setValue("RELATEDRECORGID", mainMbo.getString("ORGID"), 11L);
			msr.save();
			woticketId = wo.getUniqueIDValue() + "";
			mboSet.save();
			appBean.save();
			appBean.reset();
			appBean.next();
			this.sessionContext.queueRefreshEvent();
			this.sessionContext.queueRefreshEvent();
			WebClientSession wcs = event.getWebClientSession();
			String additionalEvent = event.getAdditionalEvent();
			String additionalEventValue = event.getAdditionalEventValue();

			String queryString = "?event=gotoapp&value=hdwoticket";
			if (!WebClientRuntime.isNull(additionalEvent)) {
				queryString = queryString + "&additionalevent=" + additionalEvent;
				if (!WebClientRuntime.isNull(additionalEventValue)) {
					queryString = queryString + "&additionaleventvalue=" + additionalEventValue;
				}
			}
			queryString = queryString + "&uniqueid=" + woticketId;
			wcs.getCurrentApp().put("forcereload", "true");
			wcs.gotoApplink(queryString);
			event.cancelRender();
		} catch (MXException mxe) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
		}
	}

	/**
	 * 创建工单补料单
	 *
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 */
	public int CREATEWOMUR() throws MXException, RemoteException {
		WebClientEvent localWebClientEvent = this.sessionContext.getCurrentEvent();
		DataBean localDataBean = Utility.getDataSource(this.sessionContext, this.app.getAppHandler());
		MboRemote woMbo = this.app.getAppBean().getMbo();
		// 工单创建人映射的ERP部门编号
		String wodepartnum = woMbo.getString("CREATEPERSON.DEPARTMENT.ERPDEPNUM");
		try {
			localDataBean.save();
			String msg = canCreateWomur(woMbo);
			if (msg != null && !"".equals(msg)) {
				throw new MXApplicationException("workorder", msg);
			}
			woMbo.setFlag(MboConstants.READONLY, false);
			MboSetRemote womurMboSet = woMbo.getMboSet("WOMURS");
			MboRemote womurMbo = womurMboSet.add();
			// 补料单创建人映射的ERP部门编号
			String womurdepartnum = womurMbo.getString("CREATEPERSON.DEPARTMENT.ERPDEPNUM");
			String womurdesc = "由工单" + woMbo.getString("wonum") + "创建的补料单(" + womurMbo.getString("wonum") + ")";
			womurMbo.setValue("wol2", woMbo.getString("wonum"), 11L);
			womurMbo.setValue("worktype", "工单领料", 11L);
			womurMbo.setValue("description", womurdesc, 11L);
			womurMbo.setValue("interfaceorgtype", woMbo.getString("interfaceorgtype"), 11L);
			if (wodepartnum.equals(womurdepartnum)) {
				womurMbo.setValue("dept_line_id", woMbo.getString("dept_line_id"), 11L);
				womurMbo.setValue("project_code", woMbo.getString("project_code"), 11L);
				womurMbo.setValue("task_id", woMbo.getString("task_id"), 11L);
				womurMbo.setValue("prod_code", woMbo.getString("prod_code"), 11L);
				womurMbo.setValue("c_jizu", woMbo.getString("c_jizu"), 11L);
			}
			womurMbo.setValue("s_erpprofession", woMbo.getString("s_erpprofession"), 11L);

			String womurid = womurMbo.getUniqueIDValue() + "";
			womurMboSet.save();

			WebClientSession localWebClientSession = localWebClientEvent.getWebClientSession();
			String str4 = localWebClientEvent.getAdditionalEvent();
			String str5 = localWebClientEvent.getAdditionalEventValue();

			String str6 = "?event=gotoapp&value=wo_mur";
			if (!WebClientRuntime.isNull(str4)) {
				str6 = str6 + "&additionalevent=" + str4;

				if (!WebClientRuntime.isNull(str5)) {
					str6 = str6 + "&additionaleventvalue=" + str5;
				}
			}
			str6 = str6 + "&uniqueid=" + womurid;

			localWebClientSession.getCurrentApp().put("forcereload", "true");
			localWebClientSession.gotoApplink(str6);
			localWebClientEvent.cancelRender();
		} catch (MXException localMXException) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), localMXException);
		}
		return 1;
	}

	/**
	 * 获取工单物料总金额
	 *
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private double getTotalCost(MboRemote wo) throws RemoteException, MXException {
		double totalcost = 0.0D;
		DataBean wpmaterials = this.app.getDataBean("plans_materials_table");
		if (wpmaterials != null) {
			for (int i = 0; i < wpmaterials.count(); i++) {
				if (!wpmaterials.getMbo(i).toBeDeleted()) {
					totalcost += wpmaterials.getMbo(i).getDouble("linecost");
				}
			}
		} else {
			totalcost = wo.getDouble("totalcost");
		}
		return totalcost;
	}

	/**
	 * 能否创建工单补料单
	 *
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private String canCreateWomur(MboRemote wo) throws RemoteException, MXException {
		String msg = "";
		// 专业
		String zy = wo.getString("PROFESSION");
		// 状态
		String status = wo.getString("STATUS");
		String[] statusarry = new String[] { "工作进行中" };
		Arrays.sort(statusarry);
		if (Arrays.binarySearch(statusarry, status) < 0) {
			msg = "当前状态下不允许创建工单补料单!";
		} else if (zy == null || "".equals(zy)) {
			msg = "专业为空!";
		}
		return msg;
	}

}