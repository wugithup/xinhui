package com.shuto.mam.app.sjds;
/**  
com.shuto.mam.app.sjds.sjds 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午11:52:05
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

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
    try {
      if (getString("status").equals("已关闭"))
        setFlag(7L, true);
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}
