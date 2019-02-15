package com.shuto.mam.webclient.beans.stpdindicator;

import com.ibm.icu.text.SimpleDateFormat;
import com.shuto.mam.app.stpdindicator.stpdindicatorRemote;
import com.shuto.mam.webclient.beans.base.ProPerty;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.servicedesk.TicketAppBean;
/**
 * 生产指标录入
 com.shuto.mam.webclient.beans.stpdindicator.StpdindicatorAppBean 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:31:44
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class StpdindicatorAppBean extends TicketAppBean
{
  public int SAVE()throws MXException, RemoteException
  {
	  if (!hasAuth()) {
			throw new MXApplicationException("system", "noauth");
		}
    stpdindicatorRemote tmbo = (stpdindicatorRemote)getMbo();
    tmbo.save();
    return super.SAVE();
  }

  protected String OWNERTABLE = "";
	protected long OWNERID;

	public int DELETE() throws MXException, RemoteException {
		if (!hasAuth()) {
			throw new MXApplicationException("system", "noauth");
		}
		return super.DELETE();
	}

  public int INSERT() throws MXException, RemoteException {
    super.INSERT();
    Date cdate = new Date();
    SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.setTime(cdate);
    cal.add(5, -1);
    setValue("tjdate", fdate.format(cal.getTime()));
    return 1;
  }
  public int ROUTEWF() throws MXException, RemoteException {
		if (!hasAuth())
			throw new MXApplicationException("system", "noauth");
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
		this.OWNERTABLE = getMbo().getUniqueIDName();
		this.OWNERID = getMbo().getUniqueIDValue();

		MboSetRemote wfinstance = mbo.getMboSet("instance", "WFINSTANCE",
				"ownertable='" + this.OWNERTABLE + "' and ownerid='" + this.OWNERID + "' and active = 1");
		boolean noInstance = wfinstance.isEmpty();
		wfinstance.close();

		if (noInstance) {
			String createperson = mbo.getString("createperson");
			return personid.equals(createperson);
		}
		String sql = "ownerid='" + this.OWNERID + "'" + " and ownertable='" + this.OWNERTABLE + "'"
				+ " and assignstatus='活动'" + " and assigncode='" + personid + "'";
		MboSetRemote mbosetremote = mbo.getMboSet("$assigncode", "WFASSIGNMENT", sql);
		boolean hasAssigncode = mbosetremote.isEmpty();
		mbosetremote.close();
		return !hasAssigncode;
	}

	public boolean isSysuser() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		String personid = mbo.getUserInfo().getPersonId();
		MboSetRemote users = mbo.getMboSet("$tmp_maxuser", "maxuser", "sysuser =1 and personid='" + personid + "'");
		boolean isEmpty = (users.getMbo(0) != null) && (users.getMbo(1) == null);
		users.close();
		return isEmpty;
	}

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

	protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
		String status = mbo.getString("status");
		mbo.setFlag(7L, false);

		long id = mbo.getUniqueIDValue();
		String tableName = mbo.getName().toUpperCase();
		String personid = mbo.getUserInfo().getPersonId();

		MboSetRemote mbosetremote = mbo.getMboSet("$instance", "WFINSTANCE",
				"ownertable='" + tableName + "' and ownerid='" + id + "' and active = 1");
		if (!mbosetremote.isEmpty()) {
			String sql = "ownerid='" + id + "' and ownertable='" + tableName
					+ "' and assignstatus='活动' and assigncode='" + personid + "'";
			MboSetRemote wfassignmentSet = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
			if (!wfassignmentSet.isEmpty()) {
				String processname = wfassignmentSet.getMbo(0).getString("processname");
				String processrev = wfassignmentSet.getMbo(0).getString("processrev");
				String nodeid = wfassignmentSet.getMbo(0).getString("nodeid");
				String[] readonlyStr = WfUtil(processname, processrev, nodeid, "READONLY");
				String[] requiredStr = WfUtil(processname, processrev, nodeid, "REQUIRED");
				if ((readonlyStr != null) && (readonlyStr.length > 0)) {
					mbo.setFieldFlag(readonlyStr, 7L, true);
				}
				if ((requiredStr != null) && (requiredStr.length > 0))
					mbo.setFieldFlag(requiredStr, 128L, true);
			} else {
				mbo.setFlag(7L, true);
			}
		}

		if (("已关闭".equals(status)) || ("CLOSE".equals(status)) || ("CLOSED".equals(status)) || ("CAN".equals(status))) {
			mbo.setFlag(7L, true);
		}
		super.setCurrentRecordData(mbo);
	}

	private String[] WfUtil(String processname, String processrev, String nodeid, String str)
			throws RemoteException, MXException {
		MboSetRemote accessControlSet = getMbo().getMboSet("$ACCESSCONTROL", "ACCESSCONTROL",
				"PROCESSNAME = '" + processname + "'AND PROCESSREV = " + processrev + "  AND NODEID = " + nodeid
						+ " and ISBTORZD = '" + str + "'");

		String[] rdStr = new String[0];
		if (!accessControlSet.isEmpty()) {
			rdStr = accessControlSet.getMbo(0).getString("description").split(",");
		}
		accessControlSet.close();
		return rdStr;
	}

	public int RQREPORT() {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote reportSet = null;
			if (mbo == null) {
				String mboname = this.app.getAppBean().getMboName();
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
						MXServer.getMXServer().getSystemUserInfo());
				mbo = mboSet.getMbo(0);
			}
			reportSet = mbo.getMboSet("RQREPORT");
			if (reportSet.count() == 1) {
				MboRemote reportMbo = reportSet.getMbo(0);
				String rqreportname = reportMbo.getString("RQREPORTNAME");
				String wherepara = reportMbo.getString("WHEREPARA");
				StringBuffer urlbf = new StringBuffer();
				ProPerty getrqurl = new ProPerty();
				System.out.println("getrqurl = " + getrqurl.toString());
				String rqurl = getrqurl.getProperty("rq.url");
				urlbf.append(rqurl);
				System.out.println("urlbf = " + urlbf.toString());
				if (!"".equals(wherepara)) {
					String[] where = wherepara.split(",");
					urlbf.append(rqreportname);
					for (int j = 0; j < where.length; ++j) {
						String[] whereson = where[j].split("=:");
						urlbf.append("&").append(whereson[0]).append("=").append(mbo.getString(whereson[1]));
					}
				} else {
					urlbf.append(rqreportname);
				}
				this.app.openURL(urlbf.toString(), true);
			} else if (reportSet.count() > 1) {
				this.clientSession.loadDialog("RQREPORT");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}
}