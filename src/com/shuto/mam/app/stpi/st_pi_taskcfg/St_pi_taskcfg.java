package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class St_pi_taskcfg extends Mbo implements St_pi_taskcfgRemote {
	private static final long serialVersionUID = 1L;

	public St_pi_taskcfg(MboSet mboset) throws MXException, RemoteException {
		super(mboset);
	}

	public void add() throws MXException, RemoteException {
		super.add();
	}

	protected void save() throws MXException, RemoteException {
		super.save();
	}

	public void init() throws MXException {
		super.init();
		try {
			if(!isNew()){
				String type = getString("type");
				if(getBoolean("IS_ENABLE")){
//					setFlag(7L, true);
				}else{
					if("点检".equals(type)){
						if (!"天".equals(getString("CYCLE_UNIT"))) {
						      setFieldFlag("CYCLE", 7L, true);
						    } else {
						      setFieldFlag("CYCLE", 128L, true);
						   }
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void delete(long accessModifier) throws RemoteException, MXException {
		getMboSet("ST_PI_TASKCFG_AREA").deleteAll(accessModifier);
		getMboSet("ST_PI_TASKCFG_ITEM").deleteAll(accessModifier);
		getMboSet("ST_PI_TASKDATETIME").deleteAll(accessModifier);
		super.delete(accessModifier);
	}
}
