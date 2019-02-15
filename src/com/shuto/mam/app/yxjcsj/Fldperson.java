package com.shuto.mam.app.yxjcsj;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**  
com.shuto.mam.app.yxjcsj.Fldperson 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月16日 上午10:47:20
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class Fldperson extends MAXTableDomain
{
  public Fldperson(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("PERSON", "");
    String thisAtt = getMboValue().getName();
    String[] strFrom = { "PERSONID" };
    String[] strTo = { thisAtt };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
	  Mbo mbo=getMboValue().getMbo();
	  String siteid=mbo.getString("siteid");
	  setListCriteria("status = '活动' and locationsite='"+siteid+"' and department is not null");
    return super.getList();
  }
}
