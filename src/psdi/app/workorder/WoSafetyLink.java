package psdi.app.workorder;

import psdi.app.safety.Hazard;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueInfo;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class WoSafetyLink extends Mbo implements WoSafetyLinkRemote {
	private String[] tagoutAttrs = { "description", "description_longdescription", "assetnum", "location",
			"assetdescription", "assetdescription_longdescription", "requiredstate", "TAG01", "TAG02", "TAG03", "TAG04",
			"TAG05", "TAG06", "TAG07", "TAG08" };

	private String[] woslTagoutAttrs = { "tagoutdescription", "tagoutdescription_longdescription", "tagoutassetnum",
			"tagoutlocation", "tagoutassetdescription", "tagoutassetdescription_longdescription", "requiredstate",
			"TAG01", "TAG02", "TAG03", "TAG04", "TAG05", "TAG06", "TAG07", "TAG08" };

	private String[] hazardAttrs = { "description", "description_longdescription", "hazardtype", "hazmatenabled",
			"precautionenabled", "tagoutenabled", "msdsnum", "healthrating", "flammabilityrating", "reactivityrating",
			"contactrating", "HAZ01", "HAZ02", "HAZ03", "HAZ04", "HAZ05", "HAZ06", "HAZ07", "HAZ08", "HAZ09", "HAZ10",
			"HAZ11", "HAZ12", "HAZ13", "HAZ14", "HAZ15", "HAZ16", "HAZ17", "HAZ18", "HAZ19", "HAZ20" };

	private String[] woslHazardAttrs = { "hazarddescription", "hazarddescription_longdescription", "hazardtype",
			"hazmatenabled", "precautionenabled", "tagoutenabled", "msdsnum", "healthrating", "flammabilityrating",
			"reactivityrating", "contactrating", "HAZ01", "HAZ02", "HAZ03", "HAZ04", "HAZ05", "HAZ06", "HAZ07", "HAZ08",
			"HAZ09", "HAZ10", "HAZ11", "HAZ12", "HAZ13", "HAZ14", "HAZ15", "HAZ16", "HAZ17", "HAZ18", "HAZ19",
			"HAZ20" };

	private MboRemote woHazToUpdate = null;

	private MboRemote woTagToUpdate = null;

	public WoSafetyLink(MboSet ms) throws MXException, RemoteException {
		super(ms);
	}

	public void init() throws MXException {
		super.init();
		try {
			setPropagateKeyFlag(false);

			setValue("hazmatenabled", false, 2L);
			setValue("precautionenabled", false, 2L);
			setValue("tagoutenabled", false, 2L);

			MboRemote owner = getOwner();

			if ((owner == null) || ((!owner.isBasedOn("WORKORDER")) && (!owner.isBasedOn("WOSAFETYLINK"))
					&& (!owner.isBasedOn("WOHAZARD")) && (!owner.isBasedOn("WOTAGOUT")))) {
				throw new MXApplicationException("workorder", "ownerWoSafetyLink");
			}

			String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

			if (toBeAdded()) {
				if ((owner != null) && (owner.isBasedOn("WORKORDER")) && (relName != null)) {
					if (relName.equals("WOSLHAZPRECENABLED"))
						setValue("precautionenabled", true, 11L);
					else if (relName.equals("WOSLHAZMATENABLED"))
						setValue("hazmatenabled", true, 11L);
					else if (relName.equals("WOSLTAGENABLED"))
						setValue("tagoutenabled", true, 11L);
				} else if ((owner != null) && (owner.isBasedOn("WOSAFETYLINK")) && (relName != null)) {
					if (relName.equals("WOSAFETYLINKTAG"))
						setValue("tagoutenabled", true, 11L);
				}
			} else {
				setAssetDescription(false);
				setWoHazardInfo();
				setWoTagoutInfo();

				if ((owner != null) && (owner.isBasedOn("WORKORDER")) && (relName != null)) {
					if ((getString("wosafetydatasource").equals("WP")) && ((relName.equals("WOSLHAZPRECENABLED"))
							|| (relName.equals("WOSLHAZMATENABLED")) || (relName.equals("WOSLTAGENABLED")))) {
						setFlag(7L, true);
					} else {
						if (getTranslator().toInternalString("WOSTATUS", owner.getString("STATUS")).equals("WAPPR")) {
							String[] readOnly = { "wonum", "wosafetylinkid", "wosafetydatasource", "hazardid",
									"hazarddescription", "tagoutid", "assetdescription", "tagoutassetdescription",
									"tagoutassetnum", "tagoutlocation" };
							setFieldFlag(readOnly, 7L, true);
						} else {
							String[] readOnly = { "wonum", "wosafetylinkid", "wosafetydatasource", "hazardid",
									"hazarddescription", "tagoutid", "assetnum", "location", "assetdescription",
									"tagoutassetdescription", "tagoutassetnum", "tagoutlocation" };
							setFieldFlag(readOnly, 7L, true);
						}

						try {
							((WO) owner).canEditRelatedSet(relName);
						} catch (Exception e2) {
							setFlag(7L, true);
						}
					}
				} else {
					String[] readOnly = { "wonum", "wosafetylinkid", "wosafetydatasource", "hazardid", "tagoutid",
							"assetnum", "location", "assetdescription", "tagoutassetdescription", "tagoutassetnum",
							"tagoutlocation" };
					setFieldFlag(readOnly, 7L, true);

					if ((owner != null) && (owner.isBasedOn("WOSAFETYLINK"))) {
						MboRemote owner2 = owner.getOwner();

						if ((owner2 != null) && (owner2.isBasedOn("WORKORDER"))) {
							try {
								((WO) owner2).canEditRelatedSet(relName);
							} catch (Exception e2) {
								setFlag(7L, true);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void setAssetDescription(boolean tagout) throws MXException, RemoteException {
		MboSetRemote assetSet = null;
		MboRemote assetMbo = null;

		if (!tagout) {
			if (!isNull("assetnum")) {
				SqlFormat sqf = new SqlFormat(getUserInfo(), "assetnum = :1 and siteid=:2");
				sqf.setObject(1, "asset", "assetnum", getString("assetnum"));
				sqf.setObject(2, "asset", "siteid", getString("siteid"));
				assetSet = getMboSet("$getasset", "asset", sqf.format());
			} else if (!isNull("location")) {
				SqlFormat sqf = new SqlFormat(getUserInfo(), "location = :1 and siteid=:2");
				sqf.setObject(1, "locations", "location", getString("location"));
				sqf.setObject(2, "locations", "siteid", getString("siteid"));
				assetSet = getMboSet("$getlocation", "locations", sqf.format());
			} else {
				setValueNull("assetdescription", 11L);
				setValueNull("assetdescription_longdescription", 11L);
				return;
			}

			if ((assetSet != null) && (!assetSet.isEmpty())) {
				assetMbo = assetSet.getMbo(0);
			}
			if (assetMbo != null) {
				setValue("assetdescription", assetMbo.getString("description"), 11L);
				setValue("assetdescription_longdescription", assetMbo.getString("description_longdescription"), 11L);
			}
		} else {
			if (!isNull("tagoutassetnum")) {
				SqlFormat sqf = new SqlFormat(getUserInfo(), "assetnum = :1 and siteid=:2");
				sqf.setObject(1, "asset", "assetnum", getString("tagoutassetnum"));
				sqf.setObject(2, "asset", "siteid", getString("siteid"));
				assetSet = getMboSet("$gettagasset", "asset", sqf.format());
			} else if (!isNull("tagoutlocation")) {
				SqlFormat sqf = new SqlFormat(getUserInfo(), "location = :1 and siteid=:2");
				sqf.setObject(1, "locations", "location", getString("tagoutlocation"));
				sqf.setObject(2, "locations", "siteid", getString("siteid"));
				assetSet = getMboSet("$gettagloc", "locations", sqf.format());
			} else {
				setValueNull("tagoutassetdescription", 11L);
				setValueNull("tagoutassetdescription_longdescription", 11L);
				return;
			}

			if ((assetSet != null) && (!assetSet.isEmpty())) {
				assetMbo = assetSet.getMbo(0);
			}
			if (assetMbo != null) {
				setValue("tagoutassetdescription", assetMbo.getString("description"), 11L);
				setValue("tagoutassetdescription_longdescription", assetMbo.getString("description_longdescription"),
						11L);
			}
		}
	}

	private void setWoHazardInfo() throws MXException, RemoteException {
		if (isNull("hazardid")) {
			for (int xx = 0; xx < this.woslHazardAttrs.length; xx++) {
				if (getThisMboSet().getMboSetInfo().getMboValueInfo(this.woslHazardAttrs[xx]).getMaxType()
						.equals("YORN"))
					setValue(this.woslHazardAttrs[xx], false, 2L);
				else {
					setValueNull(this.woslHazardAttrs[xx], 2L);
				}
			}
			return;
		}

		MboRemote owner = getOwner();
		MboRemote hazardMbo = null;

		if ((owner != null) && (owner.isBasedOn("WOHAZARD"))) {
			hazardMbo = owner;
		} else {
			MboSetRemote hazardSet = getMboSet("WOHAZARD");

			if ((hazardSet != null) && (!hazardSet.isEmpty())) {
				hazardMbo = hazardSet.getMbo(0);
			}
		}
		if (hazardMbo != null) {
			copyAttributesFromHazard(hazardMbo);
		}
	}

	private void setWoTagoutInfo() throws MXException, RemoteException {
		if (isNull("tagoutid")) {
			for (int xx = 0; xx < this.woslTagoutAttrs.length; xx++) {
				if (getThisMboSet().getMboSetInfo().getMboValueInfo(this.woslTagoutAttrs[xx]).getMaxType()
						.equals("YORN"))
					setValue(this.woslTagoutAttrs[xx], false, 2L);
				else {
					setValueNull(this.woslTagoutAttrs[xx], 2L);
				}
			}
			return;
		}

		MboRemote owner = getOwner();
		MboRemote tagMbo = null;

		if ((owner != null) && (owner.isBasedOn("WOTAGOUT"))) {
			tagMbo = owner;
		} else {
			MboSetRemote tagSet = getMboSet("WOTAGOUT");

			if ((tagSet != null) && (!tagSet.isEmpty())) {
				tagMbo = tagSet.getMbo(0);
			}
		}
		if (tagMbo != null) {
			copyAttributesFromTagout(tagMbo);
		}
	}

	public void copyAttributesFromHazard(MboRemote hazardMbo) throws MXException, RemoteException {
		for (int xx = 0; xx < this.woslHazardAttrs.length; xx++) {
			setValue(this.woslHazardAttrs[xx], hazardMbo.getString(this.hazardAttrs[xx]), 11L);
		}
	}

	public void copyAttributesFromTagout(MboRemote tagMbo) throws MXException, RemoteException {
		for (int xx = 0; xx < this.woslTagoutAttrs.length; xx++) {
			setValue(this.woslTagoutAttrs[xx], tagMbo.getString(this.tagoutAttrs[xx]), 11L);
		}
	}

	public void add() throws MXException, RemoteException {
		MboRemote owner = getOwner();

		super.add();

		setValue("wonum", owner.getString("wonum"), 11L);
		setValue("siteid", owner.getString("siteid"), 11L);
		setValue("orgid", owner.getString("orgid"), 11L);

		setFieldFlag("wonum", 7L, true);

		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

		if (relName != null) {
			if (relName.equals("WOSLHAZPRECENABLED"))
				setValue("precautionenabled", true, 2L);
			else if (relName.equals("WOSLHAZMATENABLED"))
				setValue("hazmatenabled", true, 2L);
			else if (relName.equals("WOSLTAGENABLED")) {
				setValue("tagoutenabled", true, 2L);
			}
		}
		if (owner.isBasedOn("WOSAFETYLINK")) {
			setValue("hazardid", owner.getString("hazardid"), 11L);
			setValue("wosafetydatasource", owner.getString("wosafetydatasource"), 11L);
			setValue("assetnum", owner.getString("assetnum"), 11L);
			setValue("location", owner.getString("location"), 11L);
			setValue("assetdescription", owner.getString("assetdescription"), 11L);
			setFieldFlag("hazardid", 7L, true);
			setFieldFlag("assetnum", 7L, true);
			setFieldFlag("location", 7L, true);
			setFieldFlag("assetdescription", 7L, true);
		} else if (owner.isBasedOn("WOHAZARD")) {
			for (int xx = 0; xx < this.woslHazardAttrs.length; xx++) {
				setValue(this.woslHazardAttrs[xx], owner.getString(this.hazardAttrs[xx]), 11L);
			}

			setValue("hazardid", owner.getString("hazardid"), 11L);
			setValue("assetnum", owner.getString("assetnum"), 11L);
			setValue("location", owner.getString("location"), 11L);
			setValue("assetdescription", owner.getString("assetdescription"), 11L);
			setValue("assetdescription_longdescription", owner.getString("assetdescription_longdescription"), 11L);

			setValue("wosafetydatasource", owner.getString("wosafetydatasource"), 11L);

			setFieldFlag("hazardid", 7L, true);
			setFieldFlag("assetnum", 7L, true);
			setFieldFlag("location", 7L, true);
			setFieldFlag("assetdescription", 7L, true);

			setValue("wosafetydatasource", owner.getString("wosafetydatasource"), 11L);
		} else if (owner.isBasedOn("WOTAGOUT")) {
			setValue("hazardid", owner.getString("hazardid"), 11L);
			setValue("tagoutid", owner.getString("tagoutid"), 11L);
			setValue("wosafetydatasource", owner.getString("wosafetydatasource"), 11L);
			setFieldFlag("hazardid", 7L, true);
			setFieldFlag("tagoutid", 7L, true);

			MboRemote tagoutowner = owner.getOwner();

			if ((tagoutowner != null)
					&& ((tagoutowner.isBasedOn("WOHAZARD")) || (tagoutowner.isBasedOn("WOSAFETYLINK")))) {
				for (int xx = 0; xx < this.woslHazardAttrs.length; xx++) {
					setValue(this.woslHazardAttrs[xx], tagoutowner.getString(this.hazardAttrs[xx]), 11L);
				}

				setValue("hazardid", tagoutowner.getString("hazardid"), 11L);
				setValue("assetnum", tagoutowner.getString("assetnum"), 11L);
				setValue("location", tagoutowner.getString("location"), 11L);
				setValue("assetdescription", tagoutowner.getString("assetdescription"), 11L);
				setValue("assetdescription_longdescription", tagoutowner.getString("assetdescription_longdescription"),
						11L);

				setFieldFlag("assetnum", 7L, true);
				setFieldFlag("location", 7L, true);
				setFieldFlag("assetdescription", 7L, true);
			}

		} else {
			setValue("wosafetydatasource", "WO", 11L);
		}

		getMboValue("WoSafetyLinkID").generateUniqueID();
		setFieldFlag("WoSafetyLinkID", 7L, true);

		if ((relName != null) && (relName.equals("WOSAFETYLINKTAG"))) {
			MboValue mv = getMboValue("tagoutassetnum");
			FldWoHazardNonPer listener = new FldWoHazardNonPer(mv);
			mv.addMboValueListener(listener);

			MboValue mv2 = getMboValue("tagoutlocation");
			FldWoHazardNonPer listener2 = new FldWoHazardNonPer(mv2);
			mv2.addMboValueListener(listener2);
		}
	}

	public void delete(long accessModifier) throws MXException, RemoteException {
		super.delete(accessModifier);

		MboRemote owner = getOwner();
		WoSafetyLinkSet setToTest = null;
		MboSetRemote woHazardSet = null;
		MboSetRemote woTagoutSet = null;

		if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
			setToTest = (WoSafetyLinkSet) getThisMboSet();
			woHazardSet = owner.getMboSet("WOHAZARD");
			woTagoutSet = owner.getMboSet("WOTAGOUT");
		} else {
			if ((owner != null) && (owner.isBasedOn("WOSAFETYLINK"))) {
				owner = owner.getOwner();
			}
			if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
				setToTest = (WoSafetyLinkSet) owner.getMboSet("WOSAFETYLINK");
				woHazardSet = owner.getMboSet("WOHAZARD");
				woTagoutSet = owner.getMboSet("WOTAGOUT");
			} else {
				setToTest = (WoSafetyLinkSet) getMboSet("$testalllinks", "WOSAFETYLINK", "wonum = :wonum");
				woHazardSet = getMboSet("WOHAZARD");
				woTagoutSet = getMboSet("WOTAGOUT");
			}

		}

		if ((!isNull("TagOutID")) && (setToTest.shouldCascadeDeleteTagOut(this))) {
			int i = 0;
			MboRemote tagout = woTagoutSet.getMbo(i);
			while (tagout != null) {
				if ((!tagout.toBeDeleted()) && (tagout.getString("tagoutid").equals(getString("tagoutid")))
						&& (tagout.getString("wosafetydatasource").equals(getString("wosafetydatasource")))) {
					tagout.delete(2L);
				}

				i++;
				tagout = woTagoutSet.getMbo(i);
			}

		}

		if ((!isNull("HazardID")) && (isNull("tagoutid")) && (setToTest.shouldCascadeDeleteHazard(this))) {
			String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();
			if ((relName == null) || (!relName.equals("WOSAFETYLINKTAG"))) {
				int i = 0;
				MboRemote hazard = woHazardSet.getMbo(i);
				while (hazard != null) {
					if ((!hazard.toBeDeleted()) && (hazard.getString("hazardid").equals(getString("hazardid")))
							&& (hazard.getString("wosafetydatasource").equals(getString("wosafetydatasource")))) {
						hazard.delete(2L);
					}

					i++;
					hazard = woHazardSet.getMbo(i);
				}
			}
		}

		if ((owner != null) && (owner.isBasedOn("WORKORDER")) && (isNull("tagoutid"))
				&& ((getBoolean("tagoutenabled")) || (isTagOutEnabled()))) {
			String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

			if ((relName == null) || (!relName.equals("WOSAFETYLINK"))) {
				getMboSet("WOSAFETYLINKTAG").deleteAll(2L);
			}
		}
	}

	public void undelete() throws MXException, RemoteException {
		super.undelete();

		MboRemote owner = getOwner();

		MboSetRemote woHazardSet = null;
		MboSetRemote woTagoutSet = null;

		if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
			woHazardSet = owner.getMboSet("WOHAZARD");
			woTagoutSet = owner.getMboSet("WOTAGOUT");
		} else {
			if ((owner != null) && (owner.isBasedOn("WOSAFETYLINK"))) {
				owner = owner.getOwner();
			}
			if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
				woHazardSet = owner.getMboSet("WOHAZARD");
				woTagoutSet = owner.getMboSet("WOTAGOUT");
			} else {
				woHazardSet = getMboSet("WOHAZARD");
				woTagoutSet = getMboSet("WOTAGOUT");
			}

		}

		if (!isNull("TagOutID")) {
			int i = 0;
			MboRemote tagout = woTagoutSet.getMbo(i);
			while (tagout != null) {
				if ((tagout.getString("tagoutid").equals(getString("tagoutid")))
						&& (tagout.getString("wosafetydatasource").equals(getString("wosafetydatasource")))) {
					tagout.undelete();
				}

				i++;
				tagout = woTagoutSet.getMbo(i);
			}

		}

		if ((!isNull("HazardID")) && (isNull("tagoutid"))) {
			int i = 0;
			MboRemote hazard = woHazardSet.getMbo(i);
			while (hazard != null) {
				if ((hazard.getString("hazardid").equals(getString("hazardid")))
						&& (hazard.getString("wosafetydatasource").equals(getString("wosafetydatasource")))) {
					hazard.undelete();
				}

				i++;
				hazard = woHazardSet.getMbo(i);
			}
		}

		if ((owner != null) && (owner.isBasedOn("WORKORDER")) && (isNull("tagoutid"))
				&& ((getBoolean("tagoutenabled")) || (isTagOutEnabled()))) {
			getMboSet("WOSAFETYLINKTAG").undeleteAll();
		}
	}

	public void propagateKeyValue(String keyName, String keyValue) throws MXException, RemoteException {
		if (keyName.equalsIgnoreCase("wonum"))
			setValue("wonum", keyValue, 2L);
	}

	public void initRelationship(String relationName, MboSetRemote mboSet) throws MXException, RemoteException {
		if (mboSet.isBasedOn("WOSAFETYLINK")) {
			((WoSafetyLinkSet) mboSet).setThisRelationName(relationName);
		} else if (mboSet.isBasedOn("WOTAGLOCK")) {
			((WoTagLockSet) mboSet).setThisRelationName(relationName);
		} else if (mboSet.isBasedOn("WOHAZARDPREC")) {
			((WoHazardPrecSet) mboSet).setThisRelationName(relationName);
		}
	}

	public boolean toBeSaved() throws RemoteException {
		boolean modified = false;
		try {
			boolean testModified = false;

			if (!toBeAdded()) {
				for (int xx = 0; xx < this.woslHazardAttrs.length; xx++) {
					if (!getMboValue(this.woslHazardAttrs[xx]).isModified())
						continue;
					testModified = true;
					break;
				}

				if (!testModified) {
					for (int xx = 0; xx < this.woslTagoutAttrs.length; xx++) {
						if (!getMboValue(this.woslTagoutAttrs[xx]).isModified())
							continue;
						testModified = true;
						break;
					}
				}

			}

			if (testModified) {
				MboRemote owner = getOwner();

				if (isNull("tagoutid")) {
					MboRemote hazardMbo = null;

					if ((owner == null) || ((owner != null) && (!owner.isBasedOn("WOHAZARD")))) {
						MboSetRemote hazardSet = getMboSet("WOHAZARD");

						if ((hazardSet != null) && (!hazardSet.isEmpty())) {
							hazardMbo = hazardSet.getMbo(0);
						}
					}
					if (hazardMbo != null) {
						for (int xx = 0; xx < this.woslHazardAttrs.length; xx++) {
							if (getString(this.woslHazardAttrs[xx]).equals(hazardMbo.getString(this.hazardAttrs[xx])))
								continue;
							modified = true;
							break;
						}
					}

				} else {
					MboRemote tagMbo = null;

					if ((owner == null) || ((owner != null) && (!owner.isBasedOn("WOTAGOUT")))) {
						MboSetRemote tagSet = getMboSet("WOTAGOUT");

						if ((tagSet != null) && (!tagSet.isEmpty())) {
							tagMbo = tagSet.getMbo(0);
						}
					}
					if (tagMbo != null) {
						for (int xx = 0; xx < this.woslTagoutAttrs.length; xx++) {
							if (getString(this.woslTagoutAttrs[xx]).equals(tagMbo.getString(this.tagoutAttrs[xx])))
								continue;
							modified = true;
							break;
						}
					}
				}
			}

		} catch (Exception e) {
		}

		if (modified) {
			return true;
		}
		return super.toBeSaved();
	}

	public void appValidate() throws MXException, RemoteException {
		MboRemote owner = getOwner();
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

		if (isNull("hazardid")) {
			MboValueInfo di = MXServer.getMXServer().getMaximoDD().getMboSetInfo("wohazard")
					.getMboValueInfo("hazardid");
			Object[] p = { di.getTitle(), getName() };
			throw new MXApplicationException("system", "null", p);
		}

		if (!isNull("tagoutid")) {
			if ((owner != null) && (((owner.isBasedOn("WOSAFETYLINK")) && (owner.isNull("tagoutid")))
					|| (owner.isBasedOn("WOHAZARD")))) {
				setValue("assetnum", owner.getString("assetnum"), 11L);
				setValue("location", owner.getString("location"), 11L);

				if ((relName != null) && (relName.equals("WOSAFETYLINKTAG"))) {
					if ((isNull("tagoutassetnum")) && (isNull("tagoutlocation"))) {
//						throw new MXApplicationException("workorder", "tagoutNeedsAssetnumOrLocation");
					}
				}
			}
			WOService woServ = (WOService) getMboServer();
			boolean tagMustBeHazChild = woServ.tagoutsMustBelongToHazards();

			if ((isNull("hazardid")) && (tagMustBeHazChild)) {
				throw new MXApplicationException("workorder", "tagoutMustBelongToHazard");
			}
			if ((!isNull("hazardid")) && (!tagMustBeHazChild)) {
				throw new MXApplicationException("workorder", "tagoutCannotBelongToHazard");
			}
			if ((isNull("assetnum")) && (isNull("location"))) {
//				throw new MXApplicationException("workorder", "tagoutNeedsAssetnumOrLocation");
			}
			if ((!isNull("hazardid")) && (!isTagOutEnabled())) {
//				throw new MXApplicationException("workorder", "notTagoutEnabled");
			}
		}
		if (!isNull("hazardid")) {
			if ((isTagOutEnabled()) && (isNull("assetnum")) && (isNull("location"))) {
//				throw new MXApplicationException("workorder", "tagoutNeedsAssetnumOrLocation");
			}
		}
		if ((!isNull("assetnum")) && (!isNull("location"))) {
			throw new MXApplicationException("safety", "CannotSpecifyAssetnumAndLocation");
		}
		if ((!isNull("tagoutid")) && (!isNull("tagoutassetnum")) && (!isNull("tagoutlocation"))) {
			throw new MXApplicationException("safety", "CannotSpecifyAssetnumAndLocation");
		}

		if ((!isNull("hazardid")) && (!isNull("tagoutid"))) {
			WoSafetyLinkSet setToTest = null;

			if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
				setToTest = (WoSafetyLinkSet) getThisMboSet();
			} else if ((owner != null) && (owner.isBasedOn("WOSAFETYLINK")))
				setToTest = (WoSafetyLinkSet) owner.getThisMboSet();
			else {
				setToTest = (WoSafetyLinkSet) getMboSet("$testalllinks", "WOSAFETYLINK", "wonum = :wonum");
			}
			if (!relName.equals("TAGOUTENABLED")) {
				if (!setToTest.tagoutAssetLocMatchesHazard(this)) {
					// throw new MXApplicationException("workorder",
					// "tagoutAssetLocNotOnHazard");
				}
			}
		}
		super.appValidate();

		if ((relName != null) && (!toBeDeleted())
				&& ((relName.equals("WOSLHAZPRECENABLED")) || (relName.equals("WOSLHAZMATENABLED"))
						|| (relName.equals("WOSLTAGENABLED")) || (relName.equals("WOSAFETYLINKTAG")))) {
			if (toBeAdded()) {
				if ((relName.equals("WOSAFETYLINKTAG")) && (isNull("tagoutid"))) {
					MboValueInfo di = MXServer.getMXServer().getMaximoDD().getMboSetInfo("wotagout")
							.getMboValueInfo("tagoutid");
					Object[] p = { di.getTitle(), getName() };
					throw new MXApplicationException("system", "null", p);
				}

				MboRemote checkMbo = null;

				for (int xx = 0; (checkMbo = getThisMboSet().getMbo(xx)) != null; xx++) {
					if ((getString("wosafetylinkid").equals(checkMbo.getString("wosafetylinkid")))
							|| (!getString("wonum").equals(checkMbo.getString("wonum")))
							|| (!getString("hazardid").equals(checkMbo.getString("hazardid")))
							|| (!getString("wosafetydatasource").equals(checkMbo.getString("wosafetydatasource")))
							|| (!getString("assetnum").equals(checkMbo.getString("assetnum")))
							|| (!getString("location").equals(checkMbo.getString("location")))
							|| (!getString("tagoutid").equals(checkMbo.getString("tagoutid")))) {
						continue;
					}

					if (isNull("tagoutid")) {
						throw new MXApplicationException("workorder", "DupWOSL");
					}
					throw new MXApplicationException("workorder", "DupWOSLTag");
				}

			}

			if (getString("wosafetydatasource").equals("SP")) {
				setValue("wosafetydatasource", "WO", 11L);
			}

			if (isNull("tagoutid")) {
				String checkID = getString("hazardid");
				String checkDS = "WO";
				MboSetRemote checkSet = getThisMboSet();
				int i = 0;
				MboRemote checkMbo = checkSet.getMbo(i);

				while (checkMbo != null) {
					if ((checkMbo.getString("hazardid").equals(checkID)) && (checkMbo.isNull("tagoutid"))
							&& (!checkMbo.getString("wosafetydatasource").equals(checkDS))) {
						checkMbo.setValue("wosafetydatasource", checkDS, 11L);
					}
					i++;
					checkMbo = checkSet.getMbo(i);
				}

			}

			if (isNull("tagoutid"))
				maintainWoHazard();
			else {
				maintainWoTagout();
			}

		} else if ((owner != null) && (owner.isBasedOn("WORKORDER")) && (owner.toBeAdded())
				&& (((WO) owner).getDuplicated())) {
			if (getString("wosafetydatasource").equals("SP")) {
				setValue("wosafetydatasource", "WO", 11L);
			}
		}
	}

	public void save() throws MXException, RemoteException {
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();
		MboRemote owner = getOwner();

		if ((toBeAdded()) && (relName != null) && ((relName.equals("WOSLHAZPRECENABLED"))
				|| (relName.equals("WOSLHAZMATENABLED")) || (relName.equals("WOSLTAGENABLED")))) {
			if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
				MboSetRemote hazprecSet = owner.getMboSet("WOSLHAZPRECENABLED");
				if ((hazprecSet != null) && (!hazprecSet.isEmpty())) {
					MboRemote checkMbo = null;
					int xx = 0;

					while ((checkMbo = hazprecSet.getMbo(xx)) != null) {
						if ((checkMbo.toBeAdded()) && (!checkMbo.getString("hazardid").equals(getString("hazardid")))) {
							((Mbo) checkMbo).appValidate();
						}

						xx++;
					}
				}
				MboSetRemote hazmatSet = owner.getMboSet("WOSLHAZMATENABLED");
				if ((hazmatSet != null) && (!hazmatSet.isEmpty())) {
					MboRemote checkMbo = null;
					int xx = 0;

					while ((checkMbo = hazmatSet.getMbo(xx)) != null) {
						if ((checkMbo.toBeAdded()) && (!checkMbo.getString("hazardid").equals(getString("hazardid")))) {
							((Mbo) checkMbo).appValidate();
						}

						xx++;
					}
				}
				MboSetRemote haztagSet = owner.getMboSet("WOSLTAGENABLED");
				if ((haztagSet != null) && (!haztagSet.isEmpty())) {
					MboRemote checkMbo = null;
					int xx = 0;

					while ((checkMbo = haztagSet.getMbo(xx)) != null) {
						if ((checkMbo.toBeAdded()) && (!checkMbo.getString("hazardid").equals(getString("hazardid")))) {
							((Mbo) checkMbo).appValidate();
						}

						xx++;
					}
				}
			}
		}

		if ((toBeAdded()) && (relName != null)
				&& ((relName.equals("WOSLHAZPRECENABLED")) || (relName.equals("WOSLHAZMATENABLED")))) {
			if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
				MboSetRemote checkSet = null;

				if (relName.equals("WOSLHAZPRECENABLED"))
					checkSet = owner.getMboSet("WOSLHAZMATENABLED");
				else {
					checkSet = owner.getMboSet("WOSLHAZPRECENABLED");
				}
				if ((checkSet != null) && (!checkSet.isEmpty())) {
					MboRemote checkMbo = null;
					int xx = 0;

					while ((checkMbo = checkSet.getMbo(xx)) != null) {
						if ((checkMbo.toBeAdded())
								&& (!checkMbo.getString("wosafetylinkid").equals(getString("wosafetylinkid")))
								&& (checkMbo.getString("hazardid").equals(getString("hazardid")))
								&& (checkMbo.getString("assetnum").equals(getString("assetnum")))
								&& (checkMbo.getString("location").equals(getString("location")))) {
							checkSet.remove();
						}

						xx++;
					}
				}
			}

		}

		super.save();
	}

	public void maintainWoHazard() throws MXException, RemoteException {
		if ((!isNull("tagoutid")) || (toBeDeleted())) {
			return;
		}
		MboRemote haz = findWoHazToUpdate(true);

		if (haz != null) {
			if (!getString("hazardid").equals(haz.getString("hazardid"))) {
				haz.setValue("hazardid", getString("hazardid"));
			}
			haz.setValue("wosafetydatasource", "WO", 11L);
			setWoHazardValues(haz);
			haz.validate();
		}
	}

	public void maintainWoTagout() throws MXException, RemoteException {
		if ((isNull("tagoutid")) || (toBeDeleted())) {
			return;
		}
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

		if ((relName != null) && (relName.equals("WOSAFETYLINKTAG"))) {
			MboRemote tag = findWoTagToUpdate();

			if (tag != null) {
				tag.setValue("wosafetydatasource", "WO", 2L);
				setWoTagValues(tag);
				tag.validate();
			}
		}
	}

	private MboRemote findWoHazToUpdate(boolean add) throws MXException, RemoteException {
		if (this.woHazToUpdate != null) {
			if (toBeAdded()) {
				if (!getString("hazardid").equals(this.woHazToUpdate.getString("hazardid"))) {
					if (this.woHazToUpdate.toBeAdded()) {
						String previousHazardOnThisToBeAddedWOSL = getMboValue("hazardid").getPreviousValue()
								.asString();
						if (!previousHazardOnThisToBeAddedWOSL.equalsIgnoreCase(getString("hazardid"))) {
							MboSetRemote thisWOSafetyLinkSet = getThisMboSet();
							MboRemote nextWOSafetyLink = null;
							int i = 0;
							boolean anotherNewWOSafetyLinkForNewHazardFound = false;

							while ((nextWOSafetyLink = thisWOSafetyLinkSet.getMbo(i)) != null) {
								if ((nextWOSafetyLink.toBeAdded()) && (nextWOSafetyLink.getString("hazardid")
										.equals(this.woHazToUpdate.getString("hazardid")))) {
									anotherNewWOSafetyLinkForNewHazardFound = true;
									break;
								}
								i++;
							}
							if (!anotherNewWOSafetyLinkForNewHazardFound)
								this.woHazToUpdate.delete();
						}
					}
					this.woHazToUpdate = null;
				}
			}

			if (this.woHazToUpdate != null) {
				return this.woHazToUpdate;
			}
		}
		MboRemote haz = null;

		MboRemote owner = getOwner();

		if ((owner == null) || (!owner.isBasedOn("WORKORDER"))) {
			return haz;
		}
		MboSetRemote hazSet = owner.getMboSet("WOHAZARD");

		if (!hazSet.isEmpty()) {
			int i = 0;
			MboRemote checkhaz = hazSet.getMbo(i);

			if (getString("wosafetydatasource").equals("WP")) {
				while (checkhaz != null) {
					if ((checkhaz.getString("hazardid").equals(getString("hazardid")))
							&& (checkhaz.getString("wosafetydatasource").equals(getString("wosafetydatasource")))) {
						haz = checkhaz;
						break;
					}
					i++;
					checkhaz = hazSet.getMbo(i);
				}

				if (haz != null) {
					this.woHazToUpdate = haz;
				}
				return this.woHazToUpdate;
			}

			i = 0;
			checkhaz = hazSet.getMbo(i);

			while (checkhaz != null) {
				if ((checkhaz.getString("hazardid").equals(getString("hazardid")))
						&& (checkhaz.getString("wosafetydatasource").equals("WO"))) {
					haz = checkhaz;
					break;
				}
				i++;
				checkhaz = hazSet.getMbo(i);
			}

			if (haz == null) {
				i = 0;
				checkhaz = hazSet.getMbo(i);

				while (checkhaz != null) {
					if ((checkhaz.getString("hazardid").equals(getString("hazardid")))
							&& (checkhaz.getString("wosafetydatasource").equals("SP"))) {
						haz = checkhaz;
						break;
					}
					i++;
					checkhaz = hazSet.getMbo(i);
				}

				if ((haz == null) && (add)) {
					haz = hazSet.add();
					haz.setValue("hazardid", getString("hazardid"), 2L);
					haz.setValue("wosafetydatasource", getString("wosafetydatasource"), 2L);
					((WoHazard) haz).woslVirtualOwner = this;
				}
			}
		} else if (add) {
			haz = hazSet.add();
			haz.setValue("hazardid", getString("hazardid"), 2L);
			haz.setValue("wosafetydatasource", getString("wosafetydatasource"), 2L);
			((WoHazard) haz).woslVirtualOwner = this;
		}

		if (haz != null) {
			this.woHazToUpdate = haz;
		}
		return this.woHazToUpdate;
	}

	private MboRemote findWoTagToUpdate() throws MXException, RemoteException {
		if (this.woTagToUpdate != null) {
			return this.woTagToUpdate;
		}
		MboRemote tag = null;

		MboRemote owner = getOwner();

		if ((owner != null) && (owner.isBasedOn("WOSAFETYLINK"))) {
			owner = owner.getOwner();
		}
		if ((owner == null) || (!owner.isBasedOn("WORKORDER"))) {
			return tag;
		}
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();
		if (relName.equals("TAGOUTENABLED")) {
			return tag;
		}
		MboSetRemote tagSet = owner.getMboSet("WOTAGOUT");

		if (!tagSet.isEmpty()) {
			int i = 0;
			MboRemote checktag = tagSet.getMbo(i);

			while (checktag != null) {
				if ((!checktag.isNull("tagoutid")) && (checktag.getString("tagoutid").equals(getString("tagoutid")))) {
					tag = checktag;
					break;
				}
				i++;
				checktag = tagSet.getMbo(i);
			}

			if (tag == null) {
				tag = tagSet.add();
				tag.setValue("hazardid", getString("hazardid"), 2L);
				tag.setValue("tagoutid", getString("tagoutid"), 2L);
				tag.setValue("wosafetydatasource", getString("wosafetydatasource"), 2L);
				((WoTagOut) tag).woslVirtualOwner = this;
			}
		} else {
			tag = tagSet.add();
			tag.setValue("hazardid", getString("hazardid"), 2L);
			tag.setValue("tagoutid", getString("tagoutid"), 2L);
			tag.setValue("wosafetydatasource", getString("wosafetydatasource"), 2L);
			((WoTagOut) tag).woslVirtualOwner = this;
		}

		if (tag != null) {
			this.woTagToUpdate = tag;
		}
		return this.woTagToUpdate;
	}

	private void setWoHazardValues(MboRemote haz) throws MXException, RemoteException {
		for (int xx = 0; xx < this.woslHazardAttrs.length; xx++) {
			haz.setValue(this.hazardAttrs[xx], getString(this.woslHazardAttrs[xx]), 2L);
		}

		((Mbo) haz).appValidate();
	}

	private void setWoTagValues(MboRemote tag) throws MXException, RemoteException {
		for (int xx = 0; xx < this.woslTagoutAttrs.length; xx++) {
			tag.setValue(this.tagoutAttrs[xx], getString(this.woslTagoutAttrs[xx]), 2L);
		}

		((Mbo) tag).appValidate();
	}

	public MboSetRemote getMboSet(String name) throws MXException, RemoteException {
		MboRemote owner = getOwner();
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();
		MboSetRemote hpSet = null;

		if ((!isZombie()) && (owner != null) && (owner.isBasedOn("WORKORDER")) && (name.equals("WOHAZARDPREC"))
				&& (relName != null) && (relName.equals("WOSLHAZPRECENABLED"))) {
			MboRemote haz = findWoHazToUpdate(false);

			if (haz != null) {
				hpSet = haz.getMboSet("WOHAZARDPREC");
				((WoHazardPrecSet) hpSet).setThisRelationName("WOHAZARDPREC");
				return hpSet;
			}

			return hpSet;
		}

		return super.getMboSet(name);
	}

	public boolean isHazardEnabled() throws MXException, RemoteException {
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

		if ((!toBeAdded()) || ((relName != null)
				&& ((relName.equals("WOSLHAZPRECENABLED")) || (relName.equals("WOSLHAZMATENABLED"))
						|| (relName.equals("WOSLTAGENABLED")) || (relName.equals("WOSAFETYLINKTAG"))))) {
			return getBoolean("hazmatenabled");
		}

		if (isNull("hazardid")) {
			return false;
		}
		MboRemote hazMbo = getTempHazard();

		if (hazMbo == null)
			return false;
		if ((hazMbo instanceof WoHazard)) {
			return ((WoHazard) hazMbo).isHazardEnabled();
		}
		return ((Hazard) hazMbo).isHazardEnabled();
	}

	public boolean isPrecautionEnabled() throws MXException, RemoteException {
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

		if ((!toBeAdded()) || ((relName != null)
				&& ((relName.equals("WOSLHAZPRECENABLED")) || (relName.equals("WOSLHAZMATENABLED"))
						|| (relName.equals("WOSLTAGENABLED")) || (relName.equals("WOSAFETYLINKTAG"))))) {
			return getBoolean("precautionenabled");
		}

		if (isNull("hazardid")) {
			return false;
		}
		MboRemote hazMbo = getTempHazard();

		if (hazMbo == null)
			return false;
		if ((hazMbo instanceof WoHazard)) {
			return ((WoHazard) hazMbo).isPrecautionEnabled();
		}
		return ((Hazard) hazMbo).isPrecautionEnabled();
	}

	public boolean isTagOutEnabled() throws MXException, RemoteException {
		String relName = ((WoSafetyLinkSet) getThisMboSet()).getThisRelationName();

		if ((!toBeAdded()) || ((relName != null)
				&& ((relName.equals("WOSLHAZPRECENABLED")) || (relName.equals("WOSLHAZMATENABLED"))
						|| (relName.equals("WOSLTAGENABLED")) || (relName.equals("WOSAFETYLINKTAG"))))) {
			return getBoolean("tagoutenabled");
		}

		if (isNull("hazardid")) {
			return false;
		}
		MboRemote hazMbo = getTempHazard();

		if (hazMbo == null)
			return false;
		if ((hazMbo instanceof WoHazard)) {
			return ((WoHazard) hazMbo).isTagOutEnabled();
		}
		return ((Hazard) hazMbo).isTagOutEnabled();
	}

	private MboRemote getTempHazard() throws MXException, RemoteException {
		MboSetRemote testSet = null;
		MboRemote mboToReturn = null;

		if ((isNull("hazardid")) || (isNull("wonum"))) {
			return null;
		}
		testSet = getMboSet("WOHAZARD");

		if (testSet.isEmpty()) {
			MboRemote owner = getOwner();
			if ((owner != null) && (owner.isBasedOn("WOSAFETYLINK"))) {
				owner = owner.getOwner();
			}
			if ((owner != null) && (owner.isBasedOn("WORKORDER"))) {
				testSet = owner.getMboSet("WOHAZARD");

				int i = 0;
				MboRemote testMbo = testSet.getMbo(i);
				while (testMbo != null) {
					if (!testMbo.toBeDeleted()) {
						if ((testMbo.getString("wonum").equals(getString("wonum")))
								&& (testMbo.getString("hazardid").equals(getString("hazardid")))
								&& (testMbo.getString("WoSafetyDataSource").equals(getString("WoSafetyDataSource")))) {
							mboToReturn = testMbo;
							break;
						}
					}

					i++;
					testMbo = testSet.getMbo(i);
				}
			}

			if (mboToReturn == null) {
				testSet = getMboSet("HAZARD");
				if (!testSet.isEmpty())
					mboToReturn = testSet.getMbo(0);
			}
		} else {
			mboToReturn = testSet.getMbo(0);
		}

		return mboToReturn;
	}

	protected boolean skipCopyField(MboValueInfo mvi) throws RemoteException, MXException {
		if (mvi.getName().equalsIgnoreCase("WONUM")) {
			return true;
		}
		return mvi.getName().equalsIgnoreCase("WOSAFETYLINKID");
	}
}