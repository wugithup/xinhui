package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/** 选择项目**/
public class FldErpXm extends MAXTableDomain{

	public FldErpXm(MboValue mbv) {
		super(mbv);
		setRelationship("PROJCODE", "1=1");
		setListCriteria("1=1");
		String[] target = { "project_code" }; 
		String[] source = { "PROJCODE" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainMbo = this.getMboValue().getMbo();
		MboSetRemote jzSet = mainMbo.getMboSet("PROJECTJZ");
		if(jzSet != null && jzSet.count()==1){
			mainMbo.setValue("c_jizu", jzSet.getMbo(0).getString("erpjznum"),2l);
		} else {
			mainMbo.setValue("c_jizu", "",2l);
		}
		mainMbo.setValue("task_id", "",2l);
	}
	
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		//站点
		String orgid = mainMbo.getString("orgid");
		String siteid = mainMbo.getString("siteid");
		String interfaceorgtype = mainMbo.getString("interfaceorgtype");
	    
	    MboSetRemote yxkms = mainMbo.getMboSet("FACCOUNTS");
	    String project_type = null;
	    String acct_number = null;
	    String acct_number_sub = null;
	    if (yxkms != null && !yxkms.isEmpty()) {
	    	project_type = yxkms.getMbo(0).getString("project_type");
	 	    acct_number = yxkms.getMbo(0).getString("acct_number");
	 	    acct_number_sub = yxkms.getMbo(0).getString("acct_number_sub");
	    }
		String where = "orgid='"+orgid+"' and siteid='"+siteid+"' and interfaceorgtype='"+interfaceorgtype+"'";
		//按预算科目类型获取对应的预算项目
		MboSetRemote ysxms_by_type = mainMbo.getMboSet("$projcode_by_type", "PROJCODE",where + " and type='"+project_type+"'");
		//按预算科目编号获取对应的预算项目
		MboSetRemote ysxms_by_glaccount = mainMbo.getMboSet("$projcode_by_glaccount", "PROJCODE", where + " and to_number(year)>=2015 and project_status_code in ('1001','1002') and glaccount='"+acct_number+"'");
		//按预算科目编号及预算子目编号获取对应的预算项目
		MboSetRemote ysxms_by_glaccount_sub = mainMbo.getMboSet("$projcode_by_glaccount_sub", "PROJCODE", where + " and to_number(year)>=2015 and project_status_code in ('1001','1002') and glaccount='"+acct_number+"' and glaccount_sub='"+acct_number_sub+"'");
		if (ysxms_by_glaccount_sub != null && !ysxms_by_glaccount_sub.isEmpty()) {
			return ysxms_by_glaccount_sub;
		} else if (ysxms_by_glaccount != null && !ysxms_by_glaccount.isEmpty()) {
			return ysxms_by_glaccount;
		} else {
			return ysxms_by_type;
		}
	}
	
}
