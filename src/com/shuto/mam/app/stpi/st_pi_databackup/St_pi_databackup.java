package com.shuto.mam.app.stpi.st_pi_databackup;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class St_pi_databackup extends Mbo implements St_pi_databackupRemote {
	private static final long serialVersionUID = 1L;

	public St_pi_databackup(MboSet mboset) throws MXException, RemoteException {
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
	}

	public void delete(long accessModifier) throws RemoteException, MXException {
		super.delete(accessModifier);
	}
	/**
	 * 插入备份日志
	 * @param taskCount
	 * @param backuptime
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void setLog(int taskCount,Date backuptime) throws MXException, RemoteException {
		MboSetRemote databackuplogSet = getMboSet("ST_PI_DATABACKUPLOG");
		MboRemote databackuplogMbo = databackuplogSet.add();
		databackuplogMbo.setValue("ST_PI_DATABACKUPID", String.valueOf(getUniqueIDValue()),11L);
		databackuplogMbo.setValue("TASKCOUNT", taskCount,11L);
		databackuplogMbo.setValue("BACKUPTIME", backuptime,11L);
		databackuplogMbo.setValue("IS_SUCCESS", true,11L);
		databackuplogMbo.setValue("TYPE", getString("TYPE"),11L);
	}
}
