package com.shuto.mam.app.hr.department;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.hr.department.FldTopdepartment 磴口项目
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月7日 下午1:58:15
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FldTopdepartment extends MAXTableDomain
{
  public FldTopdepartment(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("department", "1=1");
    String[] strFrom = { "department" };
    String[] strTo = { getMboValue().getAttributeName() };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("iscrew=0 and ispost=0");
    return super.getList();
  }
}
