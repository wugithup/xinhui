//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package psdi.app.inbxconfig;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetInfo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValueData;
import psdi.mbo.MboValueInfo;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.MXFormat;
import psdi.workflow.WFAssignmentSetRemote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * 收件箱分类（限于maximo7.6）把此类放入businessobjects.jar中，
 * 并新建应用程序“收件箱过滤器设置”，
 */
public class InbxConfigSet extends MboSet implements InbxConfigSetRemote {
	private Vector inboxColumns;
	private MboValueData[][] keyAttribData = (MboValueData[][]) null;
	private int noOfAssignments = 0;
	private String latestDate = "";
	private String inboxDescTitle = "";

	public InbxConfigSet(MboServerInterface ms) throws MXException, RemoteException {
		super(ms);
	}

	protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
		return new InbxConfig(ms);
	}

	public void setInboxColumns() throws RemoteException, MXException {
		try {
			Hashtable vecAttributes = this.getWFAssignmentColumns();
			int size = vecAttributes.size();
			Enumeration e = vecAttributes.keys();

			for (int var4 = 0; e.hasMoreElements(); ++var4) {
				String key = (String) e.nextElement();
				MboRemote inboxconfigMbo = this.add();
				inboxconfigMbo.setValue("INBXCOLUMN", key);
				inboxconfigMbo.setValue("DESCRIPTION", (String) vecAttributes.get(key));
				inboxconfigMbo.setValue("DISPLAY", false);
			}
		} catch (Throwable var7) {
			var7.printStackTrace();
		}

	}

	public Vector getColumns() throws RemoteException, MXException {
		Vector vecAttributes = new Vector();
		this.setOrderBy("ordernum asc,description asc");
		int i = 0;

		while (true) {
			InbxConfigRemote inbx = (InbxConfigRemote) this.getMbo(i);
			if (inbx == null) {
				return vecAttributes;
			}

			String[] arrColumn = new String[] { inbx.getString("inbxcolumn"), inbx.getString("description") };
			vecAttributes.add(arrColumn);
			++i;
		}
	}

	private Hashtable getWFAssignmentColumns() throws RemoteException, MXException {
		Hashtable attibutes = new Hashtable();
		MboSetRemote msr = MXServer.getMXServer().getMboSet("WFASSIGNMENT", this.getUserInfo());
		MboSetInfo msi = msr.getMboSetInfo();

		String sAttribute;
		String sTitle;
		for (Iterator fields = msi.getAttributes(); fields.hasNext(); attibutes.put(sAttribute, sTitle)) {
			MboValueInfo mvi = (MboValueInfo) fields.next();
			sAttribute = mvi.getAttributeName();
			sTitle = mvi.getTitle();
			if (sTitle == null) {
				sTitle = "";
			}
		}

		return attibutes;
	}

	public MboValueData[][] getAssignments(int start, int rowcount, String sortBy) throws RemoteException, MXException {
		Vector columns = this.getInboxColumns();
		String[] arrColumns = new String[columns.size()];
		String[] keyColumns = new String[] { "description", "app", "assignid", "ownerid", "isownerlocked", "ownerlockedby" };

		for (int i = 0; i < columns.size(); ++i) {
			arrColumns[i] = ((String[]) ((String[]) columns.elementAt(i)))[0];
		}

		WFAssignmentSetRemote wf = (WFAssignmentSetRemote) MXServer.getMXServer().getMboSet("WFASSIGNMENT", this.getUserInfo());
		if (!sortBy.equals("")) {
			wf.setOrderBy(sortBy);
		}

		if (getOwner() != null && getOwner() instanceof psdi.app.scconfig.Layout) {
			psdi.app.scconfig.Layout layout = (psdi.app.scconfig.Layout) getOwner();
			//收件箱名称不为空
			if (!layout.isNull("DESCRIPTION")) {
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet("INBOXFILTERCFG", getUserInfo());
				SqlFormat sql = new SqlFormat("orgid=:1 and siteid=:2 and name=:3");
				sql.setObject(1, "INBOXFILTERCFG", "ORGID", getProfile().getDefaultOrg());
				sql.setObject(2, "INBOXFILTERCFG", "SITEID", getProfile().getDefaultSite());
				sql.setObject(3, "INBOXFILTERCFG", "NAME", layout.getString("DESCRIPTION"));
				mboSet.setWhere(sql.format());
				mboSet.reset();
				if (mboSet.count() > 0) {
					MboRemote mbo = mboSet.getMbo(0);
					String string = mbo.getString("APPWHERE");
					if (!mbo.isNull("APPWHERE")) {
						wf.setAppWhere(string);
					}
				}
				mboSet.close();
			}
		}

		wf.getInboxAssignments();
		this.inboxDescTitle = wf.getMboValueInfoStatic("description").getTitle();
		Date date = wf.latestDate("duedate");
		if (date != null) {
			this.latestDate = MXFormat.dateTimeToString(date, this.getUserInfo().getLocale(), this.getUserInfo().getTimeZone());
		}

		this.noOfAssignments = wf.count();
		MboValueData[][] keyData = wf.getMboValueData(start, rowcount, keyColumns);
		this.keyAttribData = keyData;
		MboValueData[][] mvd = wf.getMboValueData(start, rowcount, arrColumns);
		return mvd;
	}

	public Vector getInboxColumns() throws RemoteException, MXException {
		if (this.inboxColumns != null && !this.inboxColumns.isEmpty()) {
			return this.inboxColumns;
		} else {
			this.inboxColumns = new Vector();
			String sql = "display=:yes";
			SqlFormat sqf = new SqlFormat(sql);
			this.setWhere(sqf.format());
			this.setOrderBy("ordernum asc,description asc");
			this.reset();
			int i = 0;

			while (true) {
				InbxConfigRemote inbx = (InbxConfigRemote) this.getMbo(i);
				if (inbx == null) {
					return this.inboxColumns;
				}

				if (!"description".equalsIgnoreCase(inbx.getString("inbxcolumn"))) {
					String[] arrColumn = new String[] { inbx.getString("inbxcolumn"), inbx.getString("description") };
					this.inboxColumns.add(arrColumn);
				}

				++i;
			}
		}
	}

	public void resetInboxColumns() throws RemoteException, MXException {
		this.inboxColumns = null;
	}

	public MboValueData[][] getKeyAttributeData() throws RemoteException, MXException {
		return this.keyAttribData;
	}

	public int getNoOfAssignments() throws RemoteException, MXException {
		if (this.noOfAssignments == 0) {
			WFAssignmentSetRemote wf = (WFAssignmentSetRemote) MXServer.getMXServer().getMboSet("WFASSIGNMENT", this.getUserInfo());
			wf.getInboxAssignments();
			this.noOfAssignments = wf.count();
			return this.noOfAssignments;
		} else {
			return this.noOfAssignments;
		}
	}

	public String latestDate() throws RemoteException, MXException {
		return this.latestDate;
	}

	public String getInboxDescTitle() throws RemoteException, MXException {
		return this.inboxDescTitle;
	}

	public ArrayList getNonPersistentAttributes() throws RemoteException, MXException {
		ArrayList npAttributes = new ArrayList();
		MboSetRemote msr = MXServer.getMXServer().getMboSet("WFASSIGNMENT", this.getUserInfo());
		MboSetInfo msi = msr.getMboSetInfo();
		Iterator allFields = msi.getAttributes();

		while (allFields.hasNext()) {
			MboValueInfo mvi = (MboValueInfo) allFields.next();
			if (!mvi.isPersistent()) {
				npAttributes.add(mvi.getAttributeName());
			}
		}

		return npAttributes;
	}
}
