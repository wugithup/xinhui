package com.shuto.mam.app.wo.gzpwh;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 脚手架管理        JSJGL  
 com.shuto.mam.app.wo.gzpwh.FldTeamName 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月18日 下午8:12:21
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldTeamName extends MAXTableDomain{
	String tableName=null;
	String attName=null;

	public FldTeamName(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		attName = getMboValue().getName();
		tableName=getMboValue().getMbo().getName();
		setRelationship("CLASSTEAM", "siteid=:siteid");
		String[] strTo = { attName };
		
		String[] strFrom = { "TEAMNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}
	
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		Mbo mbo = getMboValue().getMbo();
		StringBuffer whereSB = new StringBuffer("1=1");
		if(mbo.isZombie()){
			whereSB.append(" and teamname in(select teamname from gzprywhzb group by teamname)");
		}
		whereSB.append(" and siteid='"+mbo.getUserInfo().getInsertSite()+"'");
		setListCriteria(whereSB.toString());
		MboSetRemote list = super.getList();
		return list;
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		MboRemote mbo = getMboValue().getMbo();
		String siteid = getMboValue().getMbo().getString("siteid");// 站点
		String personid = getMboValue().getMbo().getString("personid");// 人员
		String banzu = getMboValue().getMbo().getString("teamname");// 班组
		MboSetRemote personSet = mbo.getMboSet("#person","person", "personid='"+personid+"' and locationsite='" + siteid + "'");// 得到人员数据


		if(!banzu.isEmpty()){
			if (!personSet.isEmpty()) {// 如果数据不为空
				personSet.getMbo(0).setValue("banzu", banzu,11L);// 班组	
			}			
		}
		personSet.close();
	}
	

	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.validate();
		if(getMboValue().isNull()){
			return;
		}
		MboSetRemote list = getList();
		SqlFormat sql=new SqlFormat("TEAMNAME=:1");
		sql.setObject(1, "GZPRYWHZB", "TEAMNAME", getMboValue().getString());
		String appWhere = list.getAppWhere();
		list.setAppWhere(sql.format());
		list.reset();
		int count = list.count();
		list.setAppWhere(appWhere);
		list.reset();
		if(count==0){
			throw new MXApplicationException("该值在选择列表中不存在", "" );
		}
	}
}
