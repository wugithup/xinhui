package com.shuto.mam.app.asset.assprol;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.asset.assprol.AssproSet 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:16:58
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class AssproSet extends MboSet
  implements MboSetRemote
{
  public AssproSet(MboServerInterface mboServerInterface)
    throws RemoteException
  {
    super(mboServerInterface);
  }

  protected Mbo getMboInstance(MboSet mboSet) throws MXException, RemoteException
  {
    return new Asspro(mboSet);
  }
}