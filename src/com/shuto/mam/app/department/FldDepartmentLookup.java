package com.shuto.mam.app.department;

import java.rmi.RemoteException;

import bsh.EvalError;
import bsh.Interpreter;
import psdi.mbo.*;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.util.logging.FixedLoggers;

/**
 * 脚手架管理   JSJGL 
 com.shuto.mam.app.department.FldDepartmentLookup 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 下午5:11:31
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldDepartmentLookup  extends MAXTableDomain{
	String siteid="SITEID";

	public FldDepartmentLookup(MboValue mbv)  {
		super(mbv);
		// TODO Auto-generated constructor stub
		String objectName="DEPARTMENT";
		String whereClause=null;
		whereClause="SITEID=:"+siteid+" and DEPNUM=:"+getMboValue().getName();
		
		//是否有效Mbo,无效的话一般为用作查询的Mbo
		if(getMboValue().getMbo().isValid()){
			setMultiKeyWhereForLookup("SITEID=:"+siteid); 
		}else{
			try {
				//查询的话除管理员以外的用户只显示登录人所在站点信息
				if(!getMboValue().getMbo().getUserInfo().getPersonId().equalsIgnoreCase("MAXIMO")){
					setMultiKeyWhereForLookup("SITEID='"+getMboValue().getMbo().getUserInfo().getInsertSite()+"'"); 
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setRelationship(objectName, whereClause);
        addConditionalListWhere("siteid", "1", "siteid=:"+siteid);
        setLookupKeyMapInOrder(new String[] {
            getMboValue().getName()
        }, new String[] {
            "DEPNUM"
        });
	}
	
	public MboSetRemote getList()
        throws MXException, RemoteException
	{
		MboSetRemote list = super.getList();
		Interpreter i = new Interpreter();  // Construct an interpreter
		String message=null;
		try {
			Mbo maxtdMbo = this.getMboValue().getMbo();
			
			String appName = getAppName();
			StringBuffer where=null;
			int total=0; 
			String field = new StringBuffer().append(maxtdMbo.getName())
					.append(".").append(getMboValue().getAttributeName())
					.toString();
			i.set("maxtd", this);
			SqlFormat sql=new SqlFormat("upper(appName)=upper(:1) and upper(field)=upper(:2)");
			sql.setObject(1, "DEPARTMENTHIDE", "APPNAME", appName);
			sql.setObject(2, "DEPARTMENTHIDE", "field", field);
			MboSetRemote depSet = maxtdMbo.getMboSet("$DEPARTMENTHIDE","DEPARTMENTHIDE",sql.format());
			for (int j = 0; j < depSet.count(); j++) {
				MboRemote hideMbo = depSet.getMbo(j);
				String depnum = hideMbo.getString("DEPARTMENT");
				message=depnum;
				i.set("hideMbo", hideMbo);
				String bsh = hideMbo.getString("SOURCE");
				Object eval = i.eval(bsh);
				if(eval!=null&&eval.equals(true)){
					if(total==0){
						where=new StringBuffer() .append("DEPNUM not in ('").append(depnum).append("'");
					}else{
						where.append(",'").append(depnum).append("'");
						
					}
					total++;
				}
			}
			depSet.close();
			if(where!=null){
				where.append(")");
				list.setWhere(where.toString());
				list.reset();
			}else{
				list.setWhere("");
				list.reset();
			}
            
		} catch (EvalError e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			FixedLoggers.SERVICELOGGER.error("部门"+message+"配置错误,请联系管理员!",e);
			
			
            throw new MXApplicationException("部门"+message+"配置错误,请联系管理员!", "");
		}
		return list;
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.validate();
		MboSetRemote list = getList();
		SqlFormat sql=new SqlFormat("DEPNUM=:1");
		sql.setObject(1, "DEPARTMENT", "DEPNUM", getMboValue().getString());
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
	
	
	
	public String getAppName() throws RemoteException, MXException{
		int i=0;
		MboSetRemote mboSet = getMboSet();
		String app=null;
		while(true){
			if(mboSet!=null){
				app=mboSet.getApp();
				if(app==null){
					MboRemote owner = mboSet.getOwner();
					if(owner==null){
						return null;
					}else{
						mboSet=owner.getThisMboSet();
					}
				}else{
					return app;
				}
			}else{
				return null;
			}
			//循环检测appname,超过10次直接返回null
			if((++i)>10){
				return null;
			}
		}
	}

}







