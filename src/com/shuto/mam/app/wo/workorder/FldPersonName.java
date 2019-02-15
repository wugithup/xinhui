package com.shuto.mam.app.wo.workorder;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.mbo.MAXTableDomain;

import psdi.mbo.MboValue;

public class FldPersonName extends MAXTableDomain {

	public FldPersonName(MboValue mbv) {
		super(mbv);
		// 获得当前字段名
		String thisAtt = getMboValue().getName();
		// 设置要获取数据表的关系
		setRelationship("person", "1=1"  );
		// 目标字段名变量
		String[] strTo = { thisAtt };
		// 获取值 字段名变更
		String[] strFrom = { "displayname" };
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(strTo, strFrom);
	}

		
	

		public MboSetRemote getList() throws MXException, RemoteException {
			Mbo mbo = getMboValue().getMbo();
			String siteid = mbo.getString("siteid");
			String  S_ORDERTYPE =mbo.getString("S_ORDERTYPE");
			System.out.println("这是一条线-------------"+S_ORDERTYPE);
				System.out.println("这是一条线-------------"+S_ORDERTYPE);
				// 过滤器中的Mbo
					siteid = mbo.getThisMboSet().getProfile().getDefaultSite();
					if("热控第一种工作票".equals(S_ORDERTYPE)) {
						System.out.println("这是一条线-------------"+S_ORDERTYPE);
					
					this.setListCriteria(" status  = '活动' and locationsite='"
							+ siteid
							+ "'   and   personid  in ('280832','221145') ");
					}
					else if("继电保护工作票".equals(S_ORDERTYPE)) {
						System.out.println("这是一条线-------------"+S_ORDERTYPE);
						this.setListCriteria(" status  = '活动' and locationsite='"
								+ siteid
							+ "'   and   personid  in ('310526','126040') ");
					}
				
			
//			else {
//				this.setListCriteria(" status  = '活动' and locationsite='"
//						+ siteid
//						+ "'   and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ");
//			}
			return super.getList();
		}
	
}
