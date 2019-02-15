package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 过滤掉其他工作票的安全措施数据
 * @author Liuyc
 *
 */
public class SelectGlysDataBean extends DataBean {

	@Override
	protected void initialize() throws MXException, RemoteException {
		
		super.initialize();
		MboRemote mainMbo=app.getAppBean().getMbo();
		MboSetRemote safeMsr=mainMbo.getMboSet("TAGOUTENABLED");
		String wosafetylinkid="";
		
		if(!safeMsr.isEmpty()){
			for(int i=0;i<safeMsr.count();i++){
				wosafetylinkid+=safeMsr.getMbo(i).getString("wosafetylinkid").replace(",", "")+",";
			}
			if(wosafetylinkid.length()>0){
				wosafetylinkid=wosafetylinkid.substring(0,wosafetylinkid.length()-1);
			}
		}
		this.getMboSet().setWhere("WOTAGOUTID in("+wosafetylinkid+")");
	}
	
}
