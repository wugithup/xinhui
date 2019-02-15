package psdi.app.safety;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValueInfo;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.util.Vector;

public class TagOut extends Mbo implements TagOutRemote {
	public TagOut(MboSet ms) throws MXException, RemoteException {
		super(ms);
	}

	public void init() throws MXException {
		super.init();

		if (!toBeAdded()) {
			try {
				setAssetDescription();

				if (!isNull("assetnum"))
					setFieldFlag("location", 7L, true);
				else {
					setFieldFlag("assetnum", 7L, true);
				}
			} catch (Exception e) {
				MXLogger myLogger = MXLoggerFactory
						.getLogger("maximo.application.SAFETY");
				if (myLogger.isDebugEnabled()) {
					myLogger.debug(e.getMessage());
				}
			}

			String[] readOnly = { "tagoutid", "assetdescription" };
			setFieldFlag(readOnly, 7L, true);
		} else {
			String[] readOnly = { "assetdescription" };
			setFieldFlag(readOnly, 7L, true);
		}
	}

	public void add() throws MXException, RemoteException {
		setFieldFlag("siteid", 7L, true);
	}

	public void canDelete() throws MXException, RemoteException {
		if (!getMboSet("TAGLOCK").isEmpty()) {
			throw new MXApplicationException("safety", "CannotDeleteTagout");
		}
		if (!getMboSet(
				"$assetlocation",
				"SAFETYLEXICON",
				"tagoutid = :tagoutid and (assetnum is not null or location is not null) and siteid=:siteid")
				.isEmpty()) {
			throw new MXApplicationException("safety",
					"CannotDeleteTagoutAssetLocLink");
		}
	}

	public void delete(long accessModifier) throws MXException, RemoteException {
		super.delete(accessModifier);

		getMboSet("TAGLOCK").deleteAll(2L);
		getMboSet("SAFETYLEXICON").deleteAll(2L);
	}

	public void undelete() throws MXException, RemoteException {
		super.undelete();

		getMboSet("TAGLOCK").undeleteAll();
		getMboSet("SAFETYLEXICON").undeleteAll();
	}

	public void appValidate() throws MXException, RemoteException {
		if ((isNull("assetnum")) && (isNull("location"))) {
//			throw new MXApplicationException("safety",
//					"MustSpecifyAssetnumOrLocation");
		}

		super.appValidate();

		if (toBeAdded()) {
			if (getMboSet("SAFETYLEXMAIN").isEmpty()) {
				getMboSet("SAFETYLEXMAIN").add(2L);
			}
		}
	}

	public MboSetRemote associateLockOutToTagOut(Vector lovector,
			MboSetRemote tlset) throws MXException, RemoteException {
		MboSetRemote loset = null;

		if (tlset == null) {
			tlset = getMboSet("TAGLOCK");
		}
		for (int i = 0; i < lovector.size(); i++) {
			long lockoutid = ((Long) lovector.elementAt(i)).longValue();

			if (taglockAlreadyExists(tlset, lockoutid)) {
				throw new MXApplicationException("safety", "DuplicateTaglock");
			}
			tlset.add();
			MboRemote tl = tlset.getMbo();
			tl.setValue("lockoutid", lockoutid, 11L);
			loset = tl.getMboSet("LOCKOUT");
			if (loset.isEmpty()) {
				loset.add();
			}
		}

		return tlset;
	}

	private boolean taglockAlreadyExists(MboSetRemote tlset, long lockoutid)
			throws MXException, RemoteException {
		MboRemote tlmbo = null;

		for (int xx = 0; (tlmbo = tlset.getMbo(xx)) != null; xx++) {
			if (lockoutid == tlmbo.getLong("lockoutid")) {
				return true;
			}
		}
		return false;
	}

	private void setAssetDescription() throws MXException, RemoteException {
		MboSetRemote assetSet = null;
		MboRemote assetMbo = null;

		if (!isNull("assetnum")) {
			SqlFormat sqf = new SqlFormat(getUserInfo(),
					"assetnum = :1 and siteid = :2");
			sqf.setObject(1, "asset", "assetnum", getString("assetnum"));
			sqf.setObject(2, "asset", "siteid", getString("siteid"));
			assetSet = getMboSet("$getasset", "asset", sqf.format());
		} else if (!isNull("location")) {
			SqlFormat sqf = new SqlFormat(getUserInfo(),
					"location = :1 and siteid = :2");
			sqf.setObject(1, "locations", "location", getString("location"));
			sqf.setObject(2, "locations", "siteid", getString("siteid"));
			assetSet = getMboSet("$getasset", "locations", sqf.format());
		}

		if ((assetSet != null) && (!assetSet.isEmpty())) {
			assetMbo = assetSet.getMbo(0);
		}
		if (assetMbo != null) {
			setValue("assetdescription", assetMbo.getString("description"), 11L);
			setValue("assetdescription_longdescription",
					assetMbo.getString("description_longdescription"), 11L);
		}
	}

	public MboRemote duplicate() throws MXException, RemoteException {
		MboRemote newTagOutRemote = copy();

		MboSetRemote myDoclinksSetRemote = getMboSet("DOCLINKS");
		myDoclinksSetRemote.copy(newTagOutRemote.getMboSet("DOCLINKS"));

		MboSetRemote myTagoutSafetyLexSetRemote = getMboSet("SAFETYLEXMAIN");
		myTagoutSafetyLexSetRemote.copy(newTagOutRemote
				.getMboSet("SAFETYLEXMAIN"));

		MboSetRemote myTagLockSetRemote = getMboSet("TAGLOCK");
		MboSetRemote newTagLockSetRemote = newTagOutRemote.getMboSet("TAGLOCK");
		myTagLockSetRemote.copy(newTagLockSetRemote);

		int i = 0;
		MboRemote newtaglock = newTagLockSetRemote.getMbo(i);
		MboRemote oldtaglock = myTagLockSetRemote.getMbo(i);
		MboRemote newlockout = null;
		MboRemote oldlockout = null;
		while (newtaglock != null) {
			newlockout = newtaglock.getMboSet("LOCKOUT").getMbo(0);
			oldlockout = oldtaglock.getMboSet("LOCKOUT").getMbo(0);
			newlockout.setValue("devicedescription",
					oldlockout.getString("devicedescription"), 11L);
			newlockout.setValue("requiredstate",
					oldlockout.getString("requiredstate"), 11L);
			i++;
			newtaglock = newTagLockSetRemote.getMbo(i);
			oldtaglock = myTagLockSetRemote.getMbo(i);
		}
		return newTagOutRemote;
	}

	protected boolean skipCopyField(MboValueInfo mvi) throws RemoteException,
			MXException {
		if (mvi.getName().equalsIgnoreCase("TAGOUTID")) {
			return true;
		}
		return false;
	}
}