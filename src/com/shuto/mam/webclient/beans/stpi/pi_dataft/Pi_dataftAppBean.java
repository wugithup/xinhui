package com.shuto.mam.webclient.beans.stpi.pi_dataft;

import java.rmi.RemoteException;
import java.text.DecimalFormat;

import com.shuto.mam.crontask.stpi.upload.UploadCron;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class Pi_dataftAppBean extends AppBean {

	public Pi_dataftAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		int number = 1;
		MboSetRemote resultSet = getMbo().getMboSet("$resultSet", "ST_PI_DATAFORMAT",
				"no = (select max(no) from ST_PI_DATAFORMAT)");
		if (!resultSet.isEmpty()) {
			number = Integer.valueOf(resultSet.getMbo(0).getString("no").substring(2));
			++number;
		}
		DecimalFormat decimalFormat = new DecimalFormat("000");
		String tmp = decimalFormat.format(number);
		String strNo = "FT" + tmp;
		getMbo().setValue("no", strNo, 11L);
		resultSet.close();
		return insert;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}
		return super.SAVE();
	}

	/**
	 * 按数据格式编号手动触发数据上传
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void UPLOADBYNO() throws RemoteException, MXException {
		MboRemote mbo = getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		String dataformatno = mbo.getString("no");
		UploadCron uploadcron = new UploadCron();
		uploadcron.autoAction(mbo, orgid, siteid, dataformatno);
	}
	
	/**
	 * 按地点手动触发数据上传
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void UPLOADSITE() throws RemoteException, MXException {
		MboRemote mbo = getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		UploadCron uploadcron = new UploadCron();
		uploadcron.autoAction(mbo, orgid, siteid, null);
	}
	
	public boolean hasAuth() throws MXException, RemoteException {
		String createby = getMbo().getString("CREATEBY");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if (s1.equalsIgnoreCase(createby))
			return true;
		return false;
	}
}
