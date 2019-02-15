package com.shuto.mam.app.oplog.sbydsqd;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.oplog.sbydsqd.Sbydys 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:18:17
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class Sbydys extends Mbo
  implements SbydysRemote
{
  public Sbydys(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void init()
    throws MXException
  {
    super.init();
    String status = getMboValue("STATUS").getString();

    if ((status.equals("NEW")) || (status.equals("RETURN")) || (status.equals("BMLDSH")))
    {
      setFieldFlag("YSPERSON", 7L, true);
      setFieldFlag("YDACCEPT", 7L, true);
    }

    if (status.equals("YXZYYS"))
    {
      setFieldFlag("EDITPERSON", 7L, false);
      setFieldFlag("JGTRANSACTION", 7L, false);
      setFieldFlag("YSPERSON", 7L, false);
      setFieldFlag("YDACCEPT", 7L, false);
      setFieldFlag("EDITPERSON", 128L, true);
      setFieldFlag("JGTRANSACTION", 128L, true);
      setFieldFlag("YSPERSON", 128L, true);
      setFieldFlag("YDACCEPT", 128L, true);
    }

    if (status.equals("YSZLGD"))
    {
      setFieldFlag("DESCRIPTION", 7L, true);
      setFieldFlag("YDNUM", 7L, true);
      setFieldFlag("TZNAME", 7L, true);
      setFieldFlag("TZNUM", 7L, true);
      setFieldFlag("JGDATE", 7L, true);
      setFieldFlag("YDHACTUAL", 7L, true);
      setFieldFlag("JDPERSON", 7L, true);
      setFieldFlag("EDITPERSON", 7L, true);
      setFieldFlag("YDHGCEDIT", 7L, true);
      setFieldFlag("YSPERSON", 7L, true);
      setFieldFlag("YDACCEPT", 7L, true);
    }

    if (!(status.equals("CLOSE")))
      return;
    setFlag(7L, true);
  }
}