package com.shuto.mam.app.wo.hse;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * com.shuto.mam.app.wo.hse.FldTagoutMethod 华东大区 隔离锁工作票选择隔离方法 appname:HZWOTICKET
 *
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月17日 下午4:09:01
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldTagoutMethod extends MAXTableDomain {

	public FldTagoutMethod(MboValue mbv) {
		super(mbv);
		setRelationship("tagoutmethod", "");
		setLookupKeyMapInOrder(new String[] { "TAGMETHOD" },
				new String[] { "TAGOUTMETHOD" });
	}
	
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		MboRemote woMbo = mbo.getMboSet("WORKORDER").getMbo(0);
		System.out.println("--------------------------------"+woMbo.getName());
		String type = woMbo.getString("S_ORDERTYPE");
		if("一级动火工作票".equals(type)||"二级动火工作票".equals(type)){
			setListCriteria(" optype = '动火票'");
		}else{
			setListCriteria(" optype = '工作票'");
		}
		return super.getList();
	}

}
