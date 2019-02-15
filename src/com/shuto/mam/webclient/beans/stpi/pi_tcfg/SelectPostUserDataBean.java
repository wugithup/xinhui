package com.shuto.mam.webclient.beans.stpi.pi_tcfg;

import java.rmi.RemoteException;
import java.util.Vector;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class SelectPostUserDataBean  extends DataBean{
	
	public SelectPostUserDataBean() {
		super();
	}
	
	public MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		MboRemote mbo = app.getAppBean().getMbo();
		MboSetRemote usesrMboSet = mbo.getMboSet("$st_pi_user", "ST_PI_USER", 
				"st_pi_postid=(select st_pi_postid from st_pi_post where no = :postno and siteid=:siteid and type=:type) "
				+ "and personid not in(select personid from st_pi_taskcfg_user where st_pi_taskcfgid = :st_pi_taskcfgid)");
		
		return usesrMboSet; 
	}
	
	public synchronized int execute() throws MXException, RemoteException {
		super.execute();
		MboRemote mbo = app.getAppBean().getMbo();
		Vector selectLine = getMboSet().getSelection() ;
		//任务配置用户
		MboSetRemote set = mbo.getMboSet("ST_PI_TASKCFG_USER");
		if(selectLine.size()>0){
			MboRemote selectMbo = null;
			MboRemote newMbo = null;
			for (int i = 0; i < selectLine.size(); i++) {
				selectMbo = (MboRemote) selectLine.elementAt(i);
				newMbo = set.add();
				newMbo.setValue("personid", selectMbo.getString("personid"));
				newMbo.setValue("st_pi_taskcfgid", mbo.getString("st_pi_taskcfgid"));
				newMbo.setValue("orgid", mbo.getString("orgid"));
				newMbo.setValue("siteid", mbo.getString("siteid"));
			}
		}
		this.app.getAppBean().save();
		this.app.getAppBean().reloadTable();
		this.app.getAppBean().refreshTable();
		return 1;
	}
}
