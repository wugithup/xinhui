package com.shuto.mam.app.jngl.jnzz.jn_hy;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**  
com.shuto.mam.app.jngl.jnzz.jn_hy.FldZCr 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午9:33:15
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldZCr extends MAXTableDomain
{
  public FldZCr(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("PERSON", "");
    String[] strFrom = { "PERSONID" };
    String[] strTo = { "ZCR" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("status = '活动' and department is not null");
    return super.getList();
  }
}