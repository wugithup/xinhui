package com.shuto.mam.app.dljs;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 电量结算
 com.shuto.mam.app.dljs.sjds 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:58:29
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class sjds extends Mbo
  implements sjdsRemote
{
  public sjds(MboSet mboset)
    throws RemoteException, MXException
  {
    super(mboset);
  }

  public void save() throws MXException, RemoteException {
    super.save();
  }

  public void init()
    throws MXException
  {
    super.init();
    String[] Str = { "YEAR", "MONTH", "HY1XYM", "HY2XYM", "YHZBYM", "EHZBYM", "HY1XBL", "HY2XBL", "YHZBBL", "EHZBBL", 
      "BZ", "HY1XYC", "HY2XYC", "YHZBYC", "EHZBYC", "JZ" };
    try {
      if (!(isNew()))
        if (getString("status").equals("新建")) {
          setFieldFlag(Str, 7L, false);
        }
        else
        {
          setFieldFlag(Str, 7L, true);
        }
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }
}