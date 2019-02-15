package com.shuto.mam.app.om.noticebill;
/**  
com.shuto.mam.app.om.noticebill.FldPerson 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月15日 下午2:51:08
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPerson extends MAXTableDomain
{
  public FldPerson(MboValue mbovalue)
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
    
	  Mbo mbo = getMboValue().getMbo();
	  String siteid = mbo.getString("siteid");
	  setListCriteria("status = '活动' and locationsite='"+siteid+"' and department is not null");
    return super.getList();
  }
}
