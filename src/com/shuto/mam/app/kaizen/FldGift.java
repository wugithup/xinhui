package com.shuto.mam.app.kaizen;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**  
com.shuto.mam.app.kaizen.FldGift 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年10月24日 下午3:36:32
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class FldGift extends MAXTableDomain{

	public FldGift(MboValue mbv) {
		super(mbv);
		
		String thisAtt = getMboValue().getName();

		setRelationship("KZGIFTLIST", "kzgiftlistid=:"+thisAtt);

		String[] strFrom = { "kzgiftlistid","description" };

		String[] strTo = { thisAtt ,"description"};
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		this.setListCriteria("status='有效'");
		return super.getList();
	}

}
