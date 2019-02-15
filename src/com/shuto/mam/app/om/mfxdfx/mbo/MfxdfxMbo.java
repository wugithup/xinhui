package com.shuto.mam.app.om.mfxdfx.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
*
* com.shuto.mam.app.om.mfxdfx.mbo.MfxdfxRemote.java
* 
* app:MFXDFX(煤粉细度分析)
* 
* Email:xiamy@shuto.cn 2017年8月31日 上午9:41:33
*
*/
public class MfxdfxMbo extends Mbo implements MfxdfxRemote {
	public MfxdfxMbo(MboSet mboset) throws RemoteException {
		super(mboset);
	}

	public void init() throws MXException {
		super.init();
		try {
			String status = getString("STATUS");
			if ((!"关闭".equals(status)) || (getUserInfo().getUserName().equals("MAXADMIN")))
				return;
			setFlag(7L, true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}