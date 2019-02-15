package com.shuto.mam.app.stpi.st_pi_item;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class St_pi_item extends Mbo implements St_pi_itemRemote {
	private static final long serialVersionUID = 1L;

	public St_pi_item(MboSet mboset) throws MXException, RemoteException {
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
		try
	    {
	      String point_type = getString("point_type");

	      if ("CZ".equals(point_type)) {
	        setFieldFlag("SHAKE_TYPE", 128L, true);
	      } else {
	        setFieldFlag("SHAKE_TYPE", 7L, true);
	      }

	      if ("GC".equals(point_type)) {
	        setFieldFlag("HIGHER_LIMIT", 7L, true);
	        setFieldFlag("LOWER_LIMIT", 7L, true);
	      }
	      else {
	        setFieldFlag("HIGHER_LIMIT", 128L, true);
	        setFieldFlag("LOWER_LIMIT", 128L, true);
	      }
	    }
	    catch (RemoteException e)
	    {
	      e.printStackTrace();
	    }
	}

	public void delete(long accessModifier) throws RemoteException, MXException {
		super.delete(accessModifier);
	}
}
