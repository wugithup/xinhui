package com.shuto.mam.app.stpdzb;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public abstract interface stpdzbRemote extends MboRemote {
	public abstract void save() throws MXException, RemoteException;

	public abstract void add() throws MXException, RemoteException;
}