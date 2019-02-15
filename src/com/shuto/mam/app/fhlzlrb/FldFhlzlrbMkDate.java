package com.shuto.mam.app.fhlzlrb;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * com.shuto.mam.app.fabp.FldFhlzlrbMkDate.java
 * 
 * app:煤粉细度分析
 * 
 * Email:xiamy@shuto.cn 2017年8月31日 上午10:43:22
 *
 */
public class FldFhlzlrbMkDate extends MboValueAdapter {
	public FldFhlzlrbMkDate(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();
	}

	public void validate() throws MXException, RemoteException {
		super.validate();
		if (hasReport())
			throw new MXApplicationException("", "该日期录入记录,不能重复创建!");
	}

	public boolean hasReport() throws RemoteException, MXException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Mbo mbo = getMboValue().getMbo();
		Date mkdate = getMboValue().getDate();
		if (mkdate == null) {
			return false;
		}
		SqlFormat sql = new SqlFormat(" MKDATE=:1  and FHLZLRBID!=:2");
		sql.setObject(1, "FHLZLRB", "MKDATE", sdf.format(mkdate));
		sql.setObject(2, "FHLZLRB", "FHLZLRBID", mbo.getString("FHLZLRBID"));
		String where = sql.format();
		MboSetRemote rdSet = mbo.getMboSet("$FHLZLRB", "FHLZLRB", where);
		if (rdSet.count() > 0) {
			rdSet.close();
			return true;
		}
		rdSet.close();
		return false;
	}
}