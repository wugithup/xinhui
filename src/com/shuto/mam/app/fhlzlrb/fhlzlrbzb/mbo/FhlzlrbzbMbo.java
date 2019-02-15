package com.shuto.mam.app.fhlzlrb.fhlzlrbzb.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 *
 * com.shuto.mam.app.fhlzlrb.fhlzlrbzb.FhlzlrbzbMbo.java
 * 
 * Email:xiamy@shuto.cn 2017年8月31日 上午10:45:18
 *
 */
public class FhlzlrbzbMbo extends Mbo implements FhlzlrbzbRemote {
	public FhlzlrbzbMbo(MboSet mboset) throws RemoteException {
		super(mboset);
	}

	public void init() throws MXException {
		super.init();
		try {
			MboRemote mbo = getOwner();

			String status = mbo.getString("STATUS");
			if ((!"关闭".equals(status)) || (mbo.getUserInfo().getUserName().equals("MAXADMIN")))
				return;
			setFlag(7L, true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}