package com.shuto.mam.app.wo.gzpwh;

import java.rmi.RemoteException;


import psdi.app.person.FldPersonID;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 脚手架管理          JSJGL  
 com.shuto.mam.app.wo.gzpwh.FldGZPPersonId 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月18日 下午8:11:43
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldGZPPersonId extends FldPersonID {

	public FldGZPPersonId(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	public MboSetRemote getList() {
		try {
			String personid = this.getMboSet().getUserInfo().getPersonId(); 
			this.setListCriteria("locationsite in (select locationsite from person where personid='"+personid+"') or 'MAXADMIN'='"+personid+"'");
			return super.getList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		
		if(!getMboValue().isNull()){
			String depnum = getMboValue().getMbo().getString("PERSONID.DEPARTMENT");
			if(depnum!=null&&!depnum.isEmpty()){
				getMboValue().getMbo().setValue("DEPARTMENT", depnum,MboConstants.NOACCESSCHECK);
			}
			String phone = getMboValue().getMbo().getString("PERSONID.primaryphone");
				getMboValue().getMbo().setValue("phone",phone,MboConstants.NOACCESSCHECK);
		}
		
	}

}
