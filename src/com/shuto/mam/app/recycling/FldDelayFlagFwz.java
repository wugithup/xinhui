package com.shuto.mam.app.recycling;
/**  
com.shuto.mam.app.recycling.FldDelayFlagFwz 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月10日 下午4:51:54
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldDelayFlagFwz extends MboValueAdapter
{
  public FldDelayFlagFwz(MboValue mbv)
  {
    super(mbv);
  }

  public void action() throws MXException, RemoteException
  {
    super.action();
    kz();
  }

  public void init()
    throws MXException, RemoteException
  {
    super.init();
    kz();
  }

  public void kz() throws MXException, RemoteException {
    super.action();
    String flag = getMboValue().getString();
    MboRemote parent = getMboValue().getMbo();

    if ("固定资产".equalsIgnoreCase(flag)) {
//      parent.setFieldFlag("GLZCBFD", 7L, false);
//      parent.setFieldFlag("GLZCBFD", 128L, true);
      parent.setFieldFlag("FGDZCFL", 128L, false);
      parent.setFieldFlag("FGDZCFL", 7L, true);
    } else {
      parent.setFieldFlag("FGDZCFL", 7L, false);
      parent.setFieldFlag("FGDZCFL", 128L, true);
//      parent.setFieldFlag("GLZCBFD", 128L, false);
//      parent.setFieldFlag("GLZCBFD", 7L, true);
    }
  }
}
