package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class ReportsDataZBMbo extends Mbo implements ReportsDataZBMboRemote{

	public ReportsDataZBMbo(MboSet ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() throws MXException {
		// TODO Auto-generated method stub
		super.init();
		try {
			
			
			
			if(getOwner()!=null){
				String appName = getOwner().getThisMboSet().getApp();
				if(appName!=null){
					if(!isNull("REPORTSZBNAME.FORMULA")||!isNull("REPORTSZBNAME.SQLSTR")){
						setFieldFlag("VALUE", MboConstants.READONLY, true);
					}
					if(getOwner().getBoolean("ISLOCKED")){
						if(!getUserInfo().getPersonId().equalsIgnoreCase("MAXADMIN")){
							setFieldFlag("VALUE", MboConstants.READONLY, true);
						}
					}
					if("REPORTSDT".equalsIgnoreCase(appName)
						||"RDWH_XJLR".equalsIgnoreCase(appName)
						||"RDWH_FDMHB".equalsIgnoreCase(appName)	){
							setFieldFlag("VALUE", MboConstants.READONLY, false);
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
