package com.shuto.mam.app.htfk;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:38:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class FldHtnum extends MAXTableDomain
{
  public FldHtnum(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("ht", "1=1");
    String[] strTo = { "htnum" };
    String[] strFrom = { "htnum" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("siteid=:siteid and httype not like '%售%' and status='已批准'");
    return super.getList();
  }
}