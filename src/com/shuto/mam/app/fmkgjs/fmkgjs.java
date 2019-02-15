package com.shuto.mam.app.fmkgjs;
/**  
com.shuto.mam.app.fmkgjs.fmkgjs 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午11:08:56
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class fmkgjs extends Mbo
  implements fmkgjsRemote
{
  public fmkgjs(MboSet mboset)
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
    String[] Str = { "JIZU", "LOCATION", "GLS", "PROFESS", "LOCKPERSON", "LOCKDATE" };
    String[] Str1 = { "UNLOCKPERSON", "UNLOCKDATE", "NOTE" };
    try {
      if (!isNew()) {
        if (getString("status").equals("解锁人解锁"))
        {
          setFieldFlag(Str1, 7L, false);
        }
        else
        {
          setFieldFlag(Str1, 7L, true);
        }
        if ((getString("status").equals("新建")) || (getString("status").equals("退回修改")))
        {
          setFieldFlag(Str, 7L, false);
        }
        else
        {
          setFieldFlag(Str, 7L, true);
        }
      }

    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }
}
