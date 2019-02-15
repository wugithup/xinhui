package com.shuto.mam.webclient.beans.com;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.doclinks.DocLinksBean;
/**
 * 混配煤方案
 com.shuto.mam.webclient.beans.com.ComsignDocLinksBean 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午9:01:00
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class ComsignDocLinksBean extends DocLinksBean
{
  public MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    MboSetRemote msr = super.getMboSetRemote();

    return msr;
  }

  public int instantdelete()
    throws MXException
  {
    try
    {
      String status = getMbo().getOwner().getString("STATUS");

      if ((!("新建".equals(status))) && (!("已取消".equals(status))) && (!("退回修改".equals(status))))
      {
        getMbo().setFlag(7L, true);
      }

    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }

    return super.instantdelete();
  }
}