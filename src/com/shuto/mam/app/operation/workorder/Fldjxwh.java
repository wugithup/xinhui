package com.shuto.mam.app.operation.workorder;
/**  
com.shuto.mam.app.operation.workorder.Fldjxwh 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月16日 下午5:26:13
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class Fldjxwh extends MAXTableDomain
{
  public Fldjxwh(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);
    setRelationship("locations", "");
	String[] strFrom = new String[] { "location" };
	String thisAttr = getMboValue().getAttributeName();
	String strTo[] = { thisAttr };
	setLookupKeyMapInOrder(strTo, strFrom);
  }
  
  @Override
	public MboSetRemote getList() throws MXException, RemoteException {
	  Mbo mbo = getMboValue().getMbo();
	  String siteid = mbo.getString("siteid");
	  setListCriteria(" siteid='"+siteid+"' ");
	  return super.getList();
	}
  
}
