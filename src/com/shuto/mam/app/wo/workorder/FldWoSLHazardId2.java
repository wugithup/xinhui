package com.shuto.mam.app.wo.workorder;

import java.rmi.RemoteException;

import psdi.app.workorder.WoSafetyLink;
import psdi.app.workorder.WoSafetyLinkRemote;
import psdi.app.workorder.WoSafetyLinkSet;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldWoSLHazardId2 extends MAXTableDomain {
	public FldWoSLHazardId2(MboValue mbv) throws MXException {
		super(mbv);
		setRelationship("HAZARD", "");
		// try {
		// resetListCriteria();
		// } catch (RemoteException localRemoteException) {
		// }
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String app = getMboValue().getMbo().getThisMboSet().getApp();
		MboRemote mainMbo = getMboValue().getMbo();

		String siteid = mainMbo.getString("SITEID");
		setListCriteria("   haz01 = '" + siteid
				+ "' and    precautionenabled = 1");

		return super.getList();
	}

	public void validate() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		String relName = ((WoSafetyLinkSet) getMboValue().getMbo()
				.getThisMboSet()).getThisRelationName();

		if ((relName == null)
				|| ((!relName.equals("WOSLHAZMATENABLED"))
						&& (!relName.equals("WOSLHAZPRECENABLED")) && (!relName
							.equals("WOSLTAGENABLED")))) {
			return;
		}

		SqlFormat sqf = new SqlFormat(mbo.getUserInfo(),
				"hazardid = :1 and orgid= :2");
		sqf.setObject(1, "HAZARD", "HAZARDID", mbo.getString("hazardid"));
		sqf.setObject(2, "HAZARD", "ORGID", mbo.getString("orgid"));
		MboSetRemote hazSet = ((Mbo) mbo).getMboSet(
				"$checkhazval" + mbo.getString("hazardid"), "HAZARD",
				sqf.format());

		if (hazSet.isEmpty())
			return;
		MboRemote haz = hazSet.getMbo(0);

		if (relName.equals("WOSLHAZMATENABLED")) {
			if (!haz.getBoolean("hazmatenabled"))
				throw new MXApplicationException("workorder",
						"HazardInvalidMat");
		} else if (relName.equals("WOSLHAZPRECENABLED")) {
			if (!haz.getBoolean("precautionenabled"))
				throw new MXApplicationException("workorder",
						"HazardInvalidPrec");
		} else {
			if ((!relName.equals("WOSLTAGENABLED"))
					|| (haz.getBoolean("tagoutenabled")))
				return;
			throw new MXApplicationException("workorder", "HazardInvalidTag");
		}
	}

	public void action() throws MXException, RemoteException {
		super.action();

		MboRemote mbo = getMboValue().getMbo();
		String relName = ((WoSafetyLinkSet) getMboValue().getMbo()
				.getThisMboSet()).getThisRelationName();

		if (!mbo.isNull("hazardid")) {
			SqlFormat sqf = new SqlFormat(mbo.getUserInfo(),
					"wonum = :1 and hazardid = :2 and orgid=:3");
			sqf.setObject(1, "WOHAZARD", "WONUM", mbo.getString("wonum"));
			sqf.setObject(2, "WOHAZARD", "HAZARDID", mbo.getString("hazardid"));
			sqf.setObject(3, "WOHAZARD", "orgid", mbo.getString("orgid"));
			MboSetRemote hazSet = ((Mbo) mbo).getMboSet(
					"$checkhaz" + mbo.getString("hazardid"), "WOHAZARD",
					sqf.format());

			if (hazSet.isEmpty()) {
				SqlFormat sqf2 = new SqlFormat(mbo.getUserInfo(),
						"hazardid = :1 and orgid=:2");
				sqf2.setObject(1, "HAZARD", "HAZARDID",
						mbo.getString("hazardid"));
				sqf2.setObject(2, "HAZARD", "ORGID", mbo.getString("orgid"));
				hazSet = ((Mbo) mbo).getMboSet(
						"$checkhaz2" + mbo.getString("hazardid"), "HAZARD",
						sqf2.format());
			}

			if (!hazSet.isEmpty()) {
				MboRemote haz = hazSet.getMbo(0);
				((WoSafetyLinkRemote) mbo).copyAttributesFromHazard(haz);
			}
		}

		if (relName == null) {
			return;
		}

		MboRemote owner = getMboValue().getMbo().getOwner();

		if ((owner == null) || (!owner.getName().equals("WORKORDER"))) {
			return;
		}

		if (relName.equals("WOSLHAZMATENABLED")) {
			mbo.setValue("hazmatenabled", true, 11L);
		} else if (relName.equals("WOSLHAZPRECENABLED")) {
			mbo.setValue("precautionenabled", true, 11L);
			((WoSafetyLink) mbo).maintainWoHazard();
		} else {
			if (!relName.equals("WOSLTAGENABLED"))
				return;
			mbo.setValue("tagoutenabled", true, 11L);
			((WoSafetyLink) mbo).maintainWoTagout();
		}
	}

	private void resetListCriteria() throws MXException, RemoteException {
		String relName = ((WoSafetyLinkSet) getMboValue().getMbo()
				.getThisMboSet()).getThisRelationName();

		if (relName == null) {
			return;
		}

		MboRemote owner = getMboValue().getMbo().getOwner();

		if ((owner == null) || (!owner.getName().equals("WORKORDER"))) {
			return;
		}

		if (relName.equals("WOSLHAZMATENABLED")) {
			setListCriteria("hazmatenabled = :yes and orgid= :orgid");
		} else if (relName.equals("WOSLHAZPRECENABLED")) {
			setListCriteria("precautionenabled = :yes  and orgid= :orgid");
		} else {
			if (!relName.equals("WOSLTAGENABLED"))
				return;
			setListCriteria("tagoutenabled = :yes  and orgid= :orgid");
		}
	}
}