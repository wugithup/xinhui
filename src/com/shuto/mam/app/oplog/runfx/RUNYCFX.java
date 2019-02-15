package com.shuto.mam.app.oplog.runfx;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 运行分析
 com.shuto.mam.app.oplog.runfx.RUNYCFX 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:25:07
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class RUNYCFX extends Mbo
  implements RUNYCFXRemote
{
  public RUNYCFX(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void init() throws MXException {
    super.init();
    String Status = getMboValue("STATUS").getString();

    if ((!(Status.equalsIgnoreCase("已归档"))) && (!(Status.equalsIgnoreCase("已作废"))))
      return;
    setFlag(7L, true);
  }
}