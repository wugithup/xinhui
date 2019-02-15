package com.shuto.mam.app.operation.opticket;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年7月6日 下午3:38:01
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldBzOpticketNum extends MAXTableDomain {
	public FldBzOpticketNum(MboValue mbv) throws MXException, RemoteException {
		super(mbv);
		setRelationship("opticket", "1=1");
		String[] strTo = { "mission", "h_type", "PROFESSION", "BZOPTICKETNUM", "type1", "YN1", "YN2", "YN3", "YN4",
				"YN5", "YN6", "YN7", "YN8", "YN9", "YN10", "YN11", "YN12", "YN13", "YN14", "YN15", "YN16", "YN17",
				"YN18", "YN19", "YN20" };

		String[] strFrom = { "mission", "h_type", "PROFESSION", "OPTICKETNUM", "type1", "YN1", "YN2", "YN3", "YN4",
				"YN5", "YN6", "YN7", "YN8", "YN9", "YN10", "YN11", "YN12", "YN13", "YN14", "YN15", "YN16", "YN17",
				"YN18", "YN19", "YN20" };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		String opticketnum = getMboValue("opticketnum").getString();

		String bzopticketnum = getMboValue("bzopticketnum").getString();

		String siteid = getMboValue("siteid").getString();

		String bzp_sql = "opticketnum=" + "'" + bzopticketnum + "' and siteid = '" + siteid + "'";

		MboSetRemote opticketlinemsr = getMboValue().getMbo().getMboSet("$temp_opticketline", "opticketline", bzp_sql);
		opticketlinemsr.setOrderBy("ordernum desc");

		MboSetRemote dangerouspointmsr = getMboValue().getMbo().getMboSet("$temp_opticketdpoint", "opticketdpoint",
				bzp_sql);
		dangerouspointmsr.setOrderBy("ordernum desc");

		MboSetRemote opticketbz = getMboValue().getMbo().getMboSet("$temp_opticket", "opticket", bzp_sql);

		MboSetRemote opticketline = getMboValue().getMbo().getMboSet("OPTICKETNUM_OPTICKETLINE");

		MboSetRemote dangerouspoint = getMboValue().getMbo().getMboSet("opticketnum_opticketdpoint");

		MboRemote mbo = getMboValue().getMbo();

		opticketline.deleteAll();

		dangerouspoint.deleteAll();

		MboRemote mboremote = null;
		MboRemote optLine = null;
		if (!opticketlinemsr.isEmpty()) {
			for (int i = 0; ((optLine = opticketlinemsr.getMbo(i)) != null); ++i) {
				mboremote = opticketline.add();
				mboremote.setValue("ordernum", optLine.getString("ordernum"));
				mboremote.setValue("opticketproject", optLine.getString("opticketproject"));
				mboremote.setValue("YUKONG", optLine.getString("YUKONG"));
				mboremote.setValue("opticketnum", opticketnum);
			}

		}

		MboRemote dpLine = null;
		MboRemote mboremotewx = null;
		if (!dangerouspointmsr.isEmpty()) {
			for (int i = 0; ((dpLine = dangerouspointmsr.getMbo(i)) != null); ++i) {
				mboremotewx = dangerouspoint.add();
				mboremotewx.setValue("ordernum", dpLine.getString("ordernum"));
				mboremotewx.setValue("dangerouspoint", dpLine.getString("dangerouspoint"));
				mboremotewx.setValue("dpfcms", dpLine.getString("dpfcms"));
				mboremotewx.setValue("FXTYPES", dpLine.getString("FXTYPES"));
				mboremotewx.setValue("FXTYPEH", dpLine.getString("FXTYPEH"));
				mboremotewx.setValue("FXTYPEE", dpLine.getString("FXTYPEE"));
				mboremotewx.setValue("opticketnum", opticketnum);
			}
		}
		if (!mbo.isNew()) {
			opticketline.save();
			dangerouspoint.save();
		}
		opticketlinemsr.close();
		dangerouspointmsr.close();
		opticketbz.close();
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
		String type = mainMbo.getString("h_type"); // 操作票类型
		String siteid = mainMbo.getString("siteid"); // 地点
		if (!type.isEmpty()) {
			setListCriteria(
					" h_type = '" + type + "'  and siteid = '" + siteid + "' and isopticketbz=1 and status='已批准' ");
		} else {
			setListCriteria("siteid = '" + siteid + "' and isopticketbz=1 and status='已批准' ");
		}
		return super.getList();
	}
}
