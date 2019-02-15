package com.shuto.mam.app.sr;

import java.rmi.RemoteException;

import psdi.app.workorder.FailureReportSet;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**   
 * @Title: FldTKFRLevelCode.java 
 * @Package com.shuto.mam.app.sr 
 * @Description: TODO(重写psdi.app.ticket.FldTKFRLevelCode) 
 * @author wuqi   
 * @date 2017-7-4 下午09:24:08 
 * @version V1.0   
 */
public class FldTKFRLevelCode extends MAXTableDomain {
	private int frLevel;

	public FldTKFRLevelCode(MboValue mbv) {
		super(mbv);

		String attrName = mbv.getName();
		String numStr = attrName.substring(2, attrName.length() - 4);
		this.frLevel = Integer.parseInt(numStr);
		setKeyMap("FAILURELIST", new String[] { attrName }, new String[] { "failurecode" });
	}

	public void init() throws MXException, RemoteException {
		MboValue thisValue = getMboValue();
		FailureReportSet frs = (FailureReportSet) thisValue.getMbo().getMboSet("FAILUREREPORT");

		frs.register(this.frLevel);
		try {
			if (!frs.isLevelEditable(this.frLevel)) {
				thisValue.setReadOnly(true);
			}
		} catch (MXException e) {
		}
	}

	public void validate() throws MXException, RemoteException {
		try {
			MboValue thisValue = getMboValue();

			if (thisValue.isNull()) {
				return;
			}

			FailureReportSet frs = (FailureReportSet) thisValue.getMbo().getMboSet("FAILUREREPORT");
			MboRemote thisLevel = frs.getMbo(this.frLevel);
			if (thisLevel == null) {
				thisLevel = frs.add();
			}
			MboValue frFcCode = ((Mbo) thisLevel).getMboValue("FAILURECODE");
			String prevCode = frFcCode.getPreviousValue().asString();
			try {
				frFcCode.setValue(thisValue.getString(), 8L);
			} finally {
				frFcCode.setValue(prevCode, 11L);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw new MXApplicationException("system", "ajsdf", t);
		}
	}

	public void action() throws MXException, RemoteException {
		try {
			MboValue thisValue = getMboValue();
			FailureReportSet frs = (FailureReportSet) thisValue.getMbo().getMboSet("FAILUREREPORT");
			MboRemote thisLevel = frs.getMbo(this.frLevel);
			if (thisValue.isNull()) {
				if (thisLevel != null) {
					thisLevel.delete();
				}
			} else {
				System.out.println("=====================" + thisValue.getMbo().getString("FAILURECODE"));
				thisLevel.setValue("FailureCode", thisValue.getMbo().getString("FAILURECODE"), 2L);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new MXApplicationException("system", "ajsdf", t);
		}
	}

	public boolean hasList() {
		try {
			MboValue thisValue = getMboValue();
			return !thisValue.isReadOnly();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboSetRemote ret = null;
		try {
			if (hasList()) {
				MboValue thisValue = getMboValue();
				FailureReportSet frs = (FailureReportSet) thisValue.getMbo().getMboSet("FAILUREREPORT");
				MboRemote thisLevel = frs.getMbo(this.frLevel);
				boolean wasNull = thisLevel == null;
				if (wasNull) {
					thisLevel = frs.add();
				}
				ret = thisLevel.getList("FailureCode");
			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw new MXApplicationException("system", "ajsdf", t);
		}

		return ret;
	}
}