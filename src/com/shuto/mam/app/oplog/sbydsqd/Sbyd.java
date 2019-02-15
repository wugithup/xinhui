package com.shuto.mam.app.oplog.sbydsqd;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.oplog.sbydsqd.Sbyd 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:17:40
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class Sbyd extends Mbo
  implements SbydRemote
{
  public Sbyd(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void init()
    throws MXException
  {
    super.init();
    String status = getMboValue("STATUS").getString();
    status.equals("FDBSH");

    if (status.equals("APPR"))
    {
      setFieldFlag("SBNAME", 7L, true);
      setFieldFlag("LOCATION", 7L, true);
      setFieldFlag("JFCOST", 7L, true);
      setFieldFlag("YDCAUSE", 7L, true);
      setFieldFlag("BMFZPERSON", 7L, true);
      setFieldFlag("STARDATE", 7L, true);
      setFieldFlag("ENDDATE", 7L, true);
      setFieldFlag("YDQSITUATION", 7L, true);
      setFieldFlag("FAEDITPERSON", 7L, true);
      setFieldFlag("FAEDITDATE", 7L, true);
      setFieldFlag("YDPLAN", 7L, true);
    }

    if (!(status.equals("CLOSE")))
      return;
    setFlag(7L, true);
  }

  public void add()
    throws MXException, RemoteException
  {
    super.add();
    String status = getMboValue("STATUS").getString();

    status.equals("FDBSH");
  }
}