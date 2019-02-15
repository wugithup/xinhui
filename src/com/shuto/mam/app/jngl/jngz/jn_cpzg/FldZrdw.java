package com.shuto.mam.app.jngl.jngz.jn_cpzg;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**  
com.shuto.mam.app.jngl.jngz.jn_cpzg.FldZrdw 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午9:34:25
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldZrdw extends MAXTableDomain
{
  public FldZrdw(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("DEPARTMENT", "");
    String[] strFrom = { "DEPNUM" };
    String[] strTo = { "ZRDW" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("siteid=:siteid and  status='活动'");
    return super.getList();
  }
}