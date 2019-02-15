package com.shuto.mam.app.stpi.st_pi_task;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class St_pi_task extends Mbo implements St_pi_taskRemote {
	private static final long serialVersionUID = 1L;

	public St_pi_task(MboSet mboset) throws MXException, RemoteException {
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
//		try {
//			//替班控制（暂时未用）
//		      if (!getBoolean("IS_ASSESSMENT")) {
//		        setFlag(7L, true);
//		      }else if (getBoolean("ISREPLACE")){
//		        setFieldFlag("REPLACEPER", 128L, true);
//		      }else{
//		        setFieldFlag("REPLACEPER", 7L, true);
//		      }
//		      
//		    }
//		    catch (RemoteException e)
//		    {
//		      e.printStackTrace();
//		    }
	}

	public void delete(long accessModifier) throws RemoteException, MXException {
		getMboSet("ST_PI_TASK_AREA").deleteAll(accessModifier);
		getMboSet("ST_PI_TASK_ITEM").deleteAll(accessModifier);
		getMboSet("ST_PI_TASK_USER").deleteAll(accessModifier);
		super.delete(accessModifier);
	}
}
