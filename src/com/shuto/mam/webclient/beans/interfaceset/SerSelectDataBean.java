package com.shuto.mam.webclient.beans.interfaceset;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @Title: SerSelectDataBean.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-12 上午11:33:19 
 * @version V1.0 
 */
public class SerSelectDataBean extends DataBean {
	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		String partnernum = this.app.getAppBean().getMbo().getString("partnernum");
		MboSetRemote serviceSet = super.getMboSetRemote();
		serviceSet.setWhere("servicenum not in (select servicenum from partnerauth where partnernum='"+partnernum+"') and enable=1");
		return serviceSet;
	}
	
	public int execute() throws MXException, RemoteException {
		MboSetRemote selectLines = getMboSet();
		selectLines.resetWithSelection();
		String partnernum = this.app.getAppBean().getMbo().getString("partnernum");
		MboSetRemote partnerauths = this.app.getAppBean().getMbo().getMboSet("PARTNERAUTH");
		MboRemote partnerauth = null;
		for (int i = 0; i < selectLines.count(); i++) {
			partnerauth = partnerauths.add();
			partnerauth.setValue("partnernum", partnernum);
			partnerauth.setValue("servicenum", selectLines.getMbo(i).getString("servicenum"));
			partnerauth.setValue("servicename", selectLines.getMbo(i).getString("servicename"));
			partnerauth.setValue("servicetype", selectLines.getMbo(i).getString("servicetype"));
		}
		app.getAppBean().save();
		return super.execute();
	}
}
