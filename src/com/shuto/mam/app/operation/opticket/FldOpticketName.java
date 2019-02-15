package com.shuto.mam.app.operation.opticket;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月21日
 * @since:控股台账
 */
public class FldOpticketName extends MAXTableDomain {
	public FldOpticketName(MboValue mbv) throws MXException, RemoteException {
		super(mbv);

		String where = "isopticketbz=1 and status='已批准' and siteid=:siteid";

		setRelationship("opticket", where);

		setListCriteria(where);

		String[] strTo = { "bzopticketnum", "bzopticketdescription", "type" };

		String[] strFrom = { "opticketnum", "description", "type", "tickprofession", "system", "zsystem", "location.description" };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		StringBuffer sBuffer = new StringBuffer();

		String opticketnum = getMboValue("opticketnum").getString();

		String bzopticketnum = getMboValue("bzopticketnum").getString();

		String bzp_sql = "opticketnum=" + "'" + bzopticketnum + "'";

		MboSetRemote opticketlinemsr = getMboValue().getMbo().getMboSet("$temp_opticketline", "opticketline", bzp_sql);

		MboSetRemote dangerouspointmsr = getMboValue().getMbo().getMboSet("$temp_opticketdpoint", "opticketdpoint", bzp_sql);

		MboSetRemote opticketbz = getMboValue().getMbo().getMboSet("$temp_opticket", "opticket", bzp_sql);

		MboSetRemote opticketline = getMboValue().getMbo().getMboSet("bzopicket_opticketline");

		MboSetRemote dangerouspoint = getMboValue().getMbo().getMboSet("opticketnum_opticketdpoint");

		MboRemote mbo = getMboValue().getMbo();

		opticketline.deleteAll();

		dangerouspoint.deleteAll();

		MboRemote opticket = null;

		if (opticketbz.count() > 0) {
			opticket = opticketbz.getMbo(0);

			mbo.setValue("bzopticketdescription", opticket.getString("description"));

			mbo.setValue("MISSION", opticket.getString("description"), 11L);

			mbo.setValue("type", opticket.getString("type"));
		}
		MboRemote mboremote = null;
		MboRemote optLine = null;
		if (opticketlinemsr.count() > 0) {
			for (int i = 0; i < opticketlinemsr.count(); ++i) {
				mboremote = opticketline.add();
				optLine = opticketlinemsr.getMbo(i);

				mboremote.setValue("ordernum", optLine.getString("ordernum"));

				mboremote.setValue("opticketproject", optLine.getString("opticketproject"));

				mboremote.setValue("YUKONG", optLine.getString("YUKONG"));

				mboremote.setValue("opticketnum", opticketnum);
			}

		}

		MboRemote dpLine = null;
		MboRemote mboremotewx = null;
		if (dangerouspointmsr.count() > 0) {
			for (int i = 0; i < dangerouspointmsr.count(); ++i) {
				mboremotewx = dangerouspoint.add();
				dpLine = dangerouspointmsr.getMbo(i);

				mboremotewx.setValue("ordernum", dpLine.getString("ordernum"));

				mboremotewx.setValue("dangerouspoint", dpLine.getString("dangerouspoint"));

				mboremotewx.setValue("dpfcms", dpLine.getString("dpfcms"));
				mboremotewx.setValue("FXTYPES", dpLine.getString("FXTYPES"));
				mboremotewx.setValue("FXTYPEH", dpLine.getString("FXTYPEH"));
				mboremotewx.setValue("FXTYPEE", dpLine.getString("FXTYPEE"));

				mboremotewx.setValue("opticketnum", opticketnum);
			}

		}

		opticketlinemsr.close();
		dangerouspointmsr.close();
		opticketbz.close();
	}
}