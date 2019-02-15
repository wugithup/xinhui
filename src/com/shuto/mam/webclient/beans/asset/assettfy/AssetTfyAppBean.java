package com.shuto.mam.webclient.beans.asset.assettfy;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * com.shuto.mam.webclient.beans.asset.assettfy.AssetTfyAppBean 设备停复役 asset_tfy
 * 设备停复役
 * 
 * @author zhaowei zhaowei@shuoto.cn
 * @date 2017年5月10日 下午4:01:31
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class AssetTfyAppBean extends CAppBean {

	@Override
	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote mbo = this.app.getAppBean().getMbo();
		String personid = mbo.getString("CREATEPERSON");
		MboSetRemote personSet = mbo.getMboSet("$person", "person", "personid='" + personid + "' and status='活动'");
		if (!personSet.isEmpty()) {
			String department = personSet.getMbo(0).getString("department");
			mbo.setValue("SQUNIT", department, 11L);
		}
		this.app.getAppBean().refreshTable();
		this.app.getAppBean().reloadTable();
		personSet.close();
		return i;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		return super.SAVE();
	}
	
	@Override
	public int DUPLICATE() throws MXException, RemoteException {
		int i=super.DUPLICATE();
		MboRemote mbo=this.app.getAppBean().getMbo();
		mbo.setValue("ASSET_TFYNUM", "",11L);
		mbo.setValue("RUNMODE", "",11L);
		mbo.setValue("YXSPPERSON", "",11L);
		mbo.setValue("YXSPTIME", "",11L);
		mbo.setValue("JSBSPYJ", "",11L);
		mbo.setValue("JSBSPPERSON", "",11L);
		mbo.setValue("JSBSPTIME", "",11L);
		mbo.setValue("JSBLEADYJ", "",11L);
		mbo.setValue("JSBLEADPERSON", "",11L);
		mbo.setValue("JSBLEADTIME", "",11L);
		mbo.setValue("FGLEADYS", "",11L);
		mbo.setValue("FGLEADPERSON", "",11L);
		mbo.setValue("FGLEADTIME", "",11L);
		mbo.setValue("ZZSTOPYJ", "",11L);
		mbo.setValue("ZZSTOPPERSON", "",11L);
		mbo.setValue("ZZSTOPYSTIME", "",11L);
		mbo.setValue("ZZPZRRECOVERYJ", "",11L);
		mbo.setValue("ZZPZRECOVERPERSON", "",11L);
		mbo.setValue("ZZPZRECOVERTIME", "",11L);
		mbo.setValue("JHJXSTARTDATE", "",11L);
		mbo.setValue("JHJXENDDATE", "",11L);
		mbo.setValue("REAL_STARTDATE", "",11L);
		mbo.setValue("REAL_STOPDATE", "",11L);
		mbo.setValue("TJSJ_STARTDATE", "",11L);
		mbo.setValue("TJSJ_STOPDATE", "",11L);
		mbo.setValue("CONTROLLER_01", "",11L);
		mbo.setValue("DDJPZ_STARTDATE", "",11L);
		mbo.setValue("DDJPZ_STOPDATE", "",11L);
		mbo.setValue("CONTROLLER_02", "",11L);
		mbo.setValue("ZZPZSTARTTIME", "",11L);
		mbo.setValue("ZZPZSTOPTIME", "",11L);
		mbo.setValue("STATUS", "新建",11L);
		app.getAppBean().refreshTable();
		app.getAppBean().reloadTable();
		return i;
	}
}
