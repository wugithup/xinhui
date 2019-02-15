package com.shuto.mam.webclient.beans.tz.opbill;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
/** 
 * @author  lzq
 * @date 创建时间：2017-3-24 上午9:31:27 
 * @since  原华南台账相关类
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class OPOpglssclfmbAppBean extends AppBean
{
  public void init()
    throws RemoteException, MXException
  {
    super.initialize();
  }

  public int addrow()
  {
    try
    {
      super.addrow();
    }
    catch (MXException e)
    {
      e.printStackTrace();
    }

    return 1;
  }

  public void insert()
  {
    try
    {
      super.insert();

      DataBean db = this.app.getDataBean("1338857667621");
      MboSetRemote mboSet = db.getMboSet();

      db.refreshTable();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    catch (MXException e) {
      e.printStackTrace();
    }
  }
}