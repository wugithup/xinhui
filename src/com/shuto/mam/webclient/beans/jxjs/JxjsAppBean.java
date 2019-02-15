package com.shuto.mam.webclient.beans.jxjs;

/**  
com.shuto.mam.webclient.beans.jxjs.JxjsAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月17日 上午9:44:59
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class JxjsAppBean extends AppBean {
	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		MboSetRemote AppMboSet = this.app.getAppBean().getMboSet();
		MboRemote AppMbo = AppMboSet.getMbo();
		String personid = this.sessionContext.getUserInfo().getPersonId();
		MboSetRemote msr_person = AppMbo.getMboSet("$prperson", "person", "personid ='" + personid + "'");
		String department = msr_person.getMbo(0).getString("DEPARTMENT");
		String zy = msr_person.getMbo(0).getString("profession");
		AppMbo.setValue("DEPNUM", department, 11L);
		AppMbo.setValue("zy", zy, 11L);
		this.app.getAppBean().fireStructureChangedEvent();
		this.app.getAppBean().fireChildChangedEvent();
		return 1;
	}
}
