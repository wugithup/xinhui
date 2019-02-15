package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.workorder.FldPmstatusa 磴口项目
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月7日 下午2:48:05
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FldPmstatusa extends MboValueAdapter
{
  public FldPmstatusa(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);
  }

  public void action()
    throws MXException, RemoteException
  {
    super.action();
    MboRemote mbo = getMboValue().getMbo();

    String PMSTATUSA = mbo.getString("PMSTATUSA");

    if (PMSTATUSA.equals("已完成")) {
      mbo.setFieldFlag("OPREMARK", 128L, true);
      mbo.setFieldFlag("OPCHANGETIME", 128L, false);
      mbo.setFieldFlag("OPUNDO", 128L, false);
    } else if (PMSTATUSA.equals("延迟完成")) {
      mbo.setFieldFlag("OPREMARK", 128L, false);
      mbo.setFieldFlag("OPCHANGETIME", 128L, true);
      mbo.setFieldFlag("OPUNDO", 128L, true);
    } else {
      mbo.setFieldFlag("OPREMARK", 128L, false);
      mbo.setFieldFlag("OPCHANGETIME", 128L, false);
      mbo.setFieldFlag("OPUNDO", 128L, false);
    }
  }
}
