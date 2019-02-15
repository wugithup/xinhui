package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class Fld_ControlReadOnly extends MboValueAdapter {
	public Fld_ControlReadOnly(MboValue arg0) throws MXException {
		super(arg0);
	}

	public void init() throws MXException, RemoteException {
		super.init();
		MboRemote oplog = getMboValue().getMbo();
		MboSetRemote appSet = oplog.getThisMboSet();
		String appname = appSet.getApp();
		MboSetRemote userset = null;
		if (!"OPLOG".equalsIgnoreCase(appname)) {
			return;
		}
		String oplogtype = oplog.getString("oplogtype");

		String zhibie = oplog.getString("zhibie");

		String login2 = oplog.getUserInfo().getPersonId();
		String where = "personid = '" + login2 + "'";
		userset = oplog.getMboSet("$temp_oplogperson_user", "oplogperson", where);

		MboRemote usermbo = null;
		if (userset.count() == 0) {
			oplog.getMboSet("oplognum_oplogassetinfo_hx").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogassetinfo_sm").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogassetinfo_jdsx").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogassetinfo_asset").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogassetinfo_zycs").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogcoalstatus").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogcommand").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogfsrecord").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogkeylog").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplognote").setFlag(7L, true);
			oplog.getMboSet("oplognum_oplogpersoncq").setFlag(7L, true);
		} else {
			usermbo = userset.getMbo(0);

			String useroplogtype = usermbo.getString("oplogtype");

			String userzhibie = usermbo.getString("zhibie");

			boolean userbackup = usermbo.getBoolean("yornbackup");

			if ((userbackup) && (oplogtype.equals(useroplogtype))) {
				return;
			}

			if ((!oplogtype.equals(useroplogtype)) || (!zhibie.equals(userzhibie))) {
				oplog.getMboSet("oplognum_oplogassetinfo_hx").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogassetinfo_sm").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogassetinfo_jdsx").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogassetinfo_asset").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogassetinfo_zycs").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogcoalstatus").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogcommand").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogfsrecord").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogkeylog").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplognote").setFlag(7L, true);
				oplog.getMboSet("oplognum_oplogpersoncq").setFlag(7L, true);
			}

		}

		userset.close();
	}
}