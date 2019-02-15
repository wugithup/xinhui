package com.shuto.mam.webclient.beans.kaizen;

import java.rmi.RemoteException;
import java.util.Date;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
/**
 * KAIZEN项目提案           YX_REGISTR
 com.shuto.mam.webclient.beans.kaizen.KztaAppBean 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午9:15:24
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class KztaAppBean  extends CAppBean{
	public KztaAppBean() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public int SAVE() throws MXException, RemoteException {
		if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}
		
		return super.SAVE();
	}
	
	/* 发送工作流 */
	public int ROUTEWF() throws MXException, RemoteException {
		if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}
		MboRemote mbo = app.getAppBean().getMbo();
		String status = mbo.getString("status");

		if (status.equals("已批准")) {// 工作流关闭不能再次发送
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");

		}
		return super.ROUTEWF();

	}

	public boolean hasAuth() throws MXException, RemoteException {
		long l = getMbo().getLong("REGISTRATIONID"); 
		String s = getMbo().getString("STATUS");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if ("新建".equals(s))
			return true;
		MboSetRemote mbosetremote = getMbo()
				.getMboSet(
						"$instance",
						"WFINSTANCE",
						"ownertable='REGISTRATION' and ownerid='" + l
								+ "' and active = 1");
		if (!(mbosetremote.isEmpty())) {
			String s2 = "ownerid='"
					+ l
					+ "' and ownertable='REGISTRATION' and assignstatus='活动' and assigncode='"
					+ s1 + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode",
					"WFASSIGNMENT", s2);
			return (!(mbosetremote1.isEmpty()));
		}
		return false;
	}

	//set 集合 mbo 
	@Override 
	public int INSERT() throws MXException, RemoteException {
		try{
			super.INSERT();
			MboRemote mbo = this.getMbo();
			mbo.setValue("CREATEDATE", new Date());
			String CREATEPERSON  = mbo.getUserInfo().getPersonId(); //获得当前登陆人
			MboRemote personMbo  =mbo.getMboSet("$person","person","personid='"+CREATEPERSON+"'").getMbo(0);
			String DEPARTEMNT ="" ; 
			String banzu = ""; 
			if(personMbo!=null){
				DEPARTEMNT =personMbo .getString("department"); //获得此人的部门
				banzu =personMbo .getString("banzu"); //获得此人的部门
			}
			mbo.setValue("CREATEPERSON", CREATEPERSON,11L);
			mbo.setValue("DEPARTEMNT", DEPARTEMNT,11L);
			mbo.setValue("status", "新建",11L);
			mbo.setValue("EFFECTIVE", Boolean.TRUE,11L);
			mbo.setValue("ZHIBIE", banzu,2L);
			
//			Calendar c = Calendar.getInstance();  
			
//			mbo.setValue("year", c.get(Calendar.YEAR),2L);
//			mbo.setValue("month", c.get(Calendar.MONTH) +1 ,2L);
			this.refreshTable();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 1 ; 
	}

	@Override
	public int DUPLICATE() throws MXException, RemoteException {
		// TODO Auto-generated method stub
//		super.DUPLICATE();
		MboRemote thisMbo = this.app.getAppBean().getMbo();
		java.util.Date date = psdi.server.MXServer.getMXServer().getDate();
		String userName = thisMbo.getThisMboSet().getUserInfo().getPersonId();
		String siteid = thisMbo.getString("siteid");
		MboSetRemote mboset = thisMbo.getMboSet("$PERSON", "PERSON", "PERSONID = '"+userName+"' and LOCATIONSITE = '"+siteid+"'");
		MboRemote personmbo = mboset.getMbo(0);
		String departemnt = personmbo.getString("department");
		MboRemote newMbo = getMboSet().add();
		String projectname = thisMbo.getString("projectname");
		String zhibie = thisMbo.getString("zhibie");
		String bimprovement = thisMbo.getString("bimprovement");
		String aimprovement = thisMbo.getString("aimprovement");
		newMbo.setValue("status", "新建", 11L);
		newMbo.setValue("EFFECTIVE", Boolean.TRUE,11L);
		newMbo.setValue("createdate", date, 11L);
		newMbo.setValue("CREATEPERSON", userName, 11L);
		newMbo.setValue("PROJECTNAME", projectname, 11L);
		newMbo.setValue("ZHIBIE", zhibie, 11L);
		newMbo.setValue("BIMPROVEMENT", bimprovement, 11L);
		newMbo.setValue("AIMPROVEMENT", aimprovement, 11L);
		newMbo.setValue("DEPARTEMNT", departemnt, 11L);
		setCurrentRecordData(newMbo);
		mboset.close();
		return 1;
	}
	
}
